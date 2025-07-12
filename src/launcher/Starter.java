package launcher;

import view.UserInterface;

/**
 * This class starts the application.
 * @author ManaOverflow
 */
public final class Starter {
    private Starter() {
        // No use
    }

    /**
     * This method starts the program.
     * @param args  the command line arguments.
     */
    public static void main(String[] args) {
        if (args.length != 0) {
            System.out.print("Error, invalid command line arguments.");
        } else {
            UserInterface userInterface = new UserInterface(System.in, System.out);
            userInterface.handleInput();
        }
    }
}
