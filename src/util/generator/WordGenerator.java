package util.generator;

import model.graph.MarkovGraph;

import java.util.Random;

public class WordGenerator {
    private final int length;
    private final MarkovGraph markovGraph;
    private final String start;
    private final Random random = new Random();

    public WordGenerator(MarkovGraph markovGraph, int length, String start) {
        this.length = length;
        this.markovGraph = markovGraph;
        this.start = start;
    }

    public String generate() {
        StringBuilder stringBuilder = new StringBuilder();
        int current = markovGraph.getStartIndex(start);
        stringBuilder.append(markovGraph.getString(current)).append(" ");
        int context = markovGraph.getContext();
        for (int i = 1; i < length/context; i++) {
            current = markovGraph.getMatching(current, random.nextDouble());
            stringBuilder.append(markovGraph.getString(current));
            if (i % (10-context) == 0) stringBuilder.append(System.lineSeparator());
            else stringBuilder.append(" ");
        }
        return stringBuilder.toString();
    }

}
