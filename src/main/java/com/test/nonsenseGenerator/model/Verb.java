package com.test.nonsenseGenerator.model;

/**
 * Represents a verb word with its different tense conjugations.
 * Extends the base Word class and adds tense-specific functionality.
 */
public class Verb extends Word {

    private final String present_tense;
    private final String past_tense;
    private final String future_tense;

    /**
     * Constructs a Verb with only its base form, without conjugations.
     *
     * @param word The base form of the verb
     */
    public Verb(String word) {
        super(word, "verb");
        this.present_tense = null;
        this.past_tense = null;
        this.future_tense = null;
    }

    /**
     * Constructs a Verb with all its tense conjugations.
     *
     * @param word          The base form of the verb
     * @param present_tense The present tense conjugation
     * @param past_tense    The past tense conjugation
     * @param future_tense  The future tense conjugation
     */
    public Verb(String word, String present_tense, String past_tense, String future_tense) {
        super(word, "verb");
        this.present_tense = present_tense;
        this.past_tense = past_tense;
        this.future_tense = future_tense;
    }

    /**
     * Returns the string representation of this verb.
     *
     * @return The base form of the verb
     */
    @Override
    public String toString() {
        return getWord();
    }

    /**
     * Gets the present tense conjugation of the verb.
     *
     * @return The present tense form
     */
    public String getPresentTense() {
        return present_tense;
    }

    /**
     * Gets the past tense conjugation of the verb.
     *
     * @return The past tense form
     */
    public String getPastTense() {
        return past_tense;
    }

    /**
     * Gets the future tense conjugation of the verb.
     *
     * @return The future tense form
     */
    public String getFutureTense() {
        return future_tense;
    }

    /**
     * Checks if this verb has all tense conjugations defined.
     *
     * @return true if all tenses are defined, false otherwise
     */
    public boolean has_conjugations(){
        return present_tense != null && past_tense != null && future_tense != null;
    }
}
