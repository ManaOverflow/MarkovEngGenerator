package view.commands;

import database.GraphData;
import view.UserInterface;

public abstract class Command {
    protected static final String WRONG_NUMBER_OF_ARGUMENTS_MESSAGE = "Error, incorrect number of command arguments.";
    protected static final String NO_DATABASE_ERROR_MESSAGE = "Error, there is no database to access.";
    protected final UserInterface userInterface;
    private final String commandName;

    /**
     * Constructs a new command with a given command name and the user interface to act upon.
     *
     * @param commandName   the command name.
     * @param userInterface the user interface to act upon.
     */
    protected Command(String commandName, UserInterface userInterface) {
        this.commandName = commandName;
        this.userInterface = userInterface;
    }

    /**
     * Returns the command name as a string.
     *
     * @return the command name.
     */
    public String getCommandName() {
        return commandName;
    }

    /**
     * Executes the command with the specified parameters in arguments and the given database to use if needed.
     *
     * @param arguments the specified command arguments for specific details.
     * @param database  the database to act upon if needed.
     * @throws InvalidArgumentException when the command arguments are invalid or the command cannot be executed.
     */
    public abstract void execute(String[] arguments, GraphData database) throws InvalidArgumentException;

    /**
     * Prints a string to explain the command and how to use it.
     */
    public abstract void printCommandGuide();
}

