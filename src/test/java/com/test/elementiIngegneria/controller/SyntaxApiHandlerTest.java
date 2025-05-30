package com.test.elementiIngegneria.controller;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;

import com.test.elementiIngegneria.controller.*;

import java.io.ByteArrayInputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class SyntaxApiHandlerTest {

    /**
     * Tests the SyntaxQuery method in the SyntaxApiHandler class which sends
     * a POST request to the Google Natural Language API to analyze text syntax.
     */
    @Test
    void testSyntaxQuerySuccessfulResponse() throws Exception {
        // Arrange
        String text = "Sample text for analysis";
        String apiKey = "API_KEY_PLACEHOLDER";

        String mockResponse = "{ \"mock\": \"response\" }";
        MockedConstruction<URL> mockedURL = mockConstruction(URL.class, (mockUrl, context) -> {
            HttpURLConnection mockConnection = mock(HttpURLConnection.class);
            when(mockUrl.openConnection()).thenReturn(mockConnection);

            when(mockConnection.getOutputStream()).thenReturn(mock(OutputStream.class));

            when(mockConnection.getInputStream()).thenReturn(new ByteArrayInputStream(mockResponse.getBytes()));

            when(mockConnection.getResponseCode()).thenReturn(HttpURLConnection.HTTP_OK);
        });

        try {
            // Act
            JSONObject result = SyntaxApiHandler.SyntaxQuery(text, apiKey);

            // Assert
            assertNotNull(result);
            assertEquals("response", result.getString("mock"));
        } finally {
            mockedURL.close();
        }
    }


}