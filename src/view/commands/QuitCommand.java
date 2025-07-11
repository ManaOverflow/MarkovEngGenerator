package view.commands;

import database.GraphData;
import view.UserInterface;

/**
 * Implements the quit command which terminates the provided user interface.
 * @author uttpa
 */
public class QuitCommand extends Command {
    private static final String COMMAND_NAME = "quit";
    private static final int EXPECTED_ARGUMENTS_LENGTH = 0;

    /**
     * Constructs a new quit command with the command name and the provided user interface.
     * @param userInterface the user interface to act upon.
     */
    public QuitCommand(UserInterface userInterface) {
        super(COMMAND_NAME, userInterface);
    }

    @Override
    public void execute(String[] arguments, GraphData database) throws InvalidArgumentException {
        if (arguments.length != EXPECTED_ARGUMENTS_LENGTH) {
            throw new InvalidArgumentException(WRONG_NUMBER_OF_ARGUMENTS_MESSAGE);
        }
        userInterface.quit();
    }

    @Override
    public void printCommandGuide() {
        userInterface.print("Type [quit] to end the program.");
    }
}
