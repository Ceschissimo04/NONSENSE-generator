package com.test.elementiIngegneria.controller;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.test.elementiIngegneria.model.Node;

public class Analyzer {
    public static String getPartsOfText(String text, String language) {
        String query_output = Querier.analyzeTextSyntax(text, language);
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
        } catch (JSONException err) {
            System.out.println("error");
        }
        return "Something went wrong";
    }

    // nome da cambiare
    public static ArrayList<String[]> getElementsOfText(String text, String language) {
        String query_output = Querier.analyzeTextSyntax(text, language);
        ArrayList<String[]> result = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(query_output);
            JSONArray tokens = jsonObject.getJSONArray("tokens");
            for (int i = 0; i < tokens.length(); i++) {
                JSONObject token = tokens.getJSONObject(i);
                String word = token.getJSONObject("text").getString("content");
                String partOfSpeech = token.getJSONObject("partOfSpeech").getString("tag");

                if (partOfSpeech.equals("NOUN") || partOfSpeech.equals("VERB") || partOfSpeech.equals("ADJ")) {
                    result.add(new String[] { word, partOfSpeech });
                }
            }
            return result;
        } catch (JSONException err) {
            System.out.println("error");
        }
        return null;
    }

    // nome da cambiare
    public static ArrayList<String[]> getElementsOfTextLemma(String text, String language) {
        String query_output = Querier.analyzeTextSyntax(text, language);
        ArrayList<String[]> result = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(query_output);
            JSONArray tokens = jsonObject.getJSONArray("tokens");
            for (int i = 0; i < tokens.length(); i++) {
                JSONObject token = tokens.getJSONObject(i);
                String lemma = token.getString("lemma");
                String partOfSpeech = token.getJSONObject("partOfSpeech").getString("tag");

                if (partOfSpeech.equals("NOUN") || partOfSpeech.equals("VERB") || partOfSpeech.equals("ADJ")) {
                    result.add(new String[] { lemma, partOfSpeech });
                }
            }
            return result;
        } catch (JSONException err) {
            System.out.println("error");
        }
        return null;
    }

    public static String getSyntaxTree(String text, String language) {
        String query_output = Querier.analyzeTextSyntax(text, language);
        try {
            JSONObject jsonObject = new JSONObject(query_output);
            JSONArray tokens = jsonObject.getJSONArray("tokens");
            ArrayList<Node> nodes = new ArrayList<>(tokens.length());
            for (int i = 0; i < tokens.length(); i++) {
                nodes.add(new Node());
            }

            int rootIndex = -1;
            for (int i = 0; i < tokens.length(); i++) {
                JSONObject token = tokens.getJSONObject(i);
                String word = token.getJSONObject("text").getString("content");
                String label = token.getJSONObject("dependencyEdge").getString("label");
                int fatherIndex = token.getJSONObject("dependencyEdge").getInt("headTokenIndex");

                nodes.get(i).setName(label + ": " + word);

                // don't add father to root
                if (!label.equals("ROOT")) {
                    nodes.get(fatherIndex).addChild(nodes.get(i));
                } else {
                    rootIndex = i;
                }
            }
            return generaHTML(nodes.get(rootIndex));
        } catch (JSONException err) {
            System.out.println("error");
        }
        return "Something went wrong";
    }

    public static String generaHTML(Node radice) {
        StringBuilder html = new StringBuilder();
        html.append("<div class=\"tree\">\n");
        html.append(generaNodoHTML(radice));
        html.append("</div>\n");
        return html.toString();
    }

    private static String generaNodoHTML(Node nodo) {
        StringBuilder sb = new StringBuilder();
        sb.append("<ul>\n");
        sb.append("<li>\n");
        sb.append("<div class=\"node\">").append(nodo.getName()).append("</div>\n");

        if (!nodo.getChildren().isEmpty()) {
            for (Node figlio : nodo.getChildren()) {
                sb.append(generaNodoHTML(figlio));
            }
        }

        sb.append("</li>\n");
        sb.append("</ul>\n");
        return sb.toString();
    }

}
