package be.tomtruyen.cryptotracker.util.exception;

public class CryptoUserNotFoundException extends RuntimeException {
    private String path;

    public CryptoUserNotFoundException(String message, String path) {
        super(message);

        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
