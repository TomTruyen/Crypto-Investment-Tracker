package be.tomtruyen.cryptotracker.service;

import be.tomtruyen.cryptotracker.dao.UserDao;
import be.tomtruyen.cryptotracker.domain.User;
import be.tomtruyen.cryptotracker.domain.builder.UserResponseBuilder;
import be.tomtruyen.cryptotracker.domain.response.UserResponse;
import be.tomtruyen.cryptotracker.rest.resources.UserResource;
import be.tomtruyen.cryptotracker.util.Utils;
import be.tomtruyen.cryptotracker.util.email.EmailService;
import be.tomtruyen.cryptotracker.util.exception.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class RegisterService {
    private static final Logger LOGGER = LogManager.getLogger(LoginService.class);

    private final UserDao userDao;

    public RegisterService(UserDao userDao) {
        this.userDao = userDao;
    }

    public UserResponse register(UserResource userResource) {
        if(!Utils.isValidEmail(userResource.getEmail())) throw new UserInvalidEmailException("Email is invalid", "/register");

        if(!Utils.isValidPassword(userResource.getPassword())) throw new UserInvalidPasswordException("Password is too weak", "/register");

        User user = userDao.findUserByEmail(userResource.getEmail());

        if(user != null) throw new UserAlreadyExistsException("Email is already being used", "/register");

        user = new User();
        user.setEmail(userResource.getEmail());
        user.setPassword(Utils.hashPassword(userResource.getPassword()));
        user.setVerified(false);

        userDao.save(user);

        EmailService.sendVerificationEmail(user.getEmail());

        return new UserResponseBuilder().withPath("/register").withOk().build();
    }
}
