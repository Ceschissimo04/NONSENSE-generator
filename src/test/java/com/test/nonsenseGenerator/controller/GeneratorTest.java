package com.test.nonsenseGenerator.controller;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import com.test.nonsenseGenerator.model.*;
import com.test.nonsenseGenerator.utility.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

public class GeneratorTest {

    /**
     * Test class for the {@link Generator#generateSentences(List, String, String)} method.
     * The method generates sentences based on given elements, a template, and tense.
     */

    @Test
    public void testGenerateSentencesWithDefaultTemplateAndPresentTense() {
        List<Pair<String, String>> elements = new ArrayList<>();
        elements.add(new Pair<>("cat", "NOUN"));
        elements.add(new Pair<>("red", "ADJ"));
        elements.add(new Pair<>("runs", "VERB"));
        String defaultTemplate = "A [adjective] [noun] [verb]";
        String tense = "present";

        Dictionary dictionaryFlag = Dictionary.getInstance();

        Dictionary dictionaryMock = mock(dictionaryFlag.getClass());
        when(dictionaryMock.getRandomNoun()).thenReturn(new Noun("dog"));
        when(dictionaryMock.getRandomAdj()).thenReturn(new Adjective("blue"));
        when(dictionaryMock.getRandomVerb()).thenReturn(new Verb("jump", "jumps", "jumped", "will jump"));

        Verb verbMock = mock(Verb.class);
        when(verbMock.getPresentTense()).thenReturn("runs");
        when(verbMock.has_conjugations()).thenReturn(true);
        when(dictionaryMock.getVerbFromString("runs")).thenReturn(verbMock);

        List<String> result = Generator.generateSentences(elements, defaultTemplate, tense);

        assertEquals(1, result.size());
        assertEquals("A red cat runs", result.getFirst());
    }

    @Test
    public void testGenerateSentencesWithRandomTemplate() {
        List<Pair<String, String>> elements = new ArrayList<>();
        elements.add(new Pair<>("apple", "NOUN"));
        elements.add(new Pair<>("green", "ADJ"));
        elements.add(new Pair<>("eats", "VERB"));
        String defaultTemplate = "default";
        String tense = "present";

        MockedStatic<Template> mockStatic = mockStatic(Template.class);

        mockStatic.when(Template::getRandom).thenReturn("[noun] [verb] [adjective]");
        when(Template.extractBracketWords("[noun] [verb] [adjective]"))
                .thenReturn(new ArrayList<>(Arrays.asList("noun","verb", "adjective")));

        Dictionary dictionaryFlag = Dictionary.getInstance();

        Dictionary dictionaryMock = mock(dictionaryFlag.getClass());
        when(dictionaryMock.getRandomAdj()).thenReturn(new Adjective("yellow"));

        Verb verbMock = mock(Verb.class);
        when(dictionaryMock.getVerbFromString("eats")).thenReturn(verbMock);
        when(verbMock.getPresentTense()).thenReturn("eats");
        when(verbMock.has_conjugations()).thenReturn(true);

        List<String> result = Generator.generateSentences(elements, defaultTemplate, tense);

        assertEquals(1, result.size());
        assertEquals("apple eats green", result.getFirst());
    }

    @Test
    public void testGenerateSentencesWithEmptyElements() {
        List<Pair<String, String>> elements = new ArrayList<>();
        String defaultTemplate = "default";
        String tense = "future";

        Dictionary dictionaryMockFlag = Dictionary.getInstance();
        Dictionary dictionaryMock = mock(dictionaryMockFlag.getClass());
        when(dictionaryMock.getRandomNoun()).thenReturn(new Noun("dog"));
        when(dictionaryMock.getRandomAdj()).thenReturn(new Adjective("fast"));

        List<String> result = Generator.generateSentences(elements, defaultTemplate, tense);

        assertEquals(0, result.size());
    }

    @Test
    public void testGenerateSentencesWithUnrecognizedPoS() {
        List<Pair<String, String>> elements = new ArrayList<>();
        elements.add(new Pair<>("beautifully", "ADV"));
        elements.add(new Pair<>("fish", "UNKNOWN"));
        String defaultTemplate = "[adjective] [noun] [verb]";
        String tense = "past";

        Dictionary dictionaryMockFlag = Dictionary.getInstance();
        Dictionary dictionaryMock = mock(dictionaryMockFlag.getClass());
        when(dictionaryMock.getRandomNoun()).thenReturn(new Noun("bird"));
        when(dictionaryMock.getRandomAdj()).thenReturn(new Adjective("small"));
        when(dictionaryMock.getRandomVerb()).thenReturn(new Verb("fly", "flies", "flew", "will fly"));

        List<String> result = Generator.generateSentences(elements, defaultTemplate, tense);

        assertEquals(0, result.size());
    }

    @Test
    public void testGenerateSentencesWithCustomTense() {
        List<Pair<String, String>> elements = new ArrayList<>();
        elements.add(new Pair<>("run", "VERB"));
        String defaultTemplate = "[noun] can [verb]";
        String tense = "future";

        Dictionary dictionaryMockFlag = Dictionary.getInstance();
        Dictionary dictionaryMock = mock(dictionaryMockFlag.getClass());
        when(dictionaryMock.getRandomNoun()).thenReturn(new Noun("cat"));;

        Verb verbMock = mock(Verb.class);
        when(dictionaryMock.getVerbFromString("run")).thenReturn(verbMock);
        when(verbMock.getFutureTense()).thenReturn("will run");
        when(verbMock.has_conjugations()).thenReturn(true);

        List<String> result = Generator.generateSentences(elements, defaultTemplate, tense);

        assertEquals(1, result.size());
    }
}