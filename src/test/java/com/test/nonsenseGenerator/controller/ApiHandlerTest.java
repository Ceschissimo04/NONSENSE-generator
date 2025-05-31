package com.test.nonsenseGenerator.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mockStatic;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import com.test.nonsenseGenerator.utility.Pair;

class ApiHandlerTest {

    @Test
    void testGetToxicityScore_ValidText() throws IOException, JSONException {
        String inputSentence = "This is a simple test";
        JSONObject mockResponse = new JSONObject();
        JSONArray moderationCategories = new JSONArray();
        mockResponse.put("moderationCategories", moderationCategories);

        try (MockedStatic<ToxicityApiHandler> mockedApiHandler = mockStatic(ToxicityApiHandler.class);
             MockedStatic<ApiKeyLoader> mockedApiKeyLoader = mockStatic(ApiKeyLoader.class)) {

            mockedApiKeyLoader.when(() -> ApiKeyLoader.getApiKey("config.json")).thenReturn("mock-api-key");
            mockedApiHandler.when(() -> ToxicityApiHandler.toxicityQuery(eq(inputSentence), anyString())).thenReturn(mockResponse);

            ApiHandler apiHandler = ApiHandler.getInstance();
            Pair<String, Integer> result = apiHandler.getToxicityScore(inputSentence);

            assertEquals("Valid", result.getFirst());
            assertEquals(100, result.getSecond());
        }
    }

    @Test
    void testGetToxicityScore_ToxicText() throws IOException, JSONException {
        String inputSentence = "This is a bad example";
        JSONObject mockResponse = new JSONObject();
        JSONArray moderationCategories = new JSONArray();
        JSONObject toxicCategory = new JSONObject();
        toxicCategory.put("name", "Hate Speech");
        toxicCategory.put("confidence", 0.8);
        moderationCategories.put(toxicCategory);
        mockResponse.put("moderationCategories", moderationCategories);

        try (MockedStatic<ToxicityApiHandler> mockedApiHandler = mockStatic(ToxicityApiHandler.class);
             MockedStatic<ApiKeyLoader> mockedApiKeyLoader = mockStatic(ApiKeyLoader.class)) {

            mockedApiKeyLoader.when(() -> ApiKeyLoader.getApiKey("config.json")).thenReturn("mock-api-key");
            mockedApiHandler.when(() -> ToxicityApiHandler.toxicityQuery(eq(inputSentence), anyString())).thenReturn(mockResponse);

            ApiHandler apiHandler = ApiHandler.getInstance();
            Pair<String, Integer> result = apiHandler.getToxicityScore(inputSentence);

            assertEquals("Hate Speech", result.getFirst());
            assertEquals(80, result.getSecond());
        }
    }

    @Test
    void testGetToxicityScore_NullResponse() throws IOException {
        String inputSentence = "Testing with no API response";

        try (MockedStatic<ToxicityApiHandler> mockedApiHandler = mockStatic(ToxicityApiHandler.class);
             MockedStatic<ApiKeyLoader> mockedApiKeyLoader = mockStatic(ApiKeyLoader.class)) {

            mockedApiKeyLoader.when(() -> ApiKeyLoader.getApiKey("config.json")).thenReturn("mock-api-key");
            mockedApiHandler.when(() -> ToxicityApiHandler.toxicityQuery(eq(inputSentence), anyString())).thenReturn(null);

            ApiHandler apiHandler = ApiHandler.getInstance();
            Pair<String, Integer> result = apiHandler.getToxicityScore(inputSentence);

            assertEquals("Error in API call", result.getFirst());
            assertEquals(-1, result.getSecond());
        }
    }

    @Test
    void testGetToxicityScore_LowConfidence() throws IOException, JSONException {
        String inputSentence = "This is borderline text";
        JSONObject mockResponse = new JSONObject();
        JSONArray moderationCategories = new JSONArray();
        JSONObject borderlineCategory = new JSONObject();
        borderlineCategory.put("name", "Borderline Content");
        borderlineCategory.put("confidence", 0.4); // Below threshold
        moderationCategories.put(borderlineCategory);
        mockResponse.put("moderationCategories", moderationCategories);

        try (MockedStatic<ToxicityApiHandler> mockedApiHandler = mockStatic(ToxicityApiHandler.class);
             MockedStatic<ApiKeyLoader> mockedApiKeyLoader = mockStatic(ApiKeyLoader.class)) {

            mockedApiKeyLoader.when(() -> ApiKeyLoader.getApiKey("config.json")).thenReturn("mock-api-key");
            mockedApiHandler.when(() -> ToxicityApiHandler.toxicityQuery(eq(inputSentence), anyString())).thenReturn(mockResponse);

            ApiHandler apiHandler = ApiHandler.getInstance();
            Pair<String, Integer> result = apiHandler.getToxicityScore(inputSentence);

            assertEquals("Valid", result.getFirst());
            assertEquals(60, result.getSecond());
        }
    }
}