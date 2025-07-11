package model.graph.nodetypes;

import model.graph.Node;

public class SentenceEndNode extends Node {
    public SentenceEndNode(String text, int id) {
        super(text, id, NodeType.SENTENCE_END);
    }
}
