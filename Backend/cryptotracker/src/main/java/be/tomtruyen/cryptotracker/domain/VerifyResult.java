package be.tomtruyen.cryptotracker.domain;

import org.springframework.http.HttpStatus;

public enum VerifyResult {
    SUCCESS(true, "Email verified.", HttpStatus.OK),
    SUCCESS_RESEND(true, "Email sent.", HttpStatus.OK),
    ERR_MISSING_PARAMETERS(false, "Missing parameters.", HttpStatus.BAD_REQUEST),
    ERR_EMPTY_EMAIL(false, "Missing email.", HttpStatus.BAD_REQUEST),
    ERR_ALREADY_VERIFIED(true, "Email already verified.", HttpStatus.BAD_REQUEST),
    ERR_NOT_FOUND(false, "Failed to verify email.", HttpStatus.NOT_FOUND),
    ERR_UNKNOWN(false, "Something went wrong. Please try again.", HttpStatus.INTERNAL_SERVER_ERROR);

    private final boolean success;
    private final String message;
    private final HttpStatus status;

    VerifyResult(boolean success, String message, HttpStatus status) {
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
