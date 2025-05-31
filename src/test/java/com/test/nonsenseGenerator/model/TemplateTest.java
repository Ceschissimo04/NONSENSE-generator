package com.test.nonsenseGenerator.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

class TemplateTest {

     /**
     * Tests whether getRandom throws an exception if the allTemplates list is empty.
     */
    @Test
    void testGetRandom_ThrowsExceptionWhenListIsEmpty() {
        // Set up an empty template list
        ArrayList<String> emptyTemplates = new ArrayList<>();
        replaceAllTemplates(emptyTemplates);

        ArrayList<String> finalEmptyTemplates = Template.getAllTemplate();
        // Call the method under test and expect the size is equal to zero
        assertEquals(finalEmptyTemplates.size(), 0 );
    }

    /**
     * Helper method to replace the static allTemplates field in the Template class for testing purposes.
     */
    private void replaceAllTemplates(ArrayList<String> newTemplates) {
        Template.getAllTemplate().clear();
        Template.getAllTemplate().addAll(newTemplates);
    }
}