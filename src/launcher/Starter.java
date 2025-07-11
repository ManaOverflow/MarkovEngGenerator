package launcher;

import view.UserInterface;

public final class Starter {
    private Starter() {
        // No use
    }
    public static void main(String[] args) {
        UserInterface userInterface = new UserInterface(System.in, System.out);
        userInterface.run();
    }
}
