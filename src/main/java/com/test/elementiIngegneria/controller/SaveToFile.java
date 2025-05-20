package com.test.elementiIngegneria.controller;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class SaveToFile{

    public static void SavetoFile(ArrayList<String> generated, String sentence, String template, String tense, String fileName) {
        try {
            FileWriter writer = new FileWriter(fileName);
            writer.write("Sentence: " + sentence + "\n");
            writer.write("Template: " + template + "\n");
            writer.write("Tense: " + tense + "\n\n");
            writer.write("Generated Sentences\n");
            for (String string : generated) {
                writer.write(string + "\n");
            }
            writer.write("---------------------------------------------------------------------------\n");
            writer.close();

        } catch (IOException e) {
            System.out.println("An error occurred while saving the file.");
            e.printStackTrace();
        }
    }
}