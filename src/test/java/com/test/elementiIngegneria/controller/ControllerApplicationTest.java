package com.test.elementiIngegneria.controller;

import com.test.elementiIngegneria.model.Dictionary;
import com.test.elementiIngegneria.model.Template;
import com.test.elementiIngegneria.utility.Pair;
import com.test.elementiIngegneria.utility.TreeNode;
import com.test.elementiIngegneria.utility.Utilities;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import javax.xml.transform.Templates;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ControllerApplication.class)
public class ControllerApplicationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testController_DefaultPageLoadsSuccessfully() throws Exception {
        ArrayList<String> templates = new ArrayList<>();
        when(Template.getAllTemplate()).thenReturn(templates);

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attribute("templateList", templates))
                .andExpect(model().attribute("syntaxTree", "The tree will appear here..."))
                .andExpect(model().attribute("nonsenseResult", "Your nonsense sentence will appear here ..."))
                .andExpect(model().attribute("extractedWords", "The template will appear here..."))
                .andExpect(model().attribute("outputTitle", "Generated Nonsense Sentence"));
    }

    @Test
    public void testGeneraNonsense_ErrorHandlingOnSentenceProcessingFailure() throws Exception {
        String sentence = "test sentence";
        String template = "test template";
        String tense = "past";

        when(Template.getAllTemplate()).thenReturn(new ArrayList<>());

        mockMvc.perform(post("/generate")
                        .param("sentence", sentence)
                        .param("template", template)
                        .param("tense", tense))
                .andExpect(status().isOk())
                .andExpect(view().name("error"));
    }

    @Test
    public void testHistory_HistoryLoadsSuccessfully() throws Exception {
        List<String> historyList = new ArrayList<>();
        when(HistoryHandler.readHistory()).thenReturn(historyList);

        mockMvc.perform(post("/history"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attribute("outputTitle", "History"))
                .andExpect(model().attribute("nonsenseResult", "History is empty"))
                .andExpect(model().attribute("syntaxTree", "The tree will appear here..."))
                .andExpect(model().attribute("extractedWords", "The template will appear here..."));
    }

    @Test
    public void testAnalyze_AnalyzesSentenceSuccessfully() throws Exception {
        String sentence = "test sentence";
        String template = "test template";
        String tense = "present";
        TreeNode mockTree = mock(TreeNode.class);
        String generatedTreeHTML = "<div>MockTree</div>";

        when(Utilities.generateTreeHTML(mockTree)).thenReturn(generatedTreeHTML);
        when(Template.getAllTemplate()).thenReturn(new ArrayList<>());

        mockMvc.perform(post("/analyze")
                        .param("sentence", sentence)
                        .param("template", template)
                        .param("tense", tense)
                        .param("showSyntaxTree", "true"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attribute("templateList", new ArrayList<>()))
                .andExpect(model().attribute("syntaxTree", generatedTreeHTML));
    }

    @Test
    public void testAddDictionary_AddsWordsToDictionarySuccessfully() throws Exception {
        String sentence = "word1, word2";
        List<Pair<String, String>> wordPairs = new ArrayList<>();
        when(ApiHandler.getInstance().getElementsOfTextLemma(sentence)).thenReturn(wordPairs);
        when(Template.getAllTemplate()).thenReturn(new ArrayList<>());

        mockMvc.perform(post("/add")
                        .param("sentence", sentence))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attribute("templateList", new ArrayList<>()));
        verify(Dictionary.getInstance(), times(1)).addWords(wordPairs);
    }
}