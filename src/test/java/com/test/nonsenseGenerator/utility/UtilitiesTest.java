package com.test.nonsenseGenerator.utility;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

public class UtilitiesTest {

    /**
     * Tests for the buildTreeFromJson method in Utilities class.
     * <p>
     * The buildTreeFromJson method constructs a tree structure (TreeNode) from a JSON object containing token data.
     * Each test focuses on a single use case to verify the method's correctness.
     */

    @Test
    public void testBuildTreeFromJson_SingleNode() {
        // Arrange
        String jsonString = """
                {
                    "tokens": [
                        {
                            "text": { "content": "root" },
                            "dependencyEdge": { "label": "ROOT", "headTokenIndex": 0 }
                        }
                    ]
                }
                """;
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(jsonString);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        // Act
        TreeNode result = Utilities.buildTreeFromJson(jsonObject);

        // Assert
        assertNotNull(result);
        assertEquals("ROOT: root", result.getName());
        assertEquals(0, result.getChildren().size());
    }

    @Test
    public void testBuildTreeFromJson_TreeWithTwoLevels() {
        // Arrange
        String jsonString = """
                {
                    "tokens": [
                        {
                            "text": { "content": "root" },
                            "dependencyEdge": { "label": "ROOT", "headTokenIndex": 0 }
                        },
                        {
                            "text": { "content": "child" },
                            "dependencyEdge": { "label": "DEPENDENT", "headTokenIndex": 0 }
                        }
                    ]
                }
                """;
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(jsonString);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        // Act
        TreeNode result = Utilities.buildTreeFromJson(jsonObject);

        // Assert
        assertNotNull(result);
        assertEquals("ROOT: root", result.getName());
        assertEquals(1, result.getChildren().size());
        assertEquals("DEPENDENT: child", result.getChildren().get(0).getName());
    }

    @Test
    public void testBuildTreeFromJson_TreeWithMultipleLevels() {
        // Arrange
        String jsonString = """
                {
                    "tokens": [
                        {
                            "text": { "content": "root" },
                            "dependencyEdge": { "label": "ROOT", "headTokenIndex": 0 }
                        },
                        {
                            "text": { "content": "child1" },
                            "dependencyEdge": { "label": "DEPENDENT", "headTokenIndex": 0 }
                        },
                        {
                            "text": { "content": "child2" },
                            "dependencyEdge": { "label": "DEPENDENT", "headTokenIndex": 0 }
                        },
                        {
                            "text": { "content": "grandchild" },
                            "dependencyEdge": { "label": "DEPENDENT", "headTokenIndex": 1 }
                        }
                    ]
                }
                """;
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(jsonString);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        // Act
        TreeNode result = Utilities.buildTreeFromJson(jsonObject);

        // Assert
        assertNotNull(result);
        assertEquals("ROOT: root", result.getName());
        assertEquals(2, result.getChildren().size());
        assertEquals("DEPENDENT: child1", result.getChildren().get(0).getName());
        assertEquals("DEPENDENT: child2", result.getChildren().get(1).getName());
        assertEquals(1, result.getChildren().get(0).getChildren().size());
        assertEquals("DEPENDENT: grandchild", result.getChildren().get(0).getChildren().get(0).getName());
    }

    @Test
    public void testBuildTreeFromJson_InvalidJson() {
        // Arrange
        String jsonString = """
                {
                    "tokens": []
                }
                """;
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(jsonString);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        final JSONObject finalJsonObject = jsonObject;
        // Assert
        assertThrows(IndexOutOfBoundsException.class, () -> Utilities.buildTreeFromJson(finalJsonObject));
    }

}