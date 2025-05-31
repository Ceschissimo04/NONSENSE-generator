package com.test.nonsenseGenerator.utility;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

class TreeNodeTest {

    /**
     * Test class for the TreeNode class, specifically testing the addChild method functionality.
     * The addChild method is expected to add a new child TreeNode to the TreeNode's children list.
     */

    @Test
    void testAddChildSuccessfullyAddsChildNode() {
        // Arrange
        TreeNode parent = new TreeNode("Parent");
        TreeNode child = new TreeNode("Child");

        // Act
        parent.addChild(child);

        // Assert
        ArrayList<TreeNode> children = parent.getChildren();
        assertNotNull(children);
        assertEquals(1, children.size());
        assertEquals("Child", children.get(0).getName());
    }

    @Test
    void testAddChildWithMultipleChildren() {
        // Arrange
        TreeNode parent = new TreeNode("Parent");
        TreeNode child1 = new TreeNode("Child1");
        TreeNode child2 = new TreeNode("Child2");

        // Act
        parent.addChild(child1);
        parent.addChild(child2);

        // Assert
        ArrayList<TreeNode> children = parent.getChildren();
        assertNotNull(children);
        assertEquals(2, children.size());
        assertEquals("Child1", children.get(0).getName());
        assertEquals("Child2", children.get(1).getName());
    }

    @Test
    void testAddChildAllowsDuplicateNodes() {
        // Arrange
        TreeNode parent = new TreeNode("Parent");
        TreeNode child = new TreeNode("Child");

        // Act
        parent.addChild(child);
        parent.addChild(child);

        // Assert
        ArrayList<TreeNode> children = parent.getChildren();
        assertNotNull(children);
        assertEquals(2, children.size());
        assertSame(child, children.get(0));
        assertSame(child, children.get(1));
    }

    @Test
    void testAddChildWithNoInitialChildren() {
        // Arrange
        TreeNode parent = new TreeNode("Parent");

        // Act
        parent.addChild(new TreeNode("Child"));

        // Assert
        ArrayList<TreeNode> children = parent.getChildren();
        assertNotNull(children);
        assertFalse(children.isEmpty());
    }
}