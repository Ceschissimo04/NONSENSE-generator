package com.test.elementiIngegneria.controller;

import java.util.ArrayList;
import com.test.elementiIngegneria.model.Template;

public class Generator {
    public ArrayList<String> generateStrings(String sentence, String template, String tense) {
        //3 liste (1 nouns, 1 adjectives, 1 verbs)
        ArrayList<String> nouns = new ArrayList<>();
        ArrayList<String> adjectives = new ArrayList<>();
        ArrayList<String> verbs = new ArrayList<>();
        ArrayList<String> sentences = new ArrayList<>();

        //controlla se la frase è vuota
        if (sentence == null || sentence.isEmpty()) {
            return null;
        }
        else{
            //controllare il tipo delle parole(nouns/adjectives/verbs)
            //=>chiamata api a qualcosa che ti da il tipo della parola
            //aggiungi nouns/adjectives/verbs alle rispettive liste
            //mescola le liste, per creare un po' di rqandomità
        } 
        //prendi il template principale e riempilo con le liste
        
        //semplifico il template
        ArrayList<String> simplifiedTemplate = Template.extractBracketWords(template);
        int idx=0;

        while(!nouns.isEmpty() && !adjectives.isEmpty() && !verbs.isEmpty()){
            //==riempi template=========
            for (String s : simplifiedTemplate) {
                if (s.equals("noun")) {
                    if(!nouns.isEmpty()){
                        sentences.set(idx, replaceFirstBracketWord(template, nouns.get(0)));
                        //elimina nouns.get(0) dalla lista
                    }else{
                        //getRandomNouns
                    }
                } else if (s.equals("adjective")) {
                    
                    if(!adjectives.isEmpty()){
                        sentences.set(idx, replaceFirstBracketWord(template, adjectives.get(0)));
                        //elimina adjectives.get(0) dalla lista
                    }else{
                        //getRandomAdjective
                    }
                } else if (s.equals("verb")) {
                    if(!verbs.isEmpty()){
                        //coniuga il verbo
                        sentences.set(idx, replaceFirstBracketWord(template, verbs.get(0)));
                        //elimina verbs.get(0) dalla lista
                    }else{
                        //getRandomVerb
                        //coniuga
                    }
                    
                }
            }
            //=====================

            simplifiedTemplate = Template.extractBracketWords(Template.getRandom(template));
            idx++;
        }

        return sentences;
    }

    public static String replaceFirstBracketWord(String input, String replacement) {
        if (input == null) return null;
        return input.replaceFirst("\\[[^\\]]+\\]", replacement);
    }
}