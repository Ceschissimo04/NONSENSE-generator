package com.test.elementiIngegneria.model;

import java.util.HashMap;

public class Verbs implements Word {

    private HashMap<String, String[]> allVerbs;

    public Verbs(){
        allVerbs = new HashMap<>();
    }

    @Override
    public String getRandom() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getRandom'");
    }

    @Override
    public void getFromFile() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getFromFile'");
    }
    
}
