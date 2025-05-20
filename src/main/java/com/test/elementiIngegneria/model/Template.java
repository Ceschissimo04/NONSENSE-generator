package com.test.elementiIngegneria.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Template {
private ArrayList<String> allTemplates;

    public Template(){
        allTemplates = new ArrayList<>();
        getFromFile();
    }

    public ArrayList<String> getAllTemplate(){
        return allTemplates;
    }

    public String getRandom(String optional) {
        int intRandom = (int) (Math.floor(Math.random() * allTemplates.size()));
        return allTemplates.get(intRandom);
    }

    public void getFromFile() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("src/main/resources/static/templates/template.txt"));
            while (br.ready()) allTemplates.add(br.readLine());
            br.close();
        } catch (Exception e) { e.printStackTrace(); }
    }
    
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