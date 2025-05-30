package com.test.elementiIngegneria.controller;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ToxicityApiHandlerTest {

    /**
     * This class tests the toxicityQuery method from the ToxicityApiHandler class.
     * The method sends a POST request to the Google Language API and returns the JSON response.
     */

    private static final String TEST_API_KEY = "testApiKey";
    private static final String TEST_TEXT = "Test text for toxicity analysis.";

    @Test
    void testToxicityQueryValidResponse() throws Exception {
        // Mocking a successful API response
        String mockResponse = "{\"success\": true}";
        URL mockUrl = mock(URL.class);
        HttpURLConnection mockConnection = mock(HttpURLConnection.class);
        when(mockUrl.openConnection()).thenReturn(mockConnection);
        when(mockConnection.getOutputStream()).thenReturn(mock(OutputStream.class));
        when(mockConnection.getInputStream()).thenReturn(new ByteArrayInputStream(mockResponse.getBytes()));

        // Setting up mock behavior
        ReflectionTestUtils.setField(ToxicityApiHandler.class, "API_URL_TOXICITY", "http://mockapi.com");

        // Using URL mock
        ReflectionTestUtils.setField(ToxicityApiHandler.class, "URL", mockUrl);

        JSONObject result = ToxicityApiHandler.toxicityQuery(TEST_TEXT, TEST_API_KEY);

        assertNotNull(result);
        assertTrue(result.getBoolean("success"));
    }

    @Test
    void testToxicityQueryIOException() throws Exception {
        // Mocking connection failure
        URL mockUrl = mock(URL.class);
        HttpURLConnection mockConnection = mock(HttpURLConnection.class);
        when(mockUrl.openConnection()).thenReturn(mockConnection);
        when(mockConnection.getOutputStream()).thenReturn(mock(OutputStream.class));
        when(mockConnection.getInputStream()).thenThrow(new IOException("Connection error"));

        // Setting up mock behavior
        ReflectionTestUtils.setField(ToxicityApiHandler.class, "API_URL_TOXICITY", "http://mockapi.com");
        ReflectionTestUtils.setField(ToxicityApiHandler.class, "URL", mockUrl);

        JSONObject result = ToxicityApiHandler.toxicityQuery(TEST_TEXT, TEST_API_KEY);

        assertNull(result);
    }

    @Test
    void testToxicityQueryInvalidJSONResponse() throws Exception {
        // Mocking a malformed JSON response
        String mockResponse = "INVALID_JSON";
        URL mockUrl = mock(URL.class);
        HttpURLConnection mockConnection = mock(HttpURLConnection.class);
        when(mockUrl.openConnection()).thenReturn(mockConnection);
        when(mockConnection.getOutputStream()).thenReturn(mock(OutputStream.class));
        when(mockConnection.getInputStream()).thenReturn(new ByteArrayInputStream(mockResponse.getBytes()));

        // Setting up mock behavior
        ReflectionTestUtils.setField(ToxicityApiHandler.class, "API_URL_TOXICITY", "http://mockapi.com");
        ReflectionTestUtils.setField(ToxicityApiHandler.class, "URL", mockUrl);

        JSONObject result = ToxicityApiHandler.toxicityQuery(TEST_TEXT, TEST_API_KEY);

        assertNull(result);
    }
}