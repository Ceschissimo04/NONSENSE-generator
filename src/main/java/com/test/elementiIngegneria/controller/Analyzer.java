package com.test.elementiIngegneria.controller;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Analyzer {
    public static String getSyntaxTree(String text, String language) {
        String query_output = Querier.doSomething(text, language);
        try {
            JSONObject jsonObject = new JSONObject(query_output);
            JSONArray tokens = jsonObject.getJSONArray("tokens");
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
       }catch (JSONException err){
        System.out.println("error");
       }
       return "Something went wrong";
    }

}
