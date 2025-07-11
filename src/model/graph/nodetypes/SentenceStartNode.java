package model.graph.nodetypes;

import model.graph.Node;

public class SentenceStartNode extends Node {
    public SentenceStartNode(String text, int id) {
        super(text, id, NodeType.SENTENCE_START);
    }
}
