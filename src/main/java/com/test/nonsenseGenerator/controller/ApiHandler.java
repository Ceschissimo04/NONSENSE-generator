package com.test.nonsenseGenerator.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.test.nonsenseGenerator.utility.Pair;
import com.test.nonsenseGenerator.utility.TreeNode;
import com.test.nonsenseGenerator.utility.Utilities;


/**
 * Handler class for making API calls to various services.
 * Implements the Singleton pattern to ensure only one instance exists.
 */
public class ApiHandler {
    private final String API_KEY_FILE = "config.json";
    private final String apiKey;
    private static ApiHandler INSTANCE;

    /**
     * Private constructor for Singleton pattern.
     * Loads the API key from configuration file.
     *
     * @throws IOException if the API key file cannot be read
     */
    private ApiHandler() throws IOException {
        apiKey = ApiKeyLoader.getApiKey(API_KEY_FILE);
    }

    /**
     * Gets the singleton instance of ApiHandler.
     *
     * @return The singleton instance of ApiHandler
     * @throws IOException if there's an error initializing the handler
     */
    public static ApiHandler getInstance() throws IOException {
        if (INSTANCE == null) {
            INSTANCE = new ApiHandler();
        }
        return INSTANCE;
    }

    /**
     * Creates a syntax tree from a given sentence using API.
     *
     * @param sentence The input sentence to analyze
     * @return A TreeNode representing the syntax structure of the sentence
     */
    public TreeNode getSyntaxTree(String sentence) {
        return Utilities.buildTreeFromJson(SyntaxApiHandler.SyntaxQuery(sentence, apiKey));
    }

    /**
     * Analyzes text and returns a string with parts of speech marked for nouns, verbs, and adjectives.
     *
     * @param text The input text to analyze
     * @return A formatted string with parts of speech marked in brackets
     */
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
                if (i > 0 && !partOfSpeech.equals("PUNCT")) {
                    syntaxTree.append(" ");
                }
                syntaxTree.append(word);
            }
        }
        return syntaxTree.toString().trim();
    }

    /**
     * Extracts words and their parts of speech from text.
     *
     * @param text The input text to analyze
     * @return ArrayList of String arrays containing [word, partOfSpeech]
     */
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

    /**
     * Extracts lemmas and their parts of speech from text.
     *
     * @param text The input text to analyze
     * @return List of Pairs containing lemma and part of speech
     */
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

    /**
     * Analyzes the toxicity of a given sentence.
     *
     * @param sentence The input sentence to analyze
     * @return Pair containing category of toxicity and confidence score (0-100)
     */
    public Pair<String, Integer> getToxicityScore(String sentence) {
        JSONObject json = ToxicityApiHandler.toxicityQuery(sentence, apiKey);
        if (json == null) {
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

    /**
     * Analyzes the toxicity of multiple sentences.
     *
     * @param sentences List of sentences to analyze
     * @return List of Pairs containing toxicity category and confidence score for each sentence
     */
    public List<Pair<String, Integer>> getToxicityScoreList(List<String> sentences) {
        List<Pair<String, Integer>> results = new ArrayList<>();
        for (String sentence : sentences) {
            Pair<String, Integer> result = getToxicityScore(sentence);
            results.add(new Pair<>(result.getFirst(), result.getSecond()));
        }
        return results;
    }
}
