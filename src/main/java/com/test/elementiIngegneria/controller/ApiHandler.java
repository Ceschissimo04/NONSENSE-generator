package com.test.elementiIngegneria.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.test.elementiIngegneria.model.TreeNode;
import com.test.elementiIngegneria.utility.Pair;
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

    // TODO: controllare che sta roba sia fatta bene
    public static ApiHandler getInstance() throws IOException {
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
    public List<Pair<String, String>> getElementsOfTextLemma(String text) {
        JSONObject json = SyntaxApiHandler.SyntaxQuery(text, apiKey);
        List<Pair<String, String>> result = new ArrayList<>();
        JSONArray tokens = json.getJSONArray("tokens");
        for (int i = 0; i < tokens.length(); i++) {
            JSONObject token = tokens.getJSONObject(i);
            String lemma = token.getString("lemma");
            String partOfSpeech = token.getJSONObject("partOfSpeech").getString("tag");

            if (partOfSpeech.equals("NOUN") || partOfSpeech.equals("VERB") || partOfSpeech.equals("ADJ")) {
                result.add(new Pair<>(lemma, partOfSpeech));
            }
        }
        return result;
    }

    public Pair<String, Integer> getToxicityScore(String sentence) {
        JSONObject json = ToxicityApiHandler.toxicityQuery(sentence, apiKey);
        if (json == null) {
            // TODO: gestire eccezioni
            return new Pair<>("Error in API call", -1);
        }

        JSONArray categories = json.getJSONArray("moderationCategories");
        String detectedCategory = "Valid";
        double highestConfidence = 0;

        for (int i = 0; i < categories.length(); i++) {
            JSONObject category = categories.getJSONObject(i);
            String categoryName = category.getString("name");
            double categoryScore = category.getDouble("confidence");

            if (categoryScore > highestConfidence) {
                highestConfidence = categoryScore;
                detectedCategory = categoryName;
            }
        }

        if (highestConfidence < ControllerApplication.TOXICITY_THRESHOLD) {
            return new Pair<>("Valid", (int) ((1 - highestConfidence) * 100));
        }
        return new Pair<>(detectedCategory, (int) (highestConfidence * 100));
    }

    public List<Pair<String, Integer>> getToxicityScoreList(List<String> sentences) {
        List<Pair<String, Integer>> results = new ArrayList<>();
        for (String sentence : sentences) {
            Pair<String, Integer> result = getToxicityScore(sentence);
            results.add(new Pair<>(result.getFirst(), result.getSecond()));
        }
        return results;
    }
}
