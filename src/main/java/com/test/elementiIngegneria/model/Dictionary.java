package com.test.elementiIngegneria.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.test.elementiIngegneria.utility.Pair;

/**
 * A singleton class that manages collections of words (nouns, adjectives, and verbs).
 * Loads words from files and provides methods to access and manipulate the collections.
 */
public class Dictionary {

    private static Dictionary INSTANCE;
    private List<Word> nouns;
    private List<Word> adjs;
    private List<Word> verbs;
    private static final String NOUNS_FILE_PATH = "src/main/resources/static/files/nouns.txt";
    private static final String ADJECTIVES_FILE_PATH = "src/main/resources/static/files/adjs.txt";
    private static final String VERBS_FILE_PATH = "src/main/resources/static/files/better_verbs.json";

    /**
     * Private constructor for singleton pattern.
     * Initializes word collections and loads words from files.
     */
    private Dictionary() {
        nouns = new java.util.ArrayList<>();
        adjs = new java.util.ArrayList<>();
        verbs = new java.util.ArrayList<>();
        loadAllNouns();
        loadAllAdjs();
        loadAllVerbs();
    }

    /**
     * Gets the singleton instance of Dictionary.
     * Creates a new instance if one doesn't exist.
     *
     * @return The singleton instance of Dictionary
     */
    public static Dictionary getInstance() {
        if (INSTANCE == null)
            INSTANCE = new Dictionary();
        return INSTANCE;
    }

    /**
     * Loads all verbs from the JSON file specified in VERBS_FILE_PATH.
     * Each verb entry contains multiple forms of the verb.
     */
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

    /**
     * Loads all nouns from the text file specified in NOUNS_FILE_PATH.
     */
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

    /**
     * Loads all adjectives from the text file specified in ADJECTIVES_FILE_PATH.
     */
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

    /**
     * Returns a random noun from the collection.
     *
     * @return A random Word object of type Noun, or null if collection is empty
     */
    public Word getRandomNoun() {
        if (nouns.isEmpty())
            return null;
        int idxRandom = (int) (Math.floor(Math.random() * nouns.size()));
        return nouns.get(idxRandom);
    }

    /**
     * Returns a random adjective from the collection.
     *
     * @return A random Word object of type Adjective, or null if collection is empty
     */
    public Word getRandomAdj() {
        if (adjs.isEmpty())
            return null;
        int idxRandom = (int) (Math.floor(Math.random() * adjs.size()));
        return adjs.get(idxRandom);
    }

    /**
     * Returns a random verb from the collection.
     *
     * @return A random Word object of type Verb, or null if collection is empty
     */
    public Word getRandomVerb() {
        if (verbs.isEmpty())
            return null;
        int idxRandom = (int) (Math.floor(Math.random() * verbs.size()));
        return verbs.get(idxRandom);
    }

    /**
     * Adds new words to their respective collections based on their type.
     * Duplicates are not added.
     *
     * @param wordsList List of pairs containing word and its type (NOUN, ADJ, or VERB)
     */
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
                Verb flag = new Verb(word);
                if (!verbs.contains(flag))
                    verbs.add(flag);
            }
        }
    }

    /**
     * Searches for a verb in the collection by its string representation.
     *
     * @param verb The string to search for
     * @return The found Word object or a new Verb object if not found
     */
    public Word getVerbFromString(String verb) {
        for (Word w : verbs) {
            if (w.getWord().equals(verb)) {
                return w;
            }
        }
        return new Verb(verb);
    }

}
