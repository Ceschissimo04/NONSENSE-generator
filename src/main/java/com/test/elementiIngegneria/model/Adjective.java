package com.test.elementiIngegneria.model;

public class Adjective extends Word {
    
    public Adjective(String word) {
        super(word, "adj");
    }

    @Override
    public String toString() {
        return getWord();
    }
    
}
