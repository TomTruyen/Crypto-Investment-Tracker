package be.tomtruyen.cryptotracker.domain.builder;

import be.tomtruyen.cryptotracker.domain.response.UserResponse;
import org.springframework.http.HttpStatus;

public class UserResponseBuilder {
    private final UserResponse userResponse;

    public UserResponseBuilder() {
        this.userResponse = new UserResponse();
    }

    public UserResponseBuilder withPath(String path) {
        this.userResponse.setPath(path);

        return this;
    }

    public UserResponseBuilder withMessage(String message) {
        this.userResponse.setMessage(message);

        return this;
    }

    public UserResponseBuilder withToken(String token) {
        this.userResponse.setToken(token);

        return this;
    }

    public UserResponseBuilder withBadRequest() {
        this.userResponse.setStatus(HttpStatus.BAD_REQUEST.value());

        return this;
    }

    public UserResponseBuilder withNotFound() {
        this.userResponse.setStatus(HttpStatus.NOT_FOUND.value());

        return this;
    }

    public UserResponseBuilder withConflict() {
        this.userResponse.setStatus(HttpStatus.CONFLICT.value());

        return this;
    }

    public UserResponseBuilder withInternalError() {
        this.userResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());

        return this;
    }

    public UserResponseBuilder withOk() {
        this.userResponse.setStatus(HttpStatus.OK.value());
        this.userResponse.setSuccess(true);

        return this;
    }

    public UserResponse build() {
        return userResponse;
    }
}
