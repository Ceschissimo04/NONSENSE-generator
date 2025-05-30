package com.test.elementiIngegneria.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.test.elementiIngegneria.model.Dictionary;
import com.test.elementiIngegneria.model.Template;
import com.test.elementiIngegneria.model.TreeNode;
import com.test.elementiIngegneria.utility.Pair;
import com.test.elementiIngegneria.utility.Utilities;

@Controller
public class ControllerApplication {
    public static final String DEFAULT_LANGUAGE = "en";
    public static final double TOXICITY_THRESHOLD = 0.5; // probability value below which a sentence is considere
                                                         // non-toxic

    @GetMapping("/")
    public String Controller(Model model) {

        // Load static list of templates got from file
        model.addAttribute("templateList", Template.getAllTemplate());

        // Set form attributes
        model.addAttribute("syntaxTree", "The tree will appear here...");
        model.addAttribute("nonsenseResult", "Your nonsense sentence will appear here ...");
        model.addAttribute("extractedWords", "The template will appear here...");
        model.addAttribute("outputTitle", "Generated Nonsense Sentence");

        return "index";
    }

    // Form submit to this page
    @PostMapping("/generate")
    public String generaNonsense(
            @RequestParam("sentence") String sentence,
            @RequestParam("template") String template,
            @RequestParam("tense") String tense,
            @RequestParam(name = "showSyntaxTree", defaultValue = "false") boolean showSyntaxTree,
            @RequestParam(name = "saveToFile", defaultValue = "false") boolean saveToFile,
            Model model) {

        // Refresh page parameters
        model.addAttribute("syntaxTree", "The tree will appear here...");
        model.addAttribute("extractedWords", "The template will appear here...");
        model.addAttribute("nonsenseResult", "Your nonsense sentence will appear here ...");
        model.addAttribute("outputTitle", "Generated Nonsense Sentence");

        sentence = sentence.trim();
        if(!sentence.isEmpty()){

            model.addAttribute("inputSentence", sentence);
            model.addAttribute("selectedTense", tense);
            model.addAttribute("showSyntaxTree", showSyntaxTree);
            model.addAttribute("saveToFile", saveToFile);
            model.addAttribute("templateList", Template.getAllTemplate());
            model.addAttribute("selectedTemplate", template);

            // Generate the NonSense sentences
            List<Pair<String, String>> elements;
            try {
                elements = ApiHandler.getInstance().getElementsOfTextLemma(sentence);
            } catch (IOException e) {
                e.printStackTrace();
                return "error";
            } catch (Exception e) {
                e.printStackTrace();
                return "error";
            }

            List<String> generated = Generator.generateSentences(elements, template, tense);
            List<Pair<String, Integer>> toxicityScores = null;
            if (generated != null && !generated.isEmpty()) {
                try {
                    toxicityScores = ApiHandler.getInstance().getToxicityScoreList(generated);
                } catch (IOException e) {
                    e.printStackTrace();
                    return "error";
                }
                StringBuilder sb = new StringBuilder();
                // generated and toxicityScores should have the same size
                for (int i = 0; i < generated.size(); i++) {
                    String sentenceGenerated = generated.get(i);
                    Pair<String, Integer> toxicityScore = toxicityScores.get(i);
                    sb.append(sentenceGenerated).append(" [").append(toxicityScore.getFirst())
                            .append("] (").append(toxicityScore.getSecond()).append("%)<br>");
                }
                model.addAttribute("nonsenseResult", sb.toString());

                // Save to the file generated.txt the generated NonSense sentences
                if (saveToFile) {
                    try {
                        HistoryHandler.updateHistory(generated);
                    } catch (IOException e) {
                        e.printStackTrace();
                        return "error";
                    }
                }

            }

            // Set syntax tree only if checkbox setted
            String syntaxTreeString = "";
            TreeNode root = null;
            if (showSyntaxTree && generated != null && !generated.isEmpty()) {
                for (String s : generated) {
                    try {
                        root = ApiHandler.getInstance().getSyntaxTree(s);
                        syntaxTreeString += Utilities.generateTreeHTML(root) + "\n\n";
                    } catch (IOException e) {
                        e.printStackTrace();
                        return "error";
                    }
                }
                model.addAttribute("syntaxTree", syntaxTreeString);
            } else {
                model.addAttribute("syntaxTree", "The syntax tree will appear here...");
            }
        }

        return "index";
    }

    @PostMapping("/history")
    public String history(Model model) {

        String output = "";

        List<String> list = null;

        try {
            list = HistoryHandler.readHistory();
        } catch (IOException e) {
            e.printStackTrace();
            return "error";
        }

        if (list.isEmpty())
            output = "History is empty";
        else {
            for (String s : list)
                output += s + "<br>";
        }

        model.addAttribute("templateList", Template.getAllTemplate());
        model.addAttribute("outputTitle", "History");
        model.addAttribute("nonsenseResult", output);
        model.addAttribute("syntaxTree", "The tree will appear here...");
        model.addAttribute("extractedWords", "The template will appear here...");

        return "index";
    }

    @PostMapping("/analyze")
    public String analyze(
            @RequestParam("sentence") String sentence,
            @RequestParam("template") String template,
            @RequestParam("tense") String tense,
            @RequestParam(name = "showSyntaxTree", defaultValue = "false") boolean showSyntaxTree,
            @RequestParam(name = "saveToFile", defaultValue = "false") boolean saveToFile,
            Model model) {

        // Refresh page parameters
        model.addAttribute("outputTitle", "Generated Nonsense Sentence");
        model.addAttribute("nonsenseResult", "Your nonsense sentence will appear here ...");
        model.addAttribute("syntaxTree", "The syntax tree will appear here...");
        model.addAttribute("extractedWords", "The tamplate will appear here...");

        sentence = sentence.trim();
        if(!sentence.isEmpty()){

            model.addAttribute("selectedTense", tense);
            model.addAttribute("showSyntaxTree", showSyntaxTree);
            model.addAttribute("saveToFile", saveToFile);
            model.addAttribute("templateList", Template.getAllTemplate());
            model.addAttribute("selectedTemplate", template);

            TreeNode root = null;
            String sentenceTemplate = null;
            try {
                sentenceTemplate = ApiHandler.getInstance().getPartsOfText(sentence);
                root = ApiHandler.getInstance().getSyntaxTree(sentence);
            } catch (IOException e) {
                e.printStackTrace();
                return "error";
            }

            // Set syntax tree only if checkbox setted
            String syntaxTreeString;
            if (showSyntaxTree) {
                syntaxTreeString = Utilities.generateTreeHTML(root);
                model.addAttribute("syntaxTree", syntaxTreeString);
            }

            model.addAttribute("extractedWords", sentenceTemplate);
        }
        
        return "index";
    }

    @PostMapping("/add")
    public String addDictionary(@RequestParam("sentence") String sentence,
            Model model) {

        List<Pair<String, String>> words;
        try {
            words = ApiHandler.getInstance().getElementsOfTextLemma(sentence);
        } catch (IOException e) {
            e.printStackTrace();
            return "error";
        }

        // Set form attributes
        model.addAttribute("templateList", Template.getAllTemplate());
        model.addAttribute("syntaxTree", "The tree will appear here...");
        model.addAttribute("nonsenseResult", "Your nonsense sentence will appear here ...");
        model.addAttribute("extractedWords", "The template will appear here...");
        model.addAttribute("outputTitle", "Generated Nonsense Sentence");

        Dictionary.getInstance().addWords(words);

        return "index";
    }

}