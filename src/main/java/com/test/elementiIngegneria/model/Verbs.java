package com.test.elementiIngegneria.model;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Verbs implements Word {

    private HashMap<String, String[]> allVerbs;
    private ArrayList<String> keys;

    public Verbs() {
        allVerbs = new HashMap<>();
        keys = new ArrayList<>();
        getFromFile();
    }

    /* public String translateVerb(String verb, String tense, String person){
        if (tense.equals("present") && person.equals("nothird")) return verb;
        else if (tense.equals("past")) return allVerbs.get(verb)[1];
        else if (tense.equals("future")) return allVerbs.get(verb)[2];
        else return allVerbs.get(verb)[0];
    } */

    @Override
    public String getRandom() {
        int intRandom = (int) (Math.floor(Math.random() * keys.size()));
        String key = keys.get(intRandom);

        String[] values = allVerbs.get(key);
        int tenseRandom = (int) (Math.floor(Math.random() * 3)); // 0: present, 1: past, 2: future
        return values[tenseRandom];
    }

    public String getRandom(String optional) {
        int intRandom = (int) (Math.floor(Math.random() * keys.size()));
        String key = keys.get(intRandom);

        String[] values = allVerbs.get(key);
        switch (optional) {
            case "present":
                return values[0];
            case "past":
                return values[1];
            case "future":
                return values[2];
            default:
                return key;
        }
    }

    // Usando questo metodo non dovrebbe essere necessario getStringJson
    @Override
    public void getFromFile() {
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

    /*
     * @Override
     * public void getFromFile() {
     * try (FileReader reader = new
     * FileReader("src/main/resources/static/files/verbs.json")) {
     * JsonObject json = JsonParser.parseReader(reader).getAsJsonObject();
     * Iterator<String> it = json.keySet().iterator();
     * while (it.hasNext()) {
     * String key = it.next();
     * allVerbs.put(key, getStringJson(key, json));
     * }
     * } catch (IOException e) {
     * e.printStackTrace();
     * }
     * }
     * 
     * // secondo iterator perche senno non si riesce a fare un hash-map, questa
     * colpa
     * // di json
     * private String[] getStringJson(String key, JsonObject json) {
     * Iterator<JsonElement> strings = json.get(key).getAsJsonArray().iterator();
     * String[] array = new String[3];
     * int index = 0;
     * while (strings.hasNext()) {
     * array[index] = strings.next().toString();
     * index++;
     * }
     * return array;
     * }
     */

    public HashMap<String, String[]> getAllVerbs() {
        return allVerbs;
    }

    public ArrayList<String> getKeys() {
        return keys;
    }

}
