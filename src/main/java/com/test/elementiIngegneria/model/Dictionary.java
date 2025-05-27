package com.test.elementiIngegneria.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Dictionary {

    private static Dictionary INSTANCE;
    private List<String> allNouns;
    private HashMap<String, String[]> allVerbs;
    private ArrayList<String> keys;
    private List<String> allAdjs;

    private Dictionary() {

    }

    public static Dictionary getInstance() {
        if (INSTANCE == null)
            INSTANCE = new Dictionary();
        return INSTANCE;
    }

    public void loadAllVerbs() {
        try (FileReader reader = new FileReader("src/main/resources/static/files/verbs.json")) {
            JsonObject json = JsonParser.parseReader(reader).getAsJsonObject();
            for (String key : json.keySet()) {
                JsonArray arr = json.getAsJsonArray(key);
                String[] values = new String[arr.size()];
                for (int i = 0; i < arr.size(); i++) {
                    values[i] = arr.get(i).getAsString();
                }
                allVerbs.put(key, values);
                keys.add(key);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadAllNouns() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("src/main/resources/static/files/nouns.txt"));
            while (br.ready())
                allNouns.add(br.readLine());
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadAllAdjs() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("src/main/resources/static/files/adjs.txt"));
            while (br.ready())
                allAdjs.add(br.readLine());
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
