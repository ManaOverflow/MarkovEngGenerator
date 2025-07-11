package util.graphgenerator;

import model.graph.MarkovGraph;
import model.graph.Node;
import model.graph.nodetypes.SentenceContentNode;
import model.graph.nodetypes.SentenceEndNode;
import model.graph.nodetypes.SentenceStartNode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.StringTokenizer;

/**
 * The collector collects the nodes (strings) for the graph and creates connections between the nodes
 * based on the given context scale. Context meaning words per node.
 * @author ManaOverflow
 */
public class Collector {
    private static final String TOKENIZER_DELIMITER = " ";
    private final List<String> lines;
    private final String name;
    private final int context;
    private final Map<String, Map<String, Integer>> connections;
    private final Map<String, Node> toNode;

    private int id = 0;
    private boolean isSentenceStart = true;
    private StringTokenizer tokenizer;
    private String currentToken;

    /**
     * Constructs a new Collector with the lines for the graph, the name and the context specified by the user.
     * @param lines     the lines to use for the graph.
     * @param name      the name for the graph.
     * @param context   the context value for the graph.
     */
    public Collector(List<String> lines, String name, int context) {
        this.lines = lines;
        this.name = name;
        this.context = context;
        this.connections = new HashMap<>();
        this.toNode = new HashMap<>();
        collectWords();
    }

    public MarkovGraph getGraph() {
        return MarkovGraph.createGraph(toNode, connections, name, context);
    }

    private void collectWords() {
        StringJoiner from = new StringJoiner(" "), to, start = null;
        for (String line : lines) {
            if (line.isBlank()) continue;
            String sanLine = Sanitizer.sanitize(line);
            tokenizer = new StringTokenizer(sanLine, TOKENIZER_DELIMITER, false);
            setNextToken();
            int counter = 0;
            if (start == null) {
                start = new StringJoiner(" ");
                while (counter < context && currentToken!=null) {
                    start.add(currentToken);
                    setNextToken();
                    counter++;
                }
                createNode(start.toString());
                from = start;
            }

            while (currentToken!=null) {
                to = new StringJoiner(" ");
                counter = 0;
                while (currentToken!= null && counter<context) {
                    to.add(currentToken);
                    setNextToken();
                    counter++;
                }
                createNode(to.toString());
                connect(from.toString(), to.toString());
                from = to;
            }
        }
    }



    private void createNode(String word) {
        if (toNode.containsKey(word)) return;
        char end = word.charAt(word.length() - 1);
        if (end == '.' || end == '?' || end == '!') {
            toNode.put(word, new SentenceEndNode(word, id));
            isSentenceStart = true;
        } else {
            toNode.put(word, isSentenceStart ?
                    new SentenceStartNode(word, id) : new SentenceContentNode(word, id));
            if (isSentenceStart) isSentenceStart = false;
        }
        id++;
    }

    private void connect(String from, String to) {
        if (!connections.containsKey(from)) connections.put(from, new HashMap<>());

        Map<String, Integer> connected = connections.get(from);
        if (!connected.containsKey(to)) {
            connected.put(to, 1);
        } else {
            int occurrences = connected.get(to);
            connected.put(to, ++occurrences);
        }
    }

    private void setNextToken() {
        if (tokenizer.hasMoreTokens()) {
            currentToken = tokenizer.nextToken().trim();
        } else {
            currentToken = null;
        }
    }

}
