package com.test.nonsenseGenerator.model;

/**
 * Represents an adjective word in the language model.
 * Extends the base Word class with specific functionality for adjectives.
 */
public class Adjective extends Word {

    /**
     * Constructs a new Adjective with the specified word.
     *
     * @param word The adjective word to be represented
     */
    public Adjective(String word) {
        super(word, "adj");
    }

    @Override
    public String toString() {
        return getWord();
    }
    
}
