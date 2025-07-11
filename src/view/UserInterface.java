package view;

import database.GraphData;
import view.commands.*;

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

public class UserInterface {
    private final InputStream inputStream;
    private final PrintStream outputStream;
    private final Map<String, Command> commands = new HashMap<>();
    private final GraphData graphData = new GraphData();
    private boolean running;

    public UserInterface(InputStream inputStream, PrintStream outputStream) {
        this.inputStream = inputStream;
        this.outputStream = outputStream;
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

    public void run() {
        running = true;
        handleInput();
    }

    private void handleInput() {
        printHelpData();
        try (Scanner scanner = new Scanner(inputStream)) {
            while (running && scanner.hasNext()) {
                handleCommand(scanner.nextLine().split(" ", -1));
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
