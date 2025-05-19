package com.test.elementiIngegneria.model;

import java.util.ArrayList;

public class Node {
    private String name;
    private ArrayList<Node> children;

    public Node(String name) {
        this.name = name;
        this.children = new ArrayList<>();
    }

    public void addChildren(Node childrenNode) {
        children.add(childrenNode);
    }

    public String getName() {
        return name;
    }

    public ArrayList<Node> getChildren() {
        return children;
    }
    
}
