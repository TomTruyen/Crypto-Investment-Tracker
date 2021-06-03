package be.tomtruyen.cryptotracker.domain.builder;

import be.tomtruyen.cryptotracker.domain.response.CryptoResponse;
import org.springframework.http.HttpStatus;
import java.util.List;

public class CryptoResponseBuilder {
    private final CryptoResponse cryptoResponse;

    public CryptoResponseBuilder() {
        this.cryptoResponse = new CryptoResponse();
    }

    public CryptoResponseBuilder withPath(String path) {
        this.cryptoResponse.setPath(path);

        return this;
    }

    public CryptoResponseBuilder withMessage(String message) {
        this.cryptoResponse.setMessage(message);

        return this;
    }

    public CryptoResponseBuilder withCrypto(List<?> crypto) {
        this.cryptoResponse.setCrypto(crypto);

        return this;
    }

    public CryptoResponseBuilder withBadRequest() {
        this.cryptoResponse.setStatus(HttpStatus.BAD_REQUEST.value());

        return this;
    }

    public CryptoResponseBuilder withUnauthorized() {
        this.cryptoResponse.setStatus(HttpStatus.UNAUTHORIZED.value());

        return this;
    }

    public CryptoResponseBuilder withNotFound() {
        this.cryptoResponse.setStatus(HttpStatus.NOT_FOUND.value());

        return this;
    }

    public CryptoResponseBuilder withConflict() {
        this.cryptoResponse.setStatus(HttpStatus.CONFLICT.value());

        return this;
    }

    public CryptoResponseBuilder withInternalError() {
        this.cryptoResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());

        return this;
    }

    public CryptoResponseBuilder withOk() {
        this.cryptoResponse.setStatus(HttpStatus.OK.value());
        this.cryptoResponse.setSuccess(true);

        return this;
    }

    public CryptoResponse build() {
        return cryptoResponse;
    }
}
