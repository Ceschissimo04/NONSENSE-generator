package com.test.elementiIngegneria.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
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
                Noun flag = new Noun(word);
                if (!nouns.contains(flag))
                    nouns.add(flag);
            } else if (type.equals("ADJ")) {
                Adjective flag = new Adjective(word);
                if (!adjs.contains(flag))
                    adjs.add(flag);
            } else if (type.equals("VERB")) {
                Verb flag = (Verb) getVerbFromString(word);
                if (!verbs.contains(flag))
                    verbs.add(addVerb(flag.getWord()));
            }
        }
    }

    private Verb addVerb(String verb) {
        Gson gson = new Gson();

        try (JsonReader reader = new JsonReader(
                new FileReader("src/main/resources/static/files/verbs_with_conjugations.json"))) {
            reader.beginArray();

            while (reader.hasNext()) {
                VerbJsonData data = gson.fromJson(reader, VerbJsonData.class);
                if (data.infinitive != null && data.infinitive.contains(verb)) {
                    String present = data.indicative.get("present").get(0);
                    String past = data.indicative.get("perfect").get(0);
                    String future = data.indicative.get("future").get(0);
                    return new Verb(verb, present, past, future);
                }
            }

            reader.endArray(); // Fine array

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public Word getVerbFromString(String verb) {
        for (Word w : verbs) {
            if (w.getWord().equals(verb)) {
                return w;
            }
        }
        return new Verb(verb);
    }

    private class VerbJsonData {
        public List<String> infinitive;
        public List<String> participle;
        public List<String> gerund;
        public Map<String, List<String>> indicative;
        public Map<String, List<String>> subjuntive;
        public Map<String, List<String>> conditional;
        public List<String> imperative;
    }
}
