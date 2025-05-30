package com.test.elementiIngegneria.utility;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

public class Utilities {
    public static TreeNode buildTreeFromJson(JSONObject json) {
        JSONArray tokens = json.getJSONArray("tokens");
        ArrayList<TreeNode> nodes = new ArrayList<>(tokens.length());
        for (int i = 0; i < tokens.length(); i++) {
            nodes.add(new TreeNode());
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

        return nodes.get(rootIndex);
    }

    public static String generateTreeHTML(TreeNode root) {
        StringBuilder html = new StringBuilder();
        html.append("<div class=\"tree\">\n");
        html.append(generaNodoHTML(root));
        html.append("</div>\n");
        return html.toString();
    }

    private static String generaNodoHTML(TreeNode node) {
        StringBuilder sb = new StringBuilder();
        sb.append("<ul>\n");
        sb.append("<li>\n");
        sb.append("<div class=\"node\">").append(node.getName()).append("</div>\n");

        if (!node.getChildren().isEmpty()) {
            for (TreeNode child : node.getChildren()) {
                sb.append(generaNodoHTML(child));
            }
        }

        sb.append("</li>\n");
        sb.append("</ul>\n");
        return sb.toString();
    }

    public static String replaceFirstBracketWord(String input, String replacement) {
        if (input == null)
            return null;
        return input.replaceFirst("\\[[^\\]]+\\]", replacement);
    }
}
