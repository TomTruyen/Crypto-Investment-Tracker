package be.tomtruyen.cryptotracker.util.exception;

public class InvalidTokenException extends RuntimeException {
    private String path;

    public InvalidTokenException(String message, String path) {
        super(message);

        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
