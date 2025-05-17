package com.test.elementiIngegneria.controller;

import java.io.FileReader;
import java.io.IOException;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class ApiKeyLoader {
    public static String getApiKey(String filePath) throws IOException {
        try (FileReader reader = new FileReader(filePath)) {
            JsonObject json = JsonParser.parseReader(reader).getAsJsonObject();
            return json.get("apiKey").getAsString();
        }
    }
}
