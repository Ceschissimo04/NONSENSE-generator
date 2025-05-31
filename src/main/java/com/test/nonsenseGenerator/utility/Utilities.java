package com.test.nonsenseGenerator.utility;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Utility class providing methods for tree construction, HTML generation and string manipulation.
 */
public class Utilities {
    /**
     * Builds a tree structure from a JSON object containing tokens and their dependencies.
     *
     * @param json The JSON object containing tokens and dependency information
     * @return The root node of the constructed tree
     */
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

            if (!label.equals("ROOT")) {
                nodes.get(fatherIndex).addChild(nodes.get(i));
            } else {
                rootIndex = i;
            }
        }

        return nodes.get(rootIndex);
    }

    /**
     * Generates HTML representation of a tree structure.
     *
     * @param root The root node of the tree to convert to HTML
     * @return String containing HTML representation of the tree
     */
    public static String generateTreeHTML(TreeNode root) {
        StringBuilder html = new StringBuilder();
        html.append("<div class=\"tree\">\n");
        html.append(generaNodoHTML(root));
        html.append("</div>\n");
        return html.toString();
    }

    /**
     * Recursively generates HTML representation of a tree node and its children.
     *
     * @param node The tree node to convert to HTML
     * @return String containing HTML representation of the node and its subtree
     */
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

    /**
     * Replaces the first occurrence of a word in brackets with the specified replacement.
     *
     * @param input       The input string containing bracketed words
     * @param replacement The string to replace the first bracketed word with
     * @return The modified string, or null if input is null
     */
    public static String replaceFirstBracketWord(String input, String replacement) {
        if (input == null)
            return null;
        return input.replaceFirst("\\[[^\\]]+\\]", replacement);
    }
}
