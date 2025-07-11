package model.graph.nodetypes;

import model.graph.Node;

public class SentenceContentNode extends Node {
    public SentenceContentNode(String text, int id) {
        super(text, id, NodeType.SENTENCE_CONTENT);
    }
}
