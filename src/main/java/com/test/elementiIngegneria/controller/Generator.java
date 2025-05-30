package com.test.elementiIngegneria.controller;

import java.util.ArrayList;
import java.util.List;

import com.test.elementiIngegneria.model.Dictionary;
import com.test.elementiIngegneria.model.Template;
import com.test.elementiIngegneria.model.Verb;
import com.test.elementiIngegneria.utility.Pair;
import com.test.elementiIngegneria.utility.Utilities;

public class Generator {
    public static List<String> generateSentences(List<Pair<String, String>> elements, String defaultTemplate, String tense) {
        // 3 liste (1 nouns, 1 adjectives, 1 verbs)
        List<String> nouns = new ArrayList<>();
        List<String> adjectives = new ArrayList<>();
        List<String> verbs = new ArrayList<>();
        List<String> sentences = new ArrayList<>();
        boolean changeTemplate = defaultTemplate.equals("default");

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

        String template = defaultTemplate;
        String currentSentence = null;
        // Simplify the template
        List<String> simplifiedTemplate = null;

        while (!nouns.isEmpty() || !adjectives.isEmpty() || !verbs.isEmpty()) {
            if(changeTemplate){
                template = Template.getRandom();
            }
            else{
                template = defaultTemplate;
            }
            currentSentence = template;
            simplifiedTemplate = Template.extractBracketWords(template);
            // ========Fill template=========
            for (String currentType : simplifiedTemplate) {
                String currentWord = "";
                switch (currentType) {
                    case "noun":
                        if (!nouns.isEmpty()) {
                            currentWord = nouns.get(0);
                            nouns.remove(0);
                        } else {
                            currentWord = Dictionary.getInstance().getRandomNoun().getWord();
                        }
                        break;
                    case "adjective":
                        if (!adjectives.isEmpty()) {
                            currentWord = adjectives.get(0);
                            adjectives.remove(0);
                        } else {
                            currentWord = Dictionary.getInstance().getRandomAdj().getWord();
                        }
                        break;
                    case "verb":
                        Verb v = null;
                        if (!verbs.isEmpty()) {
                            // look for conjugation in the dictionary
                            v = (Verb) Dictionary.getInstance().getVerbFromString(verbs.get(0));
                            verbs.remove(0);
                        } else {
                            v = (Verb) Dictionary.getInstance().getRandomVerb();
                        }
                        if (!v.has_conjugations()) {
                            currentWord = v.getWord();
                        } else {
                            if (tense == null || tense.isEmpty() || tense.equals("default")) {
                                String[] tenses = { "present", "past", "future" };
                                tense = tenses[(int) (Math.random() * tenses.length)];
                            }
                            switch (tense.toLowerCase()) {
                                case "present":
                                    currentWord = v.getPresentTense();
                                    break;
                                case "past":
                                    currentWord = v.getPastTense();
                                    break;
                                case "future":
                                    currentWord = v.getFutureTense();
                                    break;
                                default:
                                    currentWord = v.getWord();
                                    break;
                            }
                        }
                        break;
                    default:
                        break;
                }
                currentSentence = Utilities.replaceFirstBracketWord(currentSentence, currentWord);
            }
            sentences.add(currentSentence);

            simplifiedTemplate = Template.extractBracketWords(template);
            currentSentence = template;
        }

        return sentences;
    }
}