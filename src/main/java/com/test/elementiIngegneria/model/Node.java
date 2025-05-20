package com.test.elementiIngegneria.model;

import java.util.ArrayList;

public class Node {
    private String name;
    private ArrayList<Node> children;

    public Node() {
        this.name = "";
        this.children = new ArrayList<>();
    }

    public Node(String name) {
        this.name = name;
        this.children = new ArrayList<>();
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Node> getChildren() {
        return children;
    }

    public void addChild(Node child) {
        this.children.add(child);
    }
}
