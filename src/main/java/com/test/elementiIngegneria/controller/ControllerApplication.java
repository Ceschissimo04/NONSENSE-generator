package com.test.elementiIngegneria.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

@Controller
public class ControllerApplication {

    @GetMapping("/")
    public String Controller() {
        return "index";
    }

    //pagina a cui rimanda il form
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

        return "index";
    }
}
