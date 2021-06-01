package be.tomtruyen.cryptotracker.rest.handler;

import be.tomtruyen.cryptotracker.domain.builder.UserResponseBuilder;
import be.tomtruyen.cryptotracker.domain.response.UserResponse;
import be.tomtruyen.cryptotracker.rest.LoginController;
import be.tomtruyen.cryptotracker.util.Utils;
import be.tomtruyen.cryptotracker.util.exception.*;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

@RestControllerAdvice
public class UserExceptionHandler {
    private static final Logger LOGGER = LogManager.getLogger(UserExceptionHandler.class);

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
                LOGGER.log(Level.ERROR, Utils.createErrorMessage(ex.getMessage()));
            }
        }

        return new UserResponseBuilder().withMessage(error).withBadRequest().build();
    }

    @ExceptionHandler(value = UserNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public UserResponse userNotFoundException(UserNotFoundException e) {
        return new UserResponseBuilder().withPath(e.getPath()).withMessage(e.getMessage()).withNotFound().build();
    }

    @ExceptionHandler(value = UserNotVerifiedException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public UserResponse userNotVerifiedException(UserNotVerifiedException e) {
        return new UserResponseBuilder().withPath(e.getPath()).withMessage(e.getMessage()).withBadRequest().build();
    }

    @ExceptionHandler(value = UserInvalidEmailException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public UserResponse userInvalidEmailException(UserInvalidEmailException e) {
        return new UserResponseBuilder().withPath(e.getPath()).withMessage(e.getMessage()).withBadRequest().build();
    }

    @ExceptionHandler(value = UserInvalidPasswordException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public UserResponse userInvalidPasswordException(UserNotVerifiedException e) {
        return new UserResponseBuilder().withPath(e.getPath()).withMessage(e.getMessage()).withBadRequest().build();
    }

    @ExceptionHandler(value = UserAlreadyExistsException.class)
    @ResponseStatus(value = HttpStatus.CONFLICT)
    public UserResponse userConflictException(UserAlreadyExistsException e) {
        return new UserResponseBuilder().withPath(e.getPath()).withMessage(e.getMessage()).withConflict().build();
    }
}
