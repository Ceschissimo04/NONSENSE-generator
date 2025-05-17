package com.test.elementiIngegneria.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URI;

public class Querier {
    public static String doSomething(String text, String language) {
        String apiKey = null;
        try {
            apiKey = ApiKeyLoader.getApiKey("config.json");
        } catch (IOException e) {
            // TODO: decidere come gestire le eccezioni
            System.err.println("Errore durante il caricamento della chiave API: " + e.getMessage());
            return null;
        }

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

        String apiUrl = "https://language.googleapis.com/v1/documents:analyzeSyntax?key=" + apiKey;

        try {
            URL url = URI.create(apiUrl).toURL();
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            con.setRequestProperty("Content-Type", "application/json; utf-8");
            con.setRequestProperty("Accept", "application/json");

            // Scrivi JSON nel body
            try (OutputStream os = con.getOutputStream()) {
                byte[] input = jsonInput.getBytes(java.nio.charset.StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            // Ricevi risposta
            int responseCode = con.getResponseCode();
            System.out.println("Codice risposta: " + responseCode);

            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(con.getInputStream(),
                            java.nio.charset.StandardCharsets.UTF_8))) {
                StringBuilder response = new StringBuilder();
                String line;

                while ((line = br.readLine()) != null) {
                    response.append(line.trim());
                }

                System.out.println("Risposta JSON:");
                System.out.println(response.toString());
                return response.toString();
            }
        } catch (IOException e) {
            // TODO: decidere come gestire le eccezioni
            System.err.println("Errore durante la richiesta HTTP: " + e.getMessage());
        }
        return null;
    }
}
