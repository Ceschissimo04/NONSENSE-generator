package com.test.nonsenseGenerator.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Template class that manages and provides access to template strings.
 * Templates are loaded from a file and can be retrieved randomly or all at once.
 */
public class Template {
private static ArrayList<String> allTemplates;
private static final String TEMPLATES_PATH = "src/main/resources/static/templates/templates.txt";

    static {
        allTemplates = new ArrayList<>();
        getFromFile();
    }

    /**
     * Returns all available templates.
     *
     * @return ArrayList containing all template strings
     */
    public static ArrayList<String> getAllTemplate(){
        return allTemplates;
    }

    /**
     * Returns a random template from the available templates.
     *
     * @return A randomly selected template string
     */
    public static String getRandom() {
        int intRandom = (int) (Math.floor(Math.random() * allTemplates.size()));
        return allTemplates.get(intRandom);
    }

    /**
     * Loads templates from a file into the allTemplates ArrayList.
     * The file is expected to be at src/main/resources/static/templates/templates.txt
     */
    private static void getFromFile() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(TEMPLATES_PATH));
            while (br.ready()) allTemplates.add(br.readLine());
            br.close();
        } catch (Exception e) { e.printStackTrace(); }
    }

    /**
     * Extracts words enclosed in square brackets from the input string.
     *
     * @param input The string to extract bracketed words from
     * @return ArrayList of strings found between square brackets
     */
    public static ArrayList<String> extractBracketWords(String input) {
        ArrayList<String> words = new ArrayList<>();
        Pattern pattern = Pattern.compile("\\[(.*?)\\]");
        Matcher matcher = pattern.matcher(input);

        while (matcher.find()) {
            words.add(matcher.group(1));
        }

        return words;
    }
}