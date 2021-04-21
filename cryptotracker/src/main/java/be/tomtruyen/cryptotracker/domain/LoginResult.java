package be.tomtruyen.cryptotracker.domain;

import org.springframework.http.HttpStatus;

public enum LoginResult {
    SUCCESS(true, "", HttpStatus.OK),
    ERR_MISSING_PARAMETERS(false, "Missing parameters.", HttpStatus.BAD_REQUEST),
    ERR_NOT_FOUND(false, "Invalid email/password combination.", HttpStatus.NOT_FOUND),
    ERR_NOT_VERIFIED(false, "Email not verified.", HttpStatus.BAD_REQUEST),
    ERR_UNKNOWN(false, "Something went wrong. Please try again.", HttpStatus.INTERNAL_SERVER_ERROR);

    private final boolean success;
    private final String message;
    private final HttpStatus status;

    LoginResult(boolean success, String message, HttpStatus status) {
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
