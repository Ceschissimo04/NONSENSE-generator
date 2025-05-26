package com.test.elementiIngegneria.controller;

import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.test.elementiIngegneria.model.TreeNode;
import com.test.elementiIngegneria.utility.Utilities;

public class ApiHandler {
    // TODO: gestire tutte le eccezioni se qualcosa non va
    // es: non trova la apikey, non sei connesso a internet, api google non
    // funziona...
    private final String API_KEY_FILE = "config.json";
    private final String apiKey;
    private static ApiHandler INSTANCE;

    private ApiHandler() throws IOException {
        apiKey = ApiKeyLoader.getApiKey(API_KEY_FILE);
    }

    // TODO: controllare che sta roba abbia sia fatta bene
    public static synchronized ApiHandler getInstance() throws IOException {
        if (INSTANCE == null) {
            INSTANCE = new ApiHandler();
        }
        return INSTANCE;
    }

    public TreeNode getSyntaxTree(String sentence) {
        return Utilities.buildTreeFromJson(SyntaxApiHandler.SyntaxQuery(sentence, apiKey));
    }

    public String getPartsOfText(String text) {
        JSONObject json = SyntaxApiHandler.SyntaxQuery(text, apiKey);
        JSONArray tokens = json.getJSONArray("tokens");
        StringBuilder syntaxTree = new StringBuilder();
        for (int i = 0; i < tokens.length(); i++) {
            JSONObject token = tokens.getJSONObject(i);
            String word = token.getJSONObject("text").getString("content");
            String partOfSpeech = token.getJSONObject("partOfSpeech").getString("tag");

            if (partOfSpeech.equals("NOUN") || partOfSpeech.equals("VERB") || partOfSpeech.equals("ADJ")) {
                syntaxTree.append(" [").append(partOfSpeech.toLowerCase()).append("]");
            } else {
                // add the " " before every token except the first one and punctuation.
                // this is just aestetics to have decently formatted output
                if (i > 0 && !partOfSpeech.equals("PUNCT")) {
                    syntaxTree.append(" ");
                }
                syntaxTree.append(word);
            }
        }
        return syntaxTree.toString().trim();
    }

    // nome da cambiare
    public ArrayList<String[]> getElementsOfText(String text) {
        ArrayList<String[]> result = new ArrayList<>();
        JSONObject json = SyntaxApiHandler.SyntaxQuery(text, apiKey);
        JSONArray tokens = json.getJSONArray("tokens");
        for (int i = 0; i < tokens.length(); i++) {
            JSONObject token = tokens.getJSONObject(i);
            String word = token.getJSONObject("text").getString("content");
            String partOfSpeech = token.getJSONObject("partOfSpeech").getString("tag");

            if (partOfSpeech.equals("NOUN") || partOfSpeech.equals("VERB") || partOfSpeech.equals("ADJ")) {
                result.add(new String[] { word, partOfSpeech });
            }
        }
        return result;
    }

    // nome da cambiare
    public ArrayList<String[]> getElementsOfTextLemma(String text) {
        JSONObject json = SyntaxApiHandler.SyntaxQuery(text, apiKey);
        ArrayList<String[]> result = new ArrayList<>();
        JSONArray tokens = json.getJSONArray("tokens");
        for (int i = 0; i < tokens.length(); i++) {
            JSONObject token = tokens.getJSONObject(i);
            String lemma = token.getString("lemma");
            String partOfSpeech = token.getJSONObject("partOfSpeech").getString("tag");

            if (partOfSpeech.equals("NOUN") || partOfSpeech.equals("VERB") || partOfSpeech.equals("ADJ")) {
                result.add(new String[] { lemma, partOfSpeech });
            }
        }
        return result;
    }

    public void getToxicityScore(String sentence) {
        // implementation here
    }
}
