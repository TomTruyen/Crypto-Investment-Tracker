package be.tomtruyen.cryptotracker.domain;

import org.springframework.http.HttpStatus;

public enum VerifyResult {
    SUCCESS(true, "", HttpStatus.OK),
    ERR_NOT_FOUND(false, "Email not found.", HttpStatus.NOT_FOUND),
    ERR_ALREADY_VERIFIED(false, "Email already verified.", HttpStatus.CONFLICT),
    ERR_UNKNOWN(false, "Something went wrong. Please try again.", HttpStatus.INTERNAL_SERVER_ERROR),
    ERR_MISSING_PARAMETERS(false, "Missing parameters.", HttpStatus.BAD_REQUEST);


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
