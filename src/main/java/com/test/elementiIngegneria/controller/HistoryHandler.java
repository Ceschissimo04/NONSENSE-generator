package com.test.elementiIngegneria.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * Handler class for managing the history of generated sentences.
 * Provides functionality to read from and write to a history file.
 */
public class HistoryHandler {
    private static final String HISTORY_FILE = "src/main/resources/static/files/generated.txt";

    /**
     * Updates the history file by appending new sentences.
     *
     * @param sentences List of sentences to append to the history file
     * @throws IOException if there is an error writing to the file
     */
    public static void updateHistory(List<String> sentences) throws IOException {
        Files.write(Path.of(HISTORY_FILE), sentences, java.nio.file.StandardOpenOption.CREATE, java.nio.file.StandardOpenOption.APPEND);
    }

    /**
     * Reads all sentences from the history file.
     *
     * @return List of sentences from the history file
     * @throws IOException if there is an error reading from the file
     */
    public static List<String> readHistory() throws IOException {
        return Files.readAllLines(Path.of(HISTORY_FILE));
    }
}
