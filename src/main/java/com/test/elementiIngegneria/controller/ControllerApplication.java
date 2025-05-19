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

import com.test.elementiIngegneria.model.Node;

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

        return "index";
    }

    // pagina a cui rimanda il form
    @PostMapping("/generate")
    public String generaNonsense(
            @RequestParam("sentence") String sentence,
            @RequestParam("template") String template,
            @RequestParam("tense") String tense,
            Model model) {

        String nonsense = "Sentence: " + sentence + " ;  Template: " + template + " ; Tense: " + tense;

        // passa i risultati alla pagina index.html
        model.addAttribute("nonsenseResult", nonsense);
        model.addAttribute("inputSentence", sentence);
        model.addAttribute("selectedTemplate", template);
        model.addAttribute("selectedTense", tense);

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
