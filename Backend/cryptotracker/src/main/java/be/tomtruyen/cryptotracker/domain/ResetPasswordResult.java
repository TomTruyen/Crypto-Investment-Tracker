package be.tomtruyen.cryptotracker.domain;

import org.springframework.http.HttpStatus;

public enum ResetPasswordResult {
    SUCCESS(true, "If an account exists with this email, then you will receive an email soon.", HttpStatus.OK),
    SUCCESS_CONFIRM(true, "Password has been changed. You can now log in.", HttpStatus.OK),
    ERR_UNKNOWN(false, "Something went wrong. Please try again.", HttpStatus.INTERNAL_SERVER_ERROR),
    ERR_NOT_FOUND(false, "Email not found.", HttpStatus.NOT_FOUND),
    ERR_INVALID_PASSWORD(false, "Password is too weak.", HttpStatus.BAD_REQUEST),
    ERR_MISSING_PARAMETERS(false, "Missing parameters.", HttpStatus.BAD_REQUEST);

    private final boolean success;
    private final String message;
    private final HttpStatus status;

    ResetPasswordResult(boolean success, String message, HttpStatus status) {
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
