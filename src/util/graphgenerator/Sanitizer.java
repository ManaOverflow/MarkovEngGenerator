package util.graphgenerator;

public final class Sanitizer {
    private static final String SPLITTERS = "\"|,|[(]|[)]|-";
    private static final String UPPER_THINGY = "'";
    private Sanitizer() {
        // No use
    }
    public static String sanitize(String line) {
        return line.replace(". ", "ß")
                .replaceAll(SPLITTERS, " ").replaceAll("ß", ". ")
                .replaceAll(UPPER_THINGY, "");
    }
}
