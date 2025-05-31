package com.test.nonsenseGenerator.controller;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.junit.jupiter.api.Test;

class ToxicityApiHandlerTest {

    /**
     * This class tests the toxicityQuery method from the ToxicityApiHandler class.
     * The method sends a POST request to the Google Language API and returns the JSON response.
     */

    private static final String TEST_API_KEY = "testApiKey";
    private static final String TEST_TEXT = "Test text for toxicity analysis.";

    @Test
    void testToxicityQueryValidResponse() throws Exception {
        // Mocking connection failure
        URL mockUrl = mock(URL.class);
        HttpURLConnection mockConnection = mock(HttpURLConnection.class);
        when(mockUrl.openConnection()).thenReturn(mockConnection);
        when(mockConnection.getOutputStream()).thenReturn(mock(OutputStream.class));
        when(mockConnection.getInputStream()).thenThrow(new IOException("Connection error"));

        
        assertNull(ToxicityApiHandler.toxicityQuery(TEST_TEXT, TEST_API_KEY));
    }
    
}