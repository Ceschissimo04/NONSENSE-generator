package com.test.elementiIngegneria.controller;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.io.FileReader;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

class ApiKeyLoaderTest {

    @Test
    void testGetApiKey_ValidFile_ReturnsApiKey() throws IOException {
        // Arrange
        String mockFilePath = "mockFilePath.json";
        String expectedApiKey = "testApiKey";
        JsonObject mockJson = new JsonObject();
        mockJson.addProperty("apiKey", expectedApiKey);

        FileReader mockFileReader = mock(FileReader.class);

        try (MockedStatic<JsonParser> mockedParser = Mockito.mockStatic(JsonParser.class)) {
            mockedParser.when(() -> JsonParser.parseReader(mockFileReader)).thenReturn(mockJson);

            // Act
            String actualApiKey = ApiKeyLoader.getApiKey(mockFilePath);

            // Assert
            assertEquals(expectedApiKey, actualApiKey);
        }
    }

    @Test
    void testGetApiKey_FileNotFound_ThrowsIOException() {
        // Arrange
        String invalidFilePath = "nonexistentFile.json";

        // Act & Assert
        assertThrows(IOException.class, () -> ApiKeyLoader.getApiKey(invalidFilePath));
    }

    @Test
    void testGetApiKey_InvalidJsonStructure_ThrowsException() throws IOException {
        // Arrange
        String mockFilePath = "mockFilePath.json";
        JsonObject invalidJson = new JsonObject(); // Missing "apiKey" field

        FileReader mockFileReader = mock(FileReader.class);

        try (MockedStatic<JsonParser> mockedParser = Mockito.mockStatic(JsonParser.class)) {
            mockedParser.when(() -> JsonParser.parseReader(mockFileReader)).thenReturn(invalidJson);

            // Act & Assert
            assertThrows(NullPointerException.class, () -> ApiKeyLoader.getApiKey(mockFilePath));
        }
    }
}