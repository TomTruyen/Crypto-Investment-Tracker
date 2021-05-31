package be.tomtruyen.cryptotracker.dao;

import be.tomtruyen.cryptotracker.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends JpaRepository<User, Long> {
    User findUserByEmail(String email);
    User findUserByEmailAndPassword(String email, String password);
}
