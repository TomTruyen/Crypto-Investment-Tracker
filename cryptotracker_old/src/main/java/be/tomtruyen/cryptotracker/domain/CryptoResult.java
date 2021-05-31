package be.tomtruyen.cryptotracker.domain;

import org.springframework.http.HttpStatus;

public enum CryptoResult {
    SUCCESS(true, "", HttpStatus.OK),
    ERR_MISSING_TOKEN(false, "Missing authorization.", HttpStatus.BAD_REQUEST),
    ERR_MISSING_PARAMETERS(false, "Missing parameters.", HttpStatus.BAD_REQUEST),
    ERR_INVALID_AUTH(false, "Invalid authorization.", HttpStatus.BAD_REQUEST),
    ERR_EXPIRED_TOKEN(false, "Token expired.", HttpStatus.BAD_REQUEST),
    ERR_NAME_EMPTY(false, "Name can't be empty.", HttpStatus.BAD_REQUEST),
    ERR_TICKER_EMPTY(false, "Name can't be empty.", HttpStatus.BAD_REQUEST),
    ERR_PRICE_GREATER_THAN_ZERO(false, "Price must be greater than 0.", HttpStatus.BAD_REQUEST),
    ERR_AMOUNT_GREATER_THAN_ZERO(false, "Amount must be greater than 0.", HttpStatus.BAD_REQUEST),
    ERR_UNKNOWN(false, "Something went wrong. Please try again.", HttpStatus.INTERNAL_SERVER_ERROR),
    ERR_AMOUNT_TOO_LARGE(false, "You can't sell more than you own.", HttpStatus.BAD_REQUEST);

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
