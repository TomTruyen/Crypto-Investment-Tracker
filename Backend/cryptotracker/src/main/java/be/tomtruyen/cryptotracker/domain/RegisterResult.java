package be.tomtruyen.cryptotracker.domain;

import org.springframework.http.HttpStatus;

public enum RegisterResult {
    SUCCESS(true, "Verification email sent.", HttpStatus.OK),
    ERR_MISSING_PARAMETERS(false, "Missing parameters.", HttpStatus.BAD_REQUEST),
    ERR_INVALID_EMAIL(false, "Invalid email.", HttpStatus.BAD_REQUEST),
    ERR_INVALID_PASSWORD(false, "Password is too weak.", HttpStatus.BAD_REQUEST),
    ERR_ALREADY_EXISTS(false, "Email is already being used.", HttpStatus.CONFLICT),
    ERR_UNKNOWN(false, "Something went wrong. Please try again.", HttpStatus.INTERNAL_SERVER_ERROR);

    private final boolean success;
    private final String message;
    private final HttpStatus status;

    RegisterResult(boolean success, String message, HttpStatus status) {
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
