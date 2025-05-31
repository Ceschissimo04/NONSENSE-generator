package com.test.elementiIngegneria.model;

/**
 * Abstract base class representing a word in the system.
 * Contains the word itself and its grammatical type.
 */
public abstract class Word {
    private final String word;
    private final String type;

    /**
     * Constructs a new Word with the specified word and type.
     *
     * @param word The actual word string
     * @param type The grammatical type of the word
     */
    public Word(String word, String type) {
        this.word = word;
        this.type = type;
    }

    /**
     * Gets the word string.
     *
     * @return The word string
     */
    public String getWord() {
        return word;
    }

    /**
     * Gets the grammatical type of the word.
     *
     * @return The grammatical type
     */
    public String getType() {
        return type;
    }

    /**
     * Returns a string representation of the word.
     *
     * @return The word string
     */
    @Override
    public String toString() {
        return word;
    }
}
