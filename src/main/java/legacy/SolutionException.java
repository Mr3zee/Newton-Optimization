package legacy;

public class SolutionException extends RuntimeException {
    private final SolutionType type;

    public SolutionException(final SolutionType type) {
        this.type = type;
    }

    public SolutionType getType() {
        return type;
    }

    @Override
    public String getMessage() {
        return String.format("Wrong solution type: %s", type);
    }
}
