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
        ArrayList<String[]> elements = new ArrayList<>();

        //controlla se la frase è vuota
        if (sentence == null || sentence.isEmpty()) {
            return null;
        }
        else{

            //mescola elements, per creare un po' di rqandomità

            elements = Analyzer.getElementsOfText(sentence, "it");
            for(String[] s : elements) {
                String partOfSpeech = s[0];
                switch (partOfSpeech) {
                    case "NOUN":
                        nouns.add(s[0]);
                        break;
                    case "ADJ":
                        adjectives.add(s[0]);
                        break;
                    case "VERB":
                        verbs.add(s[0]);
                        break;
                    default:
                        // handle other cases if needed
                        break;
                }
            }

        } 
        
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