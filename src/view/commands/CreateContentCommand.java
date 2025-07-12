package view.commands;

import graphdata.GraphData;
import util.generator.WordGenerator;
import model.graph.MarkovGraph;
import view.UserInterface;

/**
 * This class implements the create content command, usable to generate a text
 * using the specified graph and with a specified length.
 * @author ManaOverflow
 */
public class CreateContentCommand extends Command {
    private static final String COMMAND_NAME = "CC";
    private static final String COMMAND_GUIDE_TEXT = "Create Content Command: Creates text." + System.lineSeparator()
            + "CC [Graph name] [Text length] [*optional* Start word]" + System.lineSeparator()
            + "The start word needs to start a sentence in the text used to generate the graph.";
    private static final String ERROR_GRAPH_DOES_NOT_EXIST = "Error, graph does not exist.";
    private static final String TOO_MANY_WORDS_ERROR_MESSAGE = "Input size is too high, capped at 100,000,000.";

    /**
     * Constructs a new command with a given command name, and the user interface to act upon.
     *
     * @param userInterface the user interface to act upon.
     */
    public CreateContentCommand(UserInterface userInterface) {
        super(COMMAND_NAME, userInterface);
    }

    @Override
    public void execute(String[] arguments, GraphData database) throws InvalidArgumentException {
        if (arguments.length == 2 || arguments.length == 3) {
            MarkovGraph markovGraph = database.getGraph(arguments[0]);
            if (markovGraph == null) throw new InvalidArgumentException(ERROR_GRAPH_DOES_NOT_EXIST);
            int length;
            try {
                length = Integer.parseInt(arguments[1]);
            } catch (NumberFormatException e) {
                throw new InvalidArgumentException();
            }
            if (length > 100000000) {
                userInterface.print(TOO_MANY_WORDS_ERROR_MESSAGE);
                length = 100000000;
            }
            WordGenerator wordGenerator =
                    new WordGenerator(markovGraph, length, arguments.length == 2 ? null : arguments[2]);
            String output = wordGenerator.generate();
            userInterface.print(output);
        } else throw new InvalidArgumentException(WRONG_NUMBER_OF_ARGUMENTS_MESSAGE);
    }

    @Override
    public void printCommandGuide() {
        userInterface.print(COMMAND_GUIDE_TEXT);
    }
}
