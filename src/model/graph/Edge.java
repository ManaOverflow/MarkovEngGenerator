package model.graph;

public class Edge {
    private final double probability;
    private final Node node;

    public Edge(double probability, Node node) {
        this.probability = probability;
        this.node = node;
    }

    public double getProbability() {
        return probability;
    }

    public Node getNode() {
        return node;
    }
}
