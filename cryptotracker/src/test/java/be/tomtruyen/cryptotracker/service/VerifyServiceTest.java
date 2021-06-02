package be.tomtruyen.cryptotracker.service;

import be.tomtruyen.cryptotracker.dao.UserDao;
import be.tomtruyen.cryptotracker.domain.User;
import be.tomtruyen.cryptotracker.domain.response.UserResponse;
import be.tomtruyen.cryptotracker.rest.resources.EmailResource;
import be.tomtruyen.cryptotracker.util.email.EmailService;
import be.tomtruyen.cryptotracker.util.exception.UserAlreadyVerifiedException;
import be.tomtruyen.cryptotracker.util.exception.UserNotFoundException;
import io.jsonwebtoken.lang.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class VerifyServiceTest {
    @Mock
    private UserDao userDao;

    @InjectMocks
    private VerifyService verifyService;
    private EmailResource emailResource;

    @Captor
    private ArgumentCaptor<User> userCaptor;

    @BeforeEach
    void init() {
        emailResource = new EmailResource();
        emailResource.setEmail("test@verify.service");
    }

    @Test
    void verifyThrowsUserNotFoundExceptionWhenUserNotFound() {
        when(userDao.findUserByEmail(anyString())).thenReturn(null);

        Assertions.assertThrows(UserNotFoundException.class, () -> verifyService.verify(emailResource));
    }

    @Test
    void verifyThrowsUserAlreadyVerifiedExceptionWhenUserVerified() {
        User user = new User();
        user.setVerified(true);

        when(userDao.findUserByEmail(emailResource.getEmail())).thenReturn(user);

        Assertions.assertThrows(UserAlreadyVerifiedException.class, () -> verifyService.verify(emailResource));
    }

    @Test
    void verifyResponseStatusOkWhenVerified() {
        User user = new User();

        when(userDao.findUserByEmail(emailResource.getEmail())).thenReturn(user);

        UserResponse userResponse = verifyService.verify(emailResource);

        Assertions.assertNotNull(userResponse);
        Assertions.assertEquals(200, userResponse.getStatus());
        Assertions.assertEquals("/verify", userResponse.getPath());
    }
}
