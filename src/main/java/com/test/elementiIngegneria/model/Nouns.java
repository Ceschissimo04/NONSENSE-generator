package com.test.elementiIngegneria.model;

import java.util.ArrayList;

public class Nouns implements Word {

    private ArrayList<String> allNouns;

    public Nouns(){
        allNouns = new ArrayList<>();
    }

    @Override
    public String getRandom() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getRandom'");
    }

    @Override
    public Boolean getFromFile() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getFromFile'");
    }
    
}
