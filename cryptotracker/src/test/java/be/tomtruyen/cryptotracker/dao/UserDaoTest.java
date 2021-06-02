package be.tomtruyen.cryptotracker.dao;

import be.tomtruyen.cryptotracker.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Random;

@DataJpaTest
public class UserDaoTest {
    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private UserDao userDao;

    private User user;

    @BeforeEach
    void init() {
        user = new User();
        user.setEmail("user@test.dao");
        user.setPassword("password");
        user.setVerified(true);

        testEntityManager.persist(user);
        testEntityManager.flush();
        testEntityManager.clear();
    }

    @Test
    void returnsUserIfEmailFound() {
        User foundUser = userDao.findUserByEmail(user.getEmail());

        Assertions.assertNotNull(foundUser);
        Assertions.assertEquals(user.getEmail(), foundUser.getEmail());
        Assertions.assertEquals(user.getPassword(), foundUser.getPassword());
        Assertions.assertEquals(user.isVerified(), foundUser.isVerified());
    }

    @Test
    void returnsNullIfEmailNotFound() {
        User foundUser = userDao.findUserByEmail("");

        Assertions.assertNull(foundUser);
    }

    @Test
    void returnsUserIfEmailPasswordFound() {
        User foundUser = userDao.findUserByEmailAndPassword(user.getEmail(), user.getPassword());

        Assertions.assertNotNull(foundUser);
        Assertions.assertEquals(user.getEmail(), foundUser.getEmail());
        Assertions.assertEquals(user.getPassword(), foundUser.getPassword());
        Assertions.assertEquals(user.isVerified(), foundUser.isVerified());
    }

    @Test
    void returnsNullIfEmailPasswordComboNotFound() {
        User foundUser = userDao.findUserByEmailAndPassword("", "");

        Assertions.assertNull(foundUser);
    }
}
