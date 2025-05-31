package com.test.nonsenseGenerator.controller;

import java.io.FileReader;
import java.io.IOException;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Utility class for loading API keys from JSON configuration files.
 */
public class ApiKeyLoader {
    /**
     * Reads and returns the API key from a JSON configuration file.
     *
     * @param filePath Path to the JSON configuration file
     * @return The API key string from the configuration file
     * @throws IOException if there is an error reading the file
     */
    public static String getApiKey(String filePath) throws IOException {
        try (FileReader reader = new FileReader(filePath)) {
            JsonObject json = JsonParser.parseReader(reader).getAsJsonObject();
            return json.get("apiKey").getAsString();
        }
    }
}
