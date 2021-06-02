package be.tomtruyen.cryptotracker.service;

import be.tomtruyen.cryptotracker.dao.UserDao;
import be.tomtruyen.cryptotracker.domain.User;
import be.tomtruyen.cryptotracker.domain.response.UserResponse;
import be.tomtruyen.cryptotracker.model.UserResourceBuilder;
import be.tomtruyen.cryptotracker.rest.resources.UserResource;
import be.tomtruyen.cryptotracker.util.exception.UserAlreadyExistsException;
import be.tomtruyen.cryptotracker.util.exception.UserInvalidPasswordException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Random;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RegisterServiceTest {
    private final Random random = new Random();

    @Mock
    private UserDao userDao;

    @InjectMocks
    private RegisterService registerService;
    private UserResource userResource;

    @Captor
    private ArgumentCaptor<User> userCaptor;

    @BeforeEach
    void init() { userResource = UserResourceBuilder.anUserResource().build(); }

    @Test
    void throwsUserInvalidPasswordExceptionWhenPasswordIsWeak() {
        userResource.setPassword("weak");

        Assertions.assertThrows(UserInvalidPasswordException.class, () -> registerService.register(userResource));
    }

    @Test
    void throwsUserAlreadyExistsExceptionWhenUserExists() {
        User user = new User();
        user.setEmail(userResource.getEmail());
        user.setPassword(userResource.getPassword());

        when(userDao.findUserByEmail(anyString())).thenReturn(user);

        Assertions.assertThrows(UserAlreadyExistsException.class, () -> registerService.register(userResource));
    }

    @Test
    void responseStatusOkWhenUserRegistered() {
        when(userDao.findUserByEmail(anyString())).thenReturn(null);

        UserResponse userResponse = registerService.register(userResource);

        Assertions.assertNotNull(userResponse);
        Assertions.assertEquals(200, userResponse.getStatus());
        Assertions.assertEquals("/register", userResponse.getPath());

    }
}
