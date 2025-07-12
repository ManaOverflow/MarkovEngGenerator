package view;

import graphdata.GraphData;
import view.commands.Command;
import view.commands.CreateContentCommand;
import view.commands.HelpCommand;
import view.commands.InvalidArgumentException;
import view.commands.NewGraphCommand;
import view.commands.QuitCommand;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * <p> The class initiates and handles an interaction with the user. It is responsible for delegating the input to the
 * corresponding command implementations.
 * The command and its arguments are expected to be separated by {@value #COMMAND_SEPARATOR}.</p>
 *
 * <p> An interaction is started by calling {@link #handleInput()}}.
 * It is possible to stop the current interaction prematurely
 * with {@link #quit()}}.</p>
 *
 * <p> As example, if the interaction should happen via the command line,
 * the standard input/output streams should be provided.</p>
 *
 * @author Programmieren-Team
 * @author uttpa
 */
public class UserInterface {
    private static final String COMMAND_SEPARATOR = " ";
    private final InputStream inputStream;
    private final PrintStream outputStream;
    private final Map<String, Command> commands = new HashMap<>();
    private final GraphData graphData = new GraphData();
    private boolean running;

    /**
     * Constructs a new user interface using the provided input source and output stream when interacting.
     * The provided input source is closed after the interaction is finished.
     * Also sets the commands and starts the user interaction.
     *
     * @param inputSource the input source used to retrieve the user input
     * @param defaultStream the stream used to print the default output
     */
    public UserInterface(InputStream inputSource, PrintStream defaultStream) {
        this.inputStream = inputSource;
        this.outputStream = defaultStream;
        initiateCommands();
    }
    private void initiateCommands() {
        addCommand(new NewGraphCommand(this));
        addCommand(new CreateContentCommand(this));
        addCommand(new HelpCommand(this));
        addCommand(new QuitCommand(this));
    }
    private void addCommand(Command command) {
        this.commands.put(command.getCommandName(), command);
    }


    /**
     * Starts the interaction with the user. This method will block while interacting.
     * The interaction will continue as long as the provided source has more lines to read,
     * or until it is stopped. The provided input source is closed after the interaction is finished.
     *
     * @see Scanner#hasNextLine()
     * @see #UserInterface(InputStream, PrintStream)
     */
    public void handleInput() {
        running = true;
        printHelpData();
        try (Scanner scanner = new Scanner(inputStream)) {
            while (running && scanner.hasNext()) {
                handleCommand(scanner.nextLine().split(COMMAND_SEPARATOR, -1));
            }
        }
    }

    private void handleCommand(String[] split) {
        String command = split[0];
        String[] arguments = Arrays.copyOfRange(split, 1, split.length);
        if (!commands.containsKey(command)) {
            return;
        }
        try {
            commands.get(command).execute(arguments, graphData);
        } catch (InvalidArgumentException invalidArgumentException) {
            print(invalidArgumentException.getMessage());
        }
    }

    /**
     * Reads the file found through the provided path and returns the lines of the file as a list.
     * @param path the provided file path as string.
     * @return the read file as a list of strings.
     * @throws InvalidFileException when the file cannot be read or found.
     */
    public List<String> readPath(String path) throws InvalidFileException {
        try {
            return Files.readAllLines(Paths.get(path));
        } catch (IOException ioException) {
            throw new InvalidFileException();
        }
    }

    public void print(String output) {
        if (running && !output.isBlank()) {
            System.out.println(output);
        }
    }

    /**
     * Stops this instance from reading further input from the source.
     */
    public void quit() {
        running = false;
    }

    public void printHelpData() {
        outputStream.print(System.lineSeparator());
        for (Command command : commands.values()) {
            command.printCommandGuide();
            outputStream.print(System.lineSeparator());
        }
    }
}
