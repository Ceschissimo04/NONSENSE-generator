package com.test.nonsenseGenerator.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Handles syntax analysis API requests to the Google Cloud Natural Language API.
 * Provides functionality to analyze the syntax of text using the API endpoint.
 */
public class SyntaxApiHandler {
    private static final String API_URL_SYNTAX = "https://language.googleapis.com/v1/documents:analyzeSyntax?key=";

    /**
     * Sends a syntax analysis request to the Google Cloud Natural Language API.
     *
     * @param text   The text content to analyze
     * @param apiKey The API key for authentication
     * @return JSONObject containing the API response, or null if the request fails
     */
    public static JSONObject SyntaxQuery(String text, String apiKey) {
        String jsonInput = String.format(
                "{\n" +
                        "  \"document\": {\n" +
                        "    \"type\": \"PLAIN_TEXT\",\n" +
                        "    \"language\": \"%s\",\n" +
                        "    \"content\": \"%s\"\n" +
                        "  },\n" +
                        "  \"encodingType\": \"UTF16\"\n" +
                        "}",
                ControllerApplication.DEFAULT_LANGUAGE, text);

        String apiUrl = API_URL_SYNTAX + apiKey;

        try {
            URL url = URI.create(apiUrl).toURL();
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            con.setRequestProperty("Content-Type", "application/json; utf-8");
            con.setRequestProperty("Accept", "application/json");

            try (OutputStream os = con.getOutputStream()) {
                byte[] input = jsonInput.getBytes(java.nio.charset.StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(con.getInputStream(),
                            java.nio.charset.StandardCharsets.UTF_8))) {
                StringBuilder response = new StringBuilder();
                String line;

                while ((line = br.readLine()) != null) {
                    response.append(line.trim());
                }

                System.out.println(response.toString());
                try {
                    return new JSONObject(response.toString());
                } catch (JSONException err) {
                    System.out.println(err.toString());
                    return null;
                }
            }
        } catch (IOException e) {
            System.err.println("Errore durante la richiesta HTTP: " + e.getMessage());
        }
        return null;
    }
}
