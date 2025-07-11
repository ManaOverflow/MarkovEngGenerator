package model.graph;

import model.graph.nodetypes.NodeType;
import util.graphgenerator.Collector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * This class implements a markov graph and its methods.
 * @author ManaOverflow
 */
public class MarkovGraph {
    private final List<List<Edge>> markovGraph;
    private final List<Node> startingPoints;
    private final List<Node> nodes;
    private final Map<String, Node> nameToNode;
    private final String name;
    private final int context;
    protected MarkovGraph(ArrayList<List<Edge>> markovGraph, List<Node> startingPoints, List<Node> nodes,
                          Map<String, Node> nameToNode, String name, int context) {
        this.markovGraph = markovGraph;
        this.startingPoints = startingPoints;
        this.nodes = nodes;
        this.nameToNode = nameToNode;
        this.name = name;
        this.context = context;
    }

    public static MarkovGraph getMarkovGraph (List<String> lines, String name, int context) {
        Collector collector = new Collector(lines, name, context);
        return collector.getGraph();
    }

    public static MarkovGraph createGraph(Map<String, Node> toNode, Map<String, Map<String, Integer>> connections,
                                          String name, int context) {
        int size = toNode.size();
        ArrayList<List<Edge>> graph = new ArrayList<>(size);
        List<Edge> edges;
        List<Node> startingNodes = new ArrayList<>(size);
        List<Node> nodes = new ArrayList<>();
        Map<String, Node> nameToNode = new HashMap<>();
        for (int i = 0; i < size; i++) {
            graph.add(new ArrayList<>());
            nodes.add(null);
        }
        for (Node node : toNode.values()) {
            nameToNode.put(node.getText(), node);
            Map<String, Integer> connected = connections.get(node.getText());
            edges = new ArrayList<>();
            if (connected != null){
                double sum = getSum(connected);
                for (String connectedNode : connected.keySet()) {
                    edges.add(new Edge(connected.get(connectedNode) / sum, toNode.get(connectedNode)));
                }
            }
            graph.set(node.getId(), edges);
            if (node.getNodeType() == NodeType.SENTENCE_START) startingNodes.add(node);
            nodes.set(node.getId(), node);
        }

        return new MarkovGraph(graph, startingNodes, nodes, nameToNode, name, context);
    }




    private static double getSum(Map<String, Integer> connected) {
        double sum = 0;
        for (Integer i : connected.values()) sum += i;
        return sum;
    }

    public String getName() {
        return name;
    }

    private int checkStart(String start) {
        if (start == null) return -1;
        for (Node node : startingPoints) {
            if (node.getText().equals(start)) return node.getId();
        }
        return -1;
    }

    private int getRandomStartNode() {
        Random random = new Random();
        int number = random.nextInt(0, startingPoints.size());
        return startingPoints.get(number).getId();
    }

    public int getStartIndex(String start) {
        int index = checkStart(start);
        return index == -1 ? getRandomStartNode() : index;
    }

    public String getString(int current) {
        return nodes.get(current).getText();
    }

    public int getMatching(int current, double probability) {
        List<Edge> edges = markovGraph.get(current);
        double sum = 0;
        for (Edge edge : edges) {
            sum += edge.getProbability();
            if(sum >= probability) return edge.getNode().getId();
        }
        return getRandomStartNode();
    }

    public int getContext() {
        return context;
    }
}
