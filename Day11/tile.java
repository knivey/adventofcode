
enum tile {
    FLOOR,
    FILLED,
    EMPTY,
    UNKNOWN;

    public static tile fromString(String y) {
        return switch (y) {
            case "L" -> EMPTY;
            case "." -> FLOOR;
            case "#" -> FILLED;
            default -> UNKNOWN;
        };
    }

    @Override
    public String toString() {
        return switch (this) {
            case EMPTY -> "L";
            case FLOOR -> ".";
            case FILLED -> "#";
            default -> "?";
        };
    }
}