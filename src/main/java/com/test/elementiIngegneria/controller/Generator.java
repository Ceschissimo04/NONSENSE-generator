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
        }

        elements = Analyzer.getElementsOfTextLemma(sentence, ControllerApplication.DEFAULT_LANGUAGE);
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

        java.util.Collections.shuffle(nouns);
        java.util.Collections.shuffle(adjectives);
        java.util.Collections.shuffle(verbs);

        String currentSentence = template;
        // Simplify the template
        ArrayList<String> simplifiedTemplate = Template.extractBracketWords(template);

        while (!nouns.isEmpty() || !adjectives.isEmpty() || !verbs.isEmpty()) {
            System.out.println("Current template: " + template);
            // ========Fill template=========
            for (String currentType : simplifiedTemplate) {
                String currentWord = "";
                switch (currentType) {
                    case "noun":
                        if (!nouns.isEmpty()) {
                            currentWord = nouns.get(0);
                            nouns.remove(0);
                        } else {
                            currentWord = nounsObj.getRandom();
                        }
                        break;
                    case "adjective":
                        if (!adjectives.isEmpty()) {
                            currentWord = adjectives.get(0);
                            adjectives.remove(0);
                        } else {
                            currentWord = adjectivesObj.getRandom();
                        }
                        break;
                    case "verb":
                        if (!verbs.isEmpty()) {
                            currentWord = verbs.get(0);
                            verbs.remove(0);
                        } else {
                            currentWord = verbsObj.getRandom(tense);
                        }
                        break;
                    default:
                        break;
                }
                currentSentence = replaceFirstBracketWord(currentSentence, currentWord);
            }
            sentences.add(currentSentence);

            template = Template.getRandom();
            simplifiedTemplate = Template.extractBracketWords(template);
            currentSentence = template;
        }

        return sentences;
    }

    public static String replaceFirstBracketWord(String input, String replacement) {
        if (input == null)
            return null;
        return input.replaceFirst("\\[[^\\]]+\\]", replacement);
    }
}