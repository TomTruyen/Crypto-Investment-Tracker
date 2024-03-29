package be.tomtruyen.cryptotracker.service;

import be.tomtruyen.cryptotracker.dao.UserDao;
import be.tomtruyen.cryptotracker.domain.User;
import be.tomtruyen.cryptotracker.domain.builder.UserResponseBuilder;
import be.tomtruyen.cryptotracker.domain.response.UserResponse;
import be.tomtruyen.cryptotracker.rest.resources.UserResource;
import be.tomtruyen.cryptotracker.util.Utils;
import be.tomtruyen.cryptotracker.util.exception.UserNotFoundException;
import be.tomtruyen.cryptotracker.util.exception.UserNotVerifiedException;
import be.tomtruyen.cryptotracker.util.jwt.JwtService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    private static final Logger LOGGER = LogManager.getLogger(LoginService.class);

    private final JwtService jwtService;

    private final UserDao userDao;

    public LoginService(JwtService jwtService, UserDao userDao) {
        this.jwtService = jwtService;
        this.userDao = userDao;
    }

    public UserResponse login(UserResource userResource) {
            String password = Utils.hashPassword(userResource.getPassword());

            User user = userDao.findUserByEmailAndPassword(userResource.getEmail(), password);

            if (user == null) throw new UserNotFoundException("Invalid email/password combination", "/login");

            if (!user.isVerified()) throw new UserNotVerifiedException("Email not verified", "/login");

            String token = jwtService.generateToken(user.getId(), user.getEmail(), null);

            return new UserResponseBuilder().withPath("/login").withToken(token).withOk().build();
    }
}
