package com.test.elementiIngegneria.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class TemplateTest {

    /**
     * Tests the getRandom method of the Template class, which retrieves a random string
     * from the list of templates (allTemplates).
     * This test verifies that getRandom returns a value from the list and is not null.
     */
    @Test
    void testGetRandom_ReturnsValueInList() {
        // Set up the mocked data
        ArrayList<String> mockTemplates = new ArrayList<>();
        mockTemplates.add("Template1");
        mockTemplates.add("Template2");
        mockTemplates.add("Template3");

        // Use reflection to replace the allTemplates static field
        replaceAllTemplates(mockTemplates);

        // Call the method under test
        String randomTemplate = Template.getRandom();

        // Verify that the returned value is one of the mock templates
        assertTrue(mockTemplates.contains(randomTemplate),
                "getRandom should return a value that exists in the allTemplates list.");
    }

    /**
     * Tests whether getRandom throws an exception if the allTemplates list is empty.
     */
    @Test
    void testGetRandom_ThrowsExceptionWhenListIsEmpty() {
        // Set up an empty template list
        ArrayList<String> emptyTemplates = new ArrayList<>();
        replaceAllTemplates(emptyTemplates);

        // Call the method under test and verify that it throws an exception
        assertThrows(IndexOutOfBoundsException.class, Template::getRandom,
                "getRandom should throw IndexOutOfBoundsException when the allTemplates list is empty.");
    }

    /**
     * Helper method to replace the static allTemplates field in the Template class for testing purposes.
     */
    private void replaceAllTemplates(ArrayList<String> newTemplates) {
        try {
            var allTemplatesField = Template.class.getDeclaredField("allTemplates");
            allTemplatesField.setAccessible(true);
            allTemplatesField.set(null, newTemplates);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            fail("Failed to set up the test: " + e.getMessage());
        }
    }
}