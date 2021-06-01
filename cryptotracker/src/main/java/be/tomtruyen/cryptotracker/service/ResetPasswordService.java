package be.tomtruyen.cryptotracker.service;

import be.tomtruyen.cryptotracker.dao.UserDao;
import be.tomtruyen.cryptotracker.domain.User;
import be.tomtruyen.cryptotracker.domain.builder.UserResponseBuilder;
import be.tomtruyen.cryptotracker.domain.response.UserResponse;
import be.tomtruyen.cryptotracker.rest.resources.EmailResource;
import be.tomtruyen.cryptotracker.rest.resources.UserResource;
import be.tomtruyen.cryptotracker.util.Utils;
import be.tomtruyen.cryptotracker.util.email.EmailService;
import be.tomtruyen.cryptotracker.util.exception.UserInvalidPasswordException;
import be.tomtruyen.cryptotracker.util.exception.UserNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class ResetPasswordService {
    private static final Logger LOGGER = LogManager.getLogger(ResetPasswordService.class);

    private final UserDao userDao;

    public ResetPasswordService(UserDao userDao) {
        this.userDao = userDao;
    }

    public UserResponse reset(EmailResource emailResource) {
        User user = userDao.findUserByEmail(emailResource.getEmail());

        if (user == null) throw new UserNotFoundException("Email not found", "/resetpassword");

        EmailService.sendResetPasswordEmail(user.getEmail());

        return new UserResponseBuilder().withPath("/resetpassword").withOk().build();
    }

    public UserResponse confirm(UserResource userResource) {
        if (!Utils.isValidPassword(userResource.getPassword()))
            throw new UserInvalidPasswordException("Password is too weak", "/resetpassword/confirm");

        User user = userDao.findUserByEmail(userResource.getEmail());

        if (user == null) throw new UserNotFoundException("Email not found", "/resetpassword/confirm");

        user.setPassword(Utils.hashPassword(userResource.getPassword()));

        userDao.save(user);

        return new UserResponseBuilder().withPath("/resetpassword/confirm").withOk().build();
    }
}
