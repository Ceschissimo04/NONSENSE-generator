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

@Controller
public class ControllerApplication {
    public static final String DEFAULT_LANGUAGE = "en";

    @GetMapping("/")
    public String Controller(Model model) {
        try {
            model.addAttribute("templateList", loadTemplate("src/main/resources/static/templates/templates.txt"));
        } catch (IOException e) {
            e.printStackTrace();
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

        String nonsense = "";

        // Pass the results to the page index.html
        // model.addAttribute("nonsenseResult", nonsense);
        model.addAttribute("inputSentence", sentence);
        model.addAttribute("selectedTense", tense);
        model.addAttribute("syntaxTree", "The tree will appear here...");
        model.addAttribute("extractedWords", Analyzer.getPartsOfText(nonsense, "en"));
        model.addAttribute("selectedTemplate", template);

        // If the template is set to "default", a random template is selected
        if (template.equals("default")) {
            template = Template.getRandom();
        }

        // Generate the NonSense sentences
        Generator generator = new Generator();
        ArrayList<String> generated = generator.generateStrings(sentence, template, tense);
        if (generated != null && !generated.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (String s : generated) {
                sb.append(s).append("<br>");
            }
            model.addAttribute("nonsenseResult", sb.toString());
        }

        // Save to the file generated.txt the generated NonSense sentences
        SaveToFile.SavetoFile(generated, sentence, template, tense, "src/main/resources/static/files/generated.txt");

        try {
            model.addAttribute("templateList", loadTemplate("src/main/resources/static/templates/templates.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "index";
    }

    @PostMapping("/analyze")
    public String analyze(
            @RequestParam("sentence") String sentence,
            Model model) {

        model.addAttribute("inputSentence", sentence);

        String result = Analyzer.getSyntaxTree(sentence, DEFAULT_LANGUAGE);
        model.addAttribute("syntaxTree", result);
        model.addAttribute("nonsenseResult", "Your nonsense sentence will appear here ...");
        model.addAttribute("extractedWords", Analyzer.getPartsOfText(sentence, "en"));
        Querier.getToxicityScore(sentence);
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
