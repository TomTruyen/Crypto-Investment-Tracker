package be.tomtruyen.cryptotracker.service;

import be.tomtruyen.cryptotracker.dao.UserDao;
import be.tomtruyen.cryptotracker.domain.User;
import be.tomtruyen.cryptotracker.domain.response.UserResponse;
import be.tomtruyen.cryptotracker.model.UserResourceBuilder;
import be.tomtruyen.cryptotracker.rest.resources.EmailResource;
import be.tomtruyen.cryptotracker.rest.resources.UserResource;
import be.tomtruyen.cryptotracker.util.exception.UserInvalidPasswordException;
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
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ResetPasswordServiceTest {
    @Mock
    private UserDao userDao;

    @InjectMocks
    private ResetPasswordService resetPasswordService;
    private UserResource userResource;
    private EmailResource emailResource;

    @Captor
    private ArgumentCaptor<User> userCaptor;

    @BeforeEach
    void init() {
        userResource = UserResourceBuilder.anUserResource().build();
        emailResource = new EmailResource(userResource.getEmail());
    }

    @Test
    void resetThrowsUserNotFoundExceptionWhenUserNotFound() {
        when(userDao.findUserByEmail(anyString())).thenReturn(null);

        Assertions.assertThrows(UserNotFoundException.class, () -> resetPasswordService.reset(emailResource));
    }

    @Test
    void resetResponseStatusOkWhenUserFound() {
        User user = new User();
        user.setEmail(userResource.getEmail());

        when(userDao.findUserByEmail(userResource.getEmail())).thenReturn(user);

        UserResponse userResponse = resetPasswordService.reset(emailResource);

        Assertions.assertNotNull(userResponse);
        Assertions.assertEquals(200, userResponse.getStatus());
        Assertions.assertEquals("/resetpassword", userResponse.getPath());
    }

    @Test
    void confirmThrowsUserInvalidPasswordExceptionWhenPasswordIsWeak() {
        userResource.setPassword("weak");

        Assertions.assertThrows(UserInvalidPasswordException.class, () -> resetPasswordService.confirm(userResource));
    }

    @Test
    void confirmThrowsUserNotFoundExceptionWhenUserNotFound() {
        when(userDao.findUserByEmail(anyString())).thenReturn(null);

        Assertions.assertThrows(UserNotFoundException.class, () -> resetPasswordService.confirm(userResource));
    }

    @Test
    void confirmResponseStatusOkWhenConfirmed() {
        User user = new User();

        when(userDao.findUserByEmail(userResource.getEmail())).thenReturn(user);

        UserResponse userResponse = resetPasswordService.confirm(userResource);

        Assertions.assertNotNull(userResponse);
        Assertions.assertEquals(200, userResponse.getStatus());
        Assertions.assertEquals("/resetpassword/confirm", userResponse.getPath());
    }
}
