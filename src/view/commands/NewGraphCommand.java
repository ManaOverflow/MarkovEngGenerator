package view.commands;

import database.GraphData;
import model.graph.MarkovGraph;
import view.UserInterface;

import java.util.List;

public class NewGraphCommand extends Command{
    private static final String COMMAND_NAME = "NewGraph";
    /**
     * Constructs a new command with a given command name and the user interface to act upon.
     *
     * @param userInterface the user interface to act upon.
     */
    public NewGraphCommand(UserInterface userInterface) {
        super(COMMAND_NAME, userInterface);
    }

    @Override
    public void execute(String[] arguments, GraphData database) throws InvalidArgumentException {
        if (arguments.length != 3) throw new InvalidArgumentException();
        List<String> lines = userInterface.readPath(arguments[0]);
        int context;
        try {
            context = Integer.parseInt(arguments[2]);
        } catch (NumberFormatException e) {
            throw new InvalidArgumentException("Invalid context value.");
        }
        if (context<1 || context > 5) throw new InvalidArgumentException("Invalid context value.");
        database.addGraph(MarkovGraph.getMarkovGraph(lines, arguments[1], context));
    }

    @Override
    public void printCommandGuide() {
        userInterface.print("The New Graph Command can be used to create a new graph for the database."
                + System.lineSeparator() + "NewGraph [txt file path] [name]");
    }
}
