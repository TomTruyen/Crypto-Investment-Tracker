package be.tomtruyen.cryptotracker.util.exception;

public class UserAlreadyVerifiedException extends RuntimeException {
    private final String path;

    public UserAlreadyVerifiedException(String message, String path) {
        super(message);
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
