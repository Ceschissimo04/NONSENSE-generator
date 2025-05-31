package com.test.elementiIngegneria.controller;

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
 * Handler class for making API calls to Google's Content Moderation API.
 * Provides functionality to check text toxicity levels.
 */
public class ToxicityApiHandler {
    private static final String API_URL_TOXICITY = "https://language.googleapis.com/v1/documents:moderateText?key=";

    /**
     * Sends a query to the Google Content Moderation API to analyze text toxicity.
     *
     * @param text   The text content to be analyzed
     * @param apiKey The API key for authentication
     * @return JSONObject containing the API response, or null if the request fails
     */
    public static JSONObject toxicityQuery(String text, String apiKey) {
        String jsonInput = String.format(
                "{\n" +
                        "  \"document\": {\n" +
                        "    \"type\": \"PLAIN_TEXT\",\n" +
                        "    \"language\": \"%s\",\n" +
                        "    \"content\": \"%s\"\n" +
                        "  }\n" +
                        "}",
                ControllerApplication.DEFAULT_LANGUAGE, text);

        String apiUrl = API_URL_TOXICITY + apiKey;

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
