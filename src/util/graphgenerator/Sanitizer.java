package util.graphgenerator;

public final class Sanitizer {
    private static final String REGEX = "\"|,|[(]|[)]|-|'";
    private Sanitizer() {
        // No use
    }
    public static String sanitize(String line) {
        return line.replaceAll(REGEX, " ");
    }
}
