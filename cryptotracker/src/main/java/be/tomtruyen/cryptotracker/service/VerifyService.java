package be.tomtruyen.cryptotracker.service;

import be.tomtruyen.cryptotracker.dao.UserDao;
import be.tomtruyen.cryptotracker.domain.User;
import be.tomtruyen.cryptotracker.domain.builder.UserResponseBuilder;
import be.tomtruyen.cryptotracker.domain.response.UserResponse;
import be.tomtruyen.cryptotracker.rest.resources.VerifyResource;
import be.tomtruyen.cryptotracker.util.Utils;
import be.tomtruyen.cryptotracker.util.exception.UserAlreadyVerifiedException;
import be.tomtruyen.cryptotracker.util.exception.UserNotFoundException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class VerifyService {

    private static final Logger LOGGER = LogManager.getLogger(VerifyService.class);

    private final UserDao userDao;

    public VerifyService(UserDao userDao) {
        this.userDao = userDao;
    }

    public UserResponse verify(VerifyResource verifyResource) {
        try {
            User user = userDao.findUserByEmail(verifyResource.getEmail());

            if(user == null) throw new UserNotFoundException("Email not found", "/verify");
            if(user.isVerified()) throw new UserAlreadyVerifiedException("User already verified", "/verify");

            user.setVerified(true);
            
            userDao.save(user);

            return new UserResponseBuilder().withPath("/verify").withOk().build();
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, Utils.createErrorMessage("verify", e.getMessage()));
            return new UserResponseBuilder().withPath("/verify").withInternalError().build();
        }
    }
}
