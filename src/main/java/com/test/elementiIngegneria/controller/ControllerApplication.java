package com.test.elementiIngegneria.controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.test.elementiIngegneria.model.Verbs;

import org.springframework.ui.Model;

@Controller
public class ControllerApplication {

    @GetMapping("/")
    public String Controller(Model model) {

        Verbs verbs = new Verbs();

        System.out.println();

        try {
            model.addAttribute("templateList", loadTemplate("src/main/resources/static/templates/templates.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        String apiKey = loadApiKey("config.json");

        if (apiKey == null) {
            System.err.println("API Key non trovata!");
            return "";
        }

        return "index";
    }

    public static String loadApiKey(String filePath) {
        try (FileReader reader = new FileReader(filePath)) {
            JsonObject json = JsonParser.parseReader(reader).getAsJsonObject();
            return json.get("apiKey").getAsString();
        } catch (IOException e) {
            System.err.println("Errore nel caricamento del file di configurazione: " + e.getMessage());
            return null;
        }
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

    private ArrayList<String> loadTemplate(String path) throws IOException {
        ArrayList<String> templateList = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(path));
        while (br.ready())
            templateList.add(br.readLine());
        br.close();
        return templateList;
    }

}
