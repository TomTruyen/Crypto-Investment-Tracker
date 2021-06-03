package be.tomtruyen.cryptotracker.rest.handler;

import be.tomtruyen.cryptotracker.domain.builder.CryptoResponseBuilder;
import be.tomtruyen.cryptotracker.domain.builder.UserResponseBuilder;
import be.tomtruyen.cryptotracker.domain.response.CryptoResponse;
import be.tomtruyen.cryptotracker.domain.response.UserResponse;
import be.tomtruyen.cryptotracker.util.Utils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.util.NestedServletException;

import java.util.Objects;

@RestControllerAdvice
public class JpaExceptionHandler {
    private static final Logger LOGGER = LogManager.getLogger(JpaExceptionHandler.class);

    // Custom JPA Valid exception
    @ExceptionHandler(value = BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public UserResponse jpaValidException(BindException e) {
        String error = "";

        if(e.hasErrors()) {
            try {
                if(e.getFieldError("email") != null) {
                    error = Objects.requireNonNull(e.getFieldError("email")).getDefaultMessage();
                } else if (e.getFieldError("password") != null) {
                    error = Objects.requireNonNull(e.getFieldError("password")).getDefaultMessage();
                }
            } catch (NullPointerException ex) {
                LOGGER.error(Utils.createErrorMessage("jpa validation", ex.getMessage()));
            }
        }

        return new UserResponseBuilder().withPath("/error").withMessage(error).withBadRequest().build();
    }

    // Custom missing request header exception (crypto)
    @ExceptionHandler(value = MissingRequestHeaderException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CryptoResponse jpaHeaderException(MissingRequestHeaderException e) {
        LOGGER.error(Utils.createErrorMessage("missing header", e.getMessage()));

        return new CryptoResponseBuilder().withPath("/error").withMessage(e.getMessage()).withBadRequest().build();
    }
}
