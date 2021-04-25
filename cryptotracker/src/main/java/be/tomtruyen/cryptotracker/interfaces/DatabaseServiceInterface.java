package be.tomtruyen.cryptotracker.interfaces;

import be.tomtruyen.cryptotracker.domain.Crypto;
import be.tomtruyen.cryptotracker.domain.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface DatabaseServiceInterface {
    Connection getConnection() throws SQLException;
    void closeConnection()  throws SQLException;
    User findUser(String email, String password) throws SQLException;
    User findUserByEmail(String email) throws SQLException;
    void addUser(String email, String password) throws SQLException;
    boolean verifyUser(String email) throws SQLException;
    List<Crypto> getPortfolio(int userId) throws SQLException;
    List<Crypto> getPortfolioHistory(int userId) throws SQLException;
    void buyCrypto(int id, String name, String ticker, double buyAmount, double buyPrice) throws SQLException;
    void sellCrypto(int userId, int id, String name, String ticker, double sellAmount, double sellPrice, boolean isGas) throws SQLException;
}
