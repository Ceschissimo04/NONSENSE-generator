package com.test.elementiIngegneria.controller;

import java.util.ArrayList;

public class Generator {
    public String[] generateStrings(String sentence, String template, String tense) {
        //3 liste (1 nouns, 1 adjectives, 1 verbs)
        ArrayList<String> nouns = new ArrayList<>();
        ArrayList<String> adjectives = new ArrayList<>();
        ArrayList<String> verbs = new ArrayList<>();

        //controlla se la frase è vuota
        if (sentence == null || sentence.isEmpty()) {
            return new String[0];
        }
        else{
            //controllare il tipo delle parole(nouns/adjectives/verbs)
            
            //aggiungi nouns/adjectives/verbs alle rispettive liste
           
        }
        //prendi il template principale e riempilo con le liste

        //due opzioni: getRandomTempalate() finche non si svuotano le liste
        //oppure cerchi il template migliore e lo riempi con le liste



        return new String[0];
    }

}