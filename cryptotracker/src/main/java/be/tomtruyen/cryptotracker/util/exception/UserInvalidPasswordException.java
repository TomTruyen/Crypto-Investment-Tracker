package be.tomtruyen.cryptotracker.util.exception;

public class UserInvalidPasswordException extends RuntimeException{
    private final String path;

    public UserInvalidPasswordException(String message, String path) {
        super(message);
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
