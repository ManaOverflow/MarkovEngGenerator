package model.graph;

/**
 * This class implements the edge between strings/nodes in the graph
 * with a given probability, and the node it goes to.
 * @author ManaOverflow
 */
public class Edge {
    private final double probability;
    private final Node node;

    /**
     * Constructs a new Edge with a given probability, and the node it goes to.
     * @param probability   the probability of the edge.
     * @param node          the node the edge connects to.
     */
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
