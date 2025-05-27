package com.test.elementiIngegneria.model;

public abstract class Word {
    private final String word;
    private final String type;

    public Word(String word, String type) {
        this.word = word;
        this.type = type;
    }
    public String getWord() {
        return word;
    }
    public String getType() {
        return type;
    }
    @Override
    public String toString() {
        return word;
    }
}
