package com.test.elementiIngegneria.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class HistoryHandler {
    private static final String HISTORY_FILE = "src/main/resources/static/files/generated.txt";

    public static void updateHistory(List<String> sentences) throws IOException {
        Files.write(Path.of(HISTORY_FILE), sentences, java.nio.file.StandardOpenOption.CREATE, java.nio.file.StandardOpenOption.APPEND);
    }

    public static List<String> readHistory() throws IOException {
        return Files.readAllLines(Path.of(HISTORY_FILE));
    }
}
