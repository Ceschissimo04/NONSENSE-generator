package com.test.nonsenseGenerator.model;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;

class DictionaryTest {

    /**
     * Test for getInstance() method in the Dictionary class.
     * This method ensures that the Singleton instance is properly created and always the same.
     */

    @Test
    void testGetInstanceReturnsSameInstance() {
        // Act
        Dictionary instance1 = Dictionary.getInstance();
        Dictionary instance2 = Dictionary.getInstance();

        // Assert
        assertNotNull(instance1, "getInstance should not return null");
        assertSame(instance1, instance2, "getInstance should return the same instance for multiple calls");
    }

    @Test
    void testGetInstanceInitializesNounsAdjectivesAndVerbs() {
        // Act
        Dictionary instance = Dictionary.getInstance();

        // Assert
        assertNotNull(instance.getRandomNoun(), "Nouns list should be initialized and not empty");
        assertNotNull(instance.getRandomAdj(), "Adjectives list should be initialized and not empty");
        assertNotNull(instance.getRandomVerb(), "Verbs list should be initialized and not empty");
    }
}