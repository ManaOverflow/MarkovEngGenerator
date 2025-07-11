package database;

import model.graph.MarkovGraph;

import java.util.HashMap;
import java.util.Map;

public class GraphData {
    private final Map<String, MarkovGraph> savedGraphs = new HashMap<>();

    public GraphData() {
    }

    public MarkovGraph getGraph(String name) {
        return savedGraphs.get(name);
    }
    public void addGraph (MarkovGraph markovGraph) {
        savedGraphs.put(markovGraph.getName(), markovGraph);
    }
}
