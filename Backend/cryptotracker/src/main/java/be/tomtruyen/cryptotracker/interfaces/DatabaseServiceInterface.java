package be.tomtruyen.cryptotracker.interfaces;

import be.tomtruyen.cryptotracker.domain.User;

import java.sql.Connection;
import java.sql.SQLException;

public interface DatabaseServiceInterface {
    Connection getConnection() throws SQLException;
    void closeConnection()  throws SQLException;
    User findUser(String email, String password) throws SQLException;
    void addUser(String email, String password) throws SQLException;
    boolean verifyUser(String email) throws SQLException;
}
