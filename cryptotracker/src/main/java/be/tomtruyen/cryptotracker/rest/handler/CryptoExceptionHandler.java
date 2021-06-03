package be.tomtruyen.cryptotracker.rest.handler;

import be.tomtruyen.cryptotracker.domain.builder.CryptoResponseBuilder;
import be.tomtruyen.cryptotracker.domain.builder.UserResponseBuilder;
import be.tomtruyen.cryptotracker.domain.response.CryptoResponse;
import be.tomtruyen.cryptotracker.domain.response.UserResponse;
import be.tomtruyen.cryptotracker.util.exception.InvalidTokenException;
import be.tomtruyen.cryptotracker.util.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CryptoExceptionHandler {
    @ExceptionHandler(value = InvalidTokenException.class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public CryptoResponse userNotFoundException(InvalidTokenException e) {
        return new CryptoResponseBuilder().withPath(e.getPath()).withMessage(e.getMessage()).withUnauthorized().build();
    }
}
