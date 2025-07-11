package model.graph;

import model.graph.nodetypes.NodeType;

/**
 * This class implements a node in the graph.
 * @author ManaOverflow
 */
public abstract class Node {
    private final String text;
    private final int id;
    private final NodeType nodeType;

    /**
     * Constructs a new Node with given text, ID and node type.
     * @param text      the content of the node
     * @param id        the ID of the node
     * @param nodeType  the node type
     */
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
