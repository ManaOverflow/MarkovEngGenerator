package database;

import model.graph.MarkovGraph;

import java.util.HashMap;
import java.util.Map;

/**
 * Not actually a database but its enough for now.
 * This class implements the methods to save and access created graphs.
 * @author ManaOverflow
 */
public class GraphData {
    private final Map<String, MarkovGraph> savedGraphs = new HashMap<>();

    /**
     * Constructor of the class GraphData. Literally does nothing.
     */
    public GraphData() {
        // Not used.
    }

    /**
     * Returns the graph associated to the given name.
     * @param name  the name of the requested graph.
     * @return      the fitting graph.
     */
    public MarkovGraph getGraph(String name) {
        return savedGraphs.get(name);
    }

    /**
     * Adds a graph to the map.
     * @param markovGraph   the graph to add.
     */
    public void addGraph (MarkovGraph markovGraph) {
        savedGraphs.put(markovGraph.getName(), markovGraph);
    }
}
