package be.tomtruyen.cryptotracker.domain;

import org.springframework.http.HttpStatus;

public enum CryptoResult {
    SUCCESS(true, "", HttpStatus.OK),
    ERR_MISSING_PARAMETERS(false, "Missing authorization.", HttpStatus.BAD_REQUEST),
    ERR_INVALID_AUTH(false, "Invalid authorization.", HttpStatus.BAD_REQUEST),
    ERR_EXPIRED_TOKEN(false, "Token expired.", HttpStatus.BAD_REQUEST),
    ERR_UNKNOWN(false, "Something went wrong. Please try again.", HttpStatus.INTERNAL_SERVER_ERROR);

    private final boolean success;
    private final String message;
    private final HttpStatus status;

    CryptoResult(boolean success, String message, HttpStatus status) {
        this.success = success;
        this.message = message;
        this.status = status;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
