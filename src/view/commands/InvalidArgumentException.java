package view.commands;

/**
 * Signals that parsing/retrieving an argument failed, or that the specifications were invalid.
 *
 * @author Programmieren-Team
 * @author uttpa
 */
public class InvalidArgumentException extends Exception {
    private static final String MESSAGE = "Error, invalid command.";

    /**
     * Constructs a new exception with the specified detail message.
     * @param message the detail message.
     *                The detail message is saved for later retrieval by the {@link Throwable#getMessage()} method.
     */
    public InvalidArgumentException(String message) {
        super(message);
    }

    /**
     * Constructs a new exception with the standard error message.
     */
    public InvalidArgumentException() {
        this(MESSAGE);
    }
}
