package com.test.elementiIngegneria.controller;

import java.util.ArrayList;
import java.util.List;

import com.test.elementiIngegneria.model.Adjectives;
import com.test.elementiIngegneria.model.Nouns;
import com.test.elementiIngegneria.model.Template;
import com.test.elementiIngegneria.model.Verbs;
import com.test.elementiIngegneria.utility.Pair;
import com.test.elementiIngegneria.utility.Utilities;

public class Generator {
    public static List<String> generateSentences(List<Pair<String, String>> elements, String template, String tense) {
        // 3 liste (1 nouns, 1 adjectives, 1 verbs)
        List<String> nouns = new ArrayList<>();
        List<String> adjectives = new ArrayList<>();
        List<String> verbs = new ArrayList<>();
        List<String> sentences = new ArrayList<>();
        Nouns nounsObj = new Nouns();
        Adjectives adjectivesObj = new Adjectives();
        Verbs verbsObj = new Verbs();

        for (Pair<String, String> s : elements) {
            String partOfSpeech = s.getSecond();
            switch (partOfSpeech) {
                case "NOUN":
                    nouns.add(s.getFirst());
                    break;
                case "ADJ":
                    adjectives.add(s.getFirst());
                    break;
                case "VERB":
                    verbs.add(s.getFirst());
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
        List<String> simplifiedTemplate = Template.extractBracketWords(template);

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
                currentSentence = Utilities.replaceFirstBracketWord(currentSentence, currentWord);
            }
            sentences.add(currentSentence);

            // TODO: quando si sceglie il template dalla select non va preso random ma va mantenuto lo stesso; passare un flag nella funzione?
            template = Template.getRandom();
            simplifiedTemplate = Template.extractBracketWords(template);
            currentSentence = template;
        }

        return sentences;
    }
}