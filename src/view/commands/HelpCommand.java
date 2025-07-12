package view.commands;

import database.GraphData;
import view.UserInterface;

/**
 * This command implements a command to help the user by printing
 * short guides for each available command.
 * @author ManaOverflow
 */
public class HelpCommand extends Command {
    private static final String COMMAND_NAME = "help";
    /**
     * Constructs a new command with a given command name and the user interface to act upon.
     *
     * @param userInterface the user interface to act upon.
     */
    public HelpCommand(UserInterface userInterface) {
        super(COMMAND_NAME, userInterface);
    }

    @Override
    public void execute(String[] arguments, GraphData database) throws InvalidArgumentException {
        if (arguments.length != 0) {
            throw new InvalidArgumentException(WRONG_NUMBER_OF_ARGUMENTS_MESSAGE);
        }
        userInterface.printHelpData();
    }

    @Override
    public void printCommandGuide() {
        userInterface.print("Enter [help] for the command guides.");
    }
}
