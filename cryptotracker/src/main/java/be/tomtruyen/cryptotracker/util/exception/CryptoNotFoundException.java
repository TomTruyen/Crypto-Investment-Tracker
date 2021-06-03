package be.tomtruyen.cryptotracker.util.exception;

public class CryptoNotFoundException extends RuntimeException {
    private final String path;

    public CryptoNotFoundException(String message, String path) {
        super(message);

        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
