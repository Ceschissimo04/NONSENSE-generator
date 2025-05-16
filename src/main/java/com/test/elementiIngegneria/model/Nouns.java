package com.test.elementiIngegneria.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class Nouns implements Word {

    private ArrayList<String> allNouns;

    public Nouns(){
        allNouns = new ArrayList<>();
        getFromFile();
    }

    public ArrayList<String> getAllNouns(){
        return allNouns;
    }

    @Override
    public String getRandom() {
        int intRandom = (int) (Math.floor(Math.random() * allNouns.size()));
        return allNouns.get(intRandom);
    }

    @Override
    public void getFromFile() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("src/main/resources/static/json/nouns.txt"));
            while (br.ready()) allNouns.add(br.readLine());
            br.close();
        } catch (Exception e) { e.printStackTrace(); }
    }
    
}
