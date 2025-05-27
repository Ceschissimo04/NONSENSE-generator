package com.test.elementiIngegneria.model;

public class Verb extends Word {
    
    //private final String default_tense;
    // the word attribute is used as the default_tense
    private final String present_tense;
    private final String past_tense;
    private final String future_tense;

    public Verb(String word) {
        super(word, "verb");
        this.present_tense = null;
        this.past_tense = null;
        this.future_tense = null;
    }

    public Verb(String word, String present_tense, String past_tense, String future_tense) {
        super(word, "verb");
        this.present_tense = present_tense;
        this.past_tense = past_tense;
        this.future_tense = future_tense;
    }

    @Override
    public String toString() {
        return getWord();
    }
    
    public String getPresentTense() {
        return present_tense;
    }

    public String getPastTense() {
        return past_tense;
    }

    public String getFutureTense() {
        return future_tense;
    }

    public boolean has_conjugations(){
        return present_tense != null && past_tense != null && future_tense != null;
    }
}
