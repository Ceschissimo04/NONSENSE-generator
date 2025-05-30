package com.test.elementiIngegneria.controller;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.io.FileReader;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

class ApiKeyLoaderTest {
    /**
     * This class tests the getApiKey method from the ApiKeyLoader class.
     * The method reads an API key from a JSON file and returns it as a String.
     */
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