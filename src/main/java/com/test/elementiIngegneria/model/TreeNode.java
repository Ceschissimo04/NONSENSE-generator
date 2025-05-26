package com.test.elementiIngegneria.model;

import java.util.ArrayList;

public class TreeNode {
    private String name;
    private ArrayList<TreeNode> children;

    public TreeNode() {
        this.name = "";
        this.children = new ArrayList<>();
    }

    public TreeNode(String name) {
        this.name = name;
        this.children = new ArrayList<>();
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public ArrayList<TreeNode> getChildren() {
        return children;
    }

    public void addChild(TreeNode child) {
        this.children.add(child);
    }
}
