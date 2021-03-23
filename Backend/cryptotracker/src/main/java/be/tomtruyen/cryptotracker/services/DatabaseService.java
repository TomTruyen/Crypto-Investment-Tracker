package be.tomtruyen.cryptotracker.services;

import be.tomtruyen.cryptotracker.domain.User;
import be.tomtruyen.cryptotracker.interfaces.DatabaseServiceInterface;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.Properties;

@Service
public class DatabaseService implements DatabaseServiceInterface {
    // useAffectedRows=true ==> Makes it so that when no rows are updated the updateCount = 0, otherwise if
    // The query was still found it would return 1, because there was a matching result.
    final private String database = "jdbc:mysql://localhost:3306/cryptotracker?useAffectedRows=true";
    final private String user = "root";
    final private String password = "";

    public Connection connection;

    public DatabaseService() throws SQLException {
        this.connection = getConnection();
    }

    public Connection getConnection() throws SQLException{
        Connection conn;

        Properties properties = new Properties();
        properties.put("user", user);
        properties.put("password", password);

        conn = DriverManager.getConnection(database, properties);

        return conn;
    }

    public void closeConnection() throws SQLException {
        if(connection != null) {
            connection.close();
        }
    }

    public User findUser(String email, String password) throws SQLException {
        String query = "SELECT * FROM users WHERE email = ? AND password = ? LIMIT 1";

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, email);
        preparedStatement.setString(2, password);

        ResultSet rs = preparedStatement.executeQuery();

        User user = null;
        while(rs.next()) {
            int id = rs.getInt("id");
            boolean verified = rs.getBoolean("verified");

            user = new User(id, email, password, verified);
        }

        return user;
    }

    public void addUser(String email, String password) throws SQLException {
        String query = "INSERT INTO users (email, password, verified) VALUES (?, ?, ?)";

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, email);
        preparedStatement.setString(2, password);
        preparedStatement.setBoolean(3, false);

        preparedStatement.executeUpdate();
    }

    public boolean verifyUser(String email) throws SQLException {
        String query = "UPDATE users SET verified = ? WHERE email = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setBoolean(1, true);
        preparedStatement.setString(2, email);

        preparedStatement.executeUpdate();

        return preparedStatement.getUpdateCount() > 0;
    }
}
