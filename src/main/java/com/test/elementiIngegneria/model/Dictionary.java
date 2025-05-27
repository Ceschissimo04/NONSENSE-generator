package com.test.elementiIngegneria.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.test.elementiIngegneria.utility.Pair;

public class Dictionary {

    private static Dictionary INSTANCE;
    private List<Word> nouns;
    private List<Word> adjs;
    private List<Word> verbs;
    private static final String NOUNS_FILE_PATH = "src/main/resources/static/files/nouns.txt";
    private static final String ADJECTIVES_FILE_PATH = "src/main/resources/static/files/adjs.txt";
    private static final String VERBS_FILE_PATH = "src/main/resources/static/files/better_verbs.json";

    private Dictionary() {
        nouns = new java.util.ArrayList<>();
        adjs = new java.util.ArrayList<>();
        verbs = new java.util.ArrayList<>();
        loadAllNouns();
        loadAllAdjs();
        loadAllVerbs();
    }

    public static Dictionary getInstance() {
        if (INSTANCE == null)
            INSTANCE = new Dictionary();
        return INSTANCE;
    }

    public void loadAllVerbs() {
        try (FileReader reader = new FileReader(VERBS_FILE_PATH)) {
            JsonObject json = JsonParser.parseReader(reader).getAsJsonObject();
            for (String key : json.keySet()) {
                JsonArray arr = json.getAsJsonArray(key);
                String[] values = new String[arr.size()];
                for (int i = 0; i < arr.size(); i++) {
                    values[i] = arr.get(i).getAsString();
                }
                verbs.add(new Verb(key, values[0], values[1], values[2]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadAllNouns() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(NOUNS_FILE_PATH));
            while (br.ready())
                nouns.add(new Noun(br.readLine()));
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadAllAdjs() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(ADJECTIVES_FILE_PATH));
            while (br.ready())
                adjs.add(new Adjective(br.readLine()));
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Word getRandomNoun() {
        if (nouns.isEmpty())
            return null;
        int idxRandom = (int) (Math.floor(Math.random() * nouns.size()));
        return nouns.get(idxRandom);
    }

    public Word getRandomAdj() {
        if (adjs.isEmpty())
            return null;
        int idxRandom = (int) (Math.floor(Math.random() * adjs.size()));
        return adjs.get(idxRandom);
    }

    public Word getRandomVerb() {
        if (verbs.isEmpty())
            return null;
        int idxRandom = (int) (Math.floor(Math.random() * verbs.size()));
        return verbs.get(idxRandom);
    }

    public void addWords(List<Pair<String, String>> wordsList) {
        for (Pair<String, String> pair : wordsList) {
            String word = pair.getFirst();
            String type = pair.getSecond();
            if (type.equals("NOUN")) {
                nouns.add(new Noun(word));
            } else if (type.equals("ADJ")) {
                adjs.add(new Adjective(word));
            } else if (type.equals("VERB")) {
                verbs.add(new Verb(word));
            }
        }
    }

    public Word getVerbFromString(String verb) {
        for (Word w : verbs) {
            if (w.getWord().equals(verb)) {
                return w;
            }
        }
        return new Verb(verb);
    }
}
