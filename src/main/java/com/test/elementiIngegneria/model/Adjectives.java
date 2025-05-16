package com.test.elementiIngegneria.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class Adjectives implements Word {

    private ArrayList<String> allAdjectives;

    public Adjectives(){
        allAdjectives = new ArrayList<>();
        getFromFile();
    }

    @Override
    public String getRandom() {
        int intRandom = (int) (Math.floor(Math.random() * allAdjectives.size()));
        return allAdjectives.get(intRandom);
    }

    @Override
    public void getFromFile() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("src/main/resources/static/json/adjs.txt"));
            while (br.ready()) allAdjectives.add(br.readLine());
            br.close();
        } catch (Exception e) { e.printStackTrace(); }
    }
    
}
