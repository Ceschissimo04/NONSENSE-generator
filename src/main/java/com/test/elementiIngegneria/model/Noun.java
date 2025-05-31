package com.test.elementiIngegneria.model;

/**
 * Represents a noun word type that extends the base Word class.
 * This class is specifically used for words that function as nouns in sentences.
 */
public class Noun extends Word {

    /**
     * Constructs a new Noun object.
     *
     * @param word The string representation of the noun word
     */
    public Noun(String word) {
        super(word, "noun");
    }

    /**
     * Returns the string representation of this noun.
     *
     * @return The word as a string
     */
    @Override
    public String toString() {
        return getWord();
    }
    
}
