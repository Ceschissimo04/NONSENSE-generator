package com.test.elementiIngegneria.controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.test.elementiIngegneria.model.Nouns;
import com.test.elementiIngegneria.model.Verbs;

import org.springframework.ui.Model;

@Controller
public class ControllerApplication {

    @GetMapping("/")
    public String Controller(Model model) {

        Verbs nouns = new Verbs();

        System.out.println();

        try {model.addAttribute("templateList", loadTemplate("src/main/resources/static/templates/templates.txt"));}
        catch (IOException e) {e.printStackTrace();}

        String apiKey = loadApiKey("config.json");

        if (apiKey == null) {
            System.err.println("API Key non trovata!");
            return "";
        }

        String text = "the pen was on the table.";
        String language = "en";

        // JSON della richiesta
        String jsonInput = String.format(
                "{\n" +
                        "  \"document\": {\n" +
                        "    \"type\": \"PLAIN_TEXT\",\n" +
                        "    \"language\": \"%s\",\n" +
                        "    \"content\": \"%s\"\n" +
                        "  },\n" +
                        "  \"encodingType\": \"UTF16\"\n" +
                        "}",
                language, text);

        return "index";
    }
    /*
     * try {
     * 
     * @SuppressWarnings("deprecation")
     * URL url = new
     * URL("https://language.googleapis.com/v1/documents:analyzeSyntax?key=" +
     * apiKey);
     * HttpURLConnection con = (HttpURLConnection) url.openConnection();
     * con.setRequestMethod("POST");
     * con.setDoOutput(true);
     * con.setRequestProperty("Content-Type", "application/json; utf-8");
     * con.setRequestProperty("Accept", "application/json");
     * 
     * // Scrivi JSON nel body
     * try (OutputStream os = con.getOutputStream()) {
     * byte[] input = jsonInput.getBytes(java.nio.charset.StandardCharsets.UTF_8);
     * os.write(input, 0, input.length);
     * }
     * 
     * // Ricevi risposta
     * int responseCode = con.getResponseCode();
     * System.out.println("Codice risposta: " + responseCode);
     * 
     * try (BufferedReader br = new BufferedReader(
     * new InputStreamReader(con.getInputStream(),
     * java.nio.charset.StandardCharsets.UTF_8))) {
     * StringBuilder response = new StringBuilder();
     * String line;
     * 
     * while ((line = br.readLine()) != null) {
     * response.append(line.trim());
     * }
     * 
     * System.out.println("Risposta JSON:");
     * System.out.println(response.toString());
     * }
     * } catch (IOException e) {
     * System.err.println("Errore durante la richiesta HTTP: " + e.getMessage());
     * }
     */

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

        return "index";
    }

    private ArrayList<String> loadTemplate(String path) throws IOException {
        ArrayList<String> templateList = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(path));
        while (br.ready()) templateList.add(br.readLine());
        br.close();
        return templateList;
    }

}
