package com.test.elementiIngegneria.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Verbs implements Word {

    private HashMap<String, String[]> allVerbs;

    public Verbs() {
        allVerbs = new HashMap<>();
        getFromFile();
    }

    @Override
    public String getRandom(String optional) {
        int intRandom = (int) (Math.floor(Math.random() * allVerbs.size()));
        //qua ci andrebbe un altro iterator per prendere un verbo a caso perche l'hash-map non ha un get(int) per semplificare questo, nel while con l'iterator dovrei anche aggiungere un int che mi tenga conto della posizione
        return "";
    }

    @Override
    public void getFromFile() {
        try (FileReader reader = new FileReader("src/main/resources/static/files/verbs.json")) {
            JsonObject json = JsonParser.parseReader(reader).getAsJsonObject();
            Iterator<String> it = json.keySet().iterator();
            while (it.hasNext()){
                String key = it.next();
                allVerbs.put(key, getStringJson(key,json));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //secondo iterator perche senno non si riesce a fare un hash-map, questa colpa di json 
    private String[] getStringJson(String key, JsonObject json) {
        Iterator<JsonElement> strings = json.get(key).getAsJsonArray().iterator();
        String[] array = new String[3];
        int index = 0;
        while (strings.hasNext()) {
            array[index] = strings.next().toString();
            index++;
        }
        return array;
    }

}
