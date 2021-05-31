package be.tomtruyen.cryptotracker.util.exception;

public class UserNotVerifiedException extends RuntimeException{
    private String path;

    public UserNotVerifiedException(String message, String path) {
        super(message);
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
