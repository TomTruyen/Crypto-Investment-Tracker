package be.tomtruyen.cryptotracker.util.exception;

public class UserAlreadyExistsException extends RuntimeException {
    private final String path;

    public UserAlreadyExistsException(String message, String path) {
        super(message);
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
