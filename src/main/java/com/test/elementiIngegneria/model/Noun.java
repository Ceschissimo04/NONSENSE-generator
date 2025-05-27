package com.test.elementiIngegneria.model;

public class Noun extends Word {
    
    public Noun(String word) {
        super(word, "noun");
    }

    @Override
    public String toString() {
        return getWord();
    }
    
}
