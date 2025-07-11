package model.graph;

import model.graph.nodetypes.NodeType;

public abstract class Node {
    private final String text;
    private final int id;
    private final NodeType nodeType;

    public Node(String text, int id, NodeType nodeType) {
        this.text = text;
        this.id = id;
        this.nodeType = nodeType;
    }

    public String getText() {
        return text;
    }

    public int getId() {
        return id;
    }

    public NodeType getNodeType() {
        return nodeType;
    }
}
