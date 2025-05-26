package com.test.elementiIngegneria.controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.test.elementiIngegneria.model.Template;
import com.test.elementiIngegneria.model.TreeNode;
import com.test.elementiIngegneria.utility.Utilities;

@Controller
public class ControllerApplication {
    public static final String DEFAULT_LANGUAGE = "en";

    @GetMapping("/")
    public String Controller(Model model) {
        // TODO: cambia questa parte usando la classe template
        try {
            model.addAttribute("templateList", loadTemplate("src/main/resources/static/templates/templates.txt"));
        } catch (IOException e) {
            e.printStackTrace();
            return "error";
        }

        model.addAttribute("syntaxTree", "The tree will appear here...");
        model.addAttribute("nonsenseResult", "Your nonsense sentence will appear here ...");
        model.addAttribute("extractedWords", "The template will appear here...");

        return "index";
    }

    // Form submit to this page
    @PostMapping("/generate")
    public String generaNonsense(
            @RequestParam("sentence") String sentence,
            @RequestParam("template") String template,
            @RequestParam("tense") String tense,
            Model model) {

        // Pass the results to the page index.html
        // model.addAttribute("nonsenseResult", nonsense);
        model.addAttribute("inputSentence", sentence);
        model.addAttribute("selectedTense", tense);
        model.addAttribute("syntaxTree", "The tree will appear here...");
        model.addAttribute("extractedWords", "The template will appear here...");
        model.addAttribute("selectedTemplate", template);

        // TODO: cambia questa parte usando la classe template
        try {
            model.addAttribute("templateList", loadTemplate("src/main/resources/static/templates/templates.txt"));
        } catch (IOException e) {
            e.printStackTrace();
            return "error";
        }


        // If the template is set to "default", a random template is selected
        if (template.equals("default")) {
            template = Template.getRandom();
        }

        // Generate the NonSense sentences
            ArrayList<String[]> elements;
            try {
                elements = ApiHandler.getInstance().getElementsOfTextLemma(sentence);
            } catch (IOException e) {
                // TODO: gestire eccezioni
                e.printStackTrace();
                return "error";
            }
            catch (Exception e){
                // TODO: gestire eccezioni
                // qui sarebbe da gestire le eccezioni che vengono sollevate
                // quando l'api di google non funziona
                e.printStackTrace();
                return "error";
            }
        
        // TODO: pensare a cosa fare se l'utente non inserisce nessun verb, nessun noun e nessuno adj
        ArrayList<String> generated = Generator.generateSentences(elements, template, tense);
        if (generated != null && !generated.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (String s : generated) {
                sb.append(s).append("<br>");
            }
            model.addAttribute("nonsenseResult", sb.toString());
            
            // Save to the file generated.txt the generated NonSense sentences
            try {
                HistoryHandler.updateHistory(generated);
            } catch (IOException e) {
                e.printStackTrace();
                return "error";
            }
        }

        return "index";
    }

    @PostMapping("/analyze")
    public String analyze(
            @RequestParam("sentence") String sentence,
            Model model) {

        model.addAttribute("inputSentence", sentence);

        TreeNode root = null;
        String sentenceTemplate = null;
        try {
            sentenceTemplate = ApiHandler.getInstance().getPartsOfText(sentence);
            root = ApiHandler.getInstance().getSyntaxTree(sentence);
        } catch (IOException e) {
            e.printStackTrace();
            return "error";
        }

        String syntaxTreeString = Utilities.generateTreeHTML(root);
        model.addAttribute("syntaxTree", syntaxTreeString);
        model.addAttribute("nonsenseResult", "Your nonsense sentence will appear here ...");
        model.addAttribute("extractedWords", sentenceTemplate);
        // ApiHandler.getToxicityScore(sentence);
        
        // TODO: cambia questa parte usando la classe template
        try {
            model.addAttribute("templateList", loadTemplate("src/main/resources/static/templates/templates.txt"));
        } catch (IOException e) {
            e.printStackTrace();
            return "error";
        }
        return "index";
    }

    private ArrayList<String> loadTemplate(String path) throws IOException {
        ArrayList<String> templateList = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(path));
        while (br.ready())
            templateList.add(br.readLine());
        br.close();
        return templateList;
    }

}
