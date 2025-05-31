package com.test.nonsenseGenerator.utility;

import java.util.ArrayList;

/**
 * Represents a node in a tree data structure.
 * Each node has a name and can have multiple children nodes.
 */
public class TreeNode {
    private String name;
    private ArrayList<TreeNode> children;

    /**
     * Constructs an empty TreeNode with no name.
     */
    public TreeNode() {
        this.name = "";
        this.children = new ArrayList<>();
    }

    /**
     * Constructs a TreeNode with the specified name.
     *
     * @param name The name of the node
     */
    public TreeNode(String name) {
        this.name = name;
        this.children = new ArrayList<>();
    }

    /**
     * Sets the name of this node.
     *
     * @param name The new name for the node
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the name of this node.
     *
     * @return The name of the node
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the list of child nodes.
     *
     * @return ArrayList containing all child nodes
     */
    public ArrayList<TreeNode> getChildren() {
        return children;
    }

    /**
     * Adds a child node to this node.
     *
     * @param child The TreeNode to add as a child
     */
    public void addChild(TreeNode child) {
        this.children.add(child);
    }
}
