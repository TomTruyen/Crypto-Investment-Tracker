package be.tomtruyen.cryptotracker.util.exception;

public class UserInvalidEmailException extends RuntimeException{
    private String path;

    public UserInvalidEmailException(String message, String path) {
        super(message);
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
