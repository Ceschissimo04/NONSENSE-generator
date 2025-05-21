package com.test.elementiIngegneria.controller;

import java.util.ArrayList;

import com.test.elementiIngegneria.model.Adjectives;
import com.test.elementiIngegneria.model.Nouns;
import com.test.elementiIngegneria.model.Template;
import com.test.elementiIngegneria.model.Verbs;

public class Generator {
    public ArrayList<String> generateStrings(String sentence, String template, String tense) {
        // 3 liste (1 nouns, 1 adjectives, 1 verbs)
        ArrayList<String> nouns = new ArrayList<>();
        ArrayList<String> adjectives = new ArrayList<>();
        ArrayList<String> verbs = new ArrayList<>();
        ArrayList<String> sentences = new ArrayList<>();
        ArrayList<String[]> elements = new ArrayList<>();
        Nouns nounsObj = new Nouns();
        Adjectives adjectivesObj = new Adjectives();
        Verbs verbsObj = new Verbs();

        // controlla se la frase è vuota
        if (sentence == null || sentence.isEmpty()) {
            return null;
        } else {

            // TODO: randomizzare le parole sugli array

            elements = Analyzer.getElementsOfText(sentence, "en");
            for (String[] s : elements) {
                String partOfSpeech = s[1];
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

        // Simplify the template
        ArrayList<String> simplifiedTemplate = Template.extractBracketWords(template);

        // TODO: Potrei aver notato errori a volte negli inserimenti di parole in
        // eccesso, controllare
        while (!nouns.isEmpty() || !adjectives.isEmpty() || !verbs.isEmpty()) {
            String currentSentence = template;
            System.out.println("Current template: " + template);
            // ========Fill template=========
            for (String s : simplifiedTemplate) {
                if (s.equals("noun")) {
                    if (!nouns.isEmpty()) {
                        currentSentence = replaceFirstBracketWord(currentSentence, nouns.get(0));
                        nouns.remove(0);
                    } else {
                        currentSentence = replaceFirstBracketWord(currentSentence, nounsObj.getRandom(""));
                    }
                } else if (s.equals("adjective")) {
                    if (!adjectives.isEmpty()) {
                        currentSentence = replaceFirstBracketWord(currentSentence, adjectives.get(0));
                        adjectives.remove(0);
                    } else {
                        currentSentence = replaceFirstBracketWord(currentSentence, adjectivesObj.getRandom(""));
                    }
                } else if (s.equals("verb")) {
                    // TODO: Manca la logica per i verbi
                    if (!verbs.isEmpty()) {
                        currentSentence = replaceFirstBracketWord(currentSentence, verbs.get(0));
                        verbs.remove(0);
                    } else {
                        currentSentence = replaceFirstBracketWord(currentSentence, verbsObj.getRandom(tense));
                    }
                }
            }
            sentences.add(currentSentence);

            simplifiedTemplate = Template.extractBracketWords(Template.getRandom(template));
        }

        return sentences;
    }

    public static String replaceFirstBracketWord(String input, String replacement) {
        if (input == null)
            return null;
        return input.replaceFirst("\\[[^\\]]+\\]", replacement);
    }
}