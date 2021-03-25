package be.tomtruyen.cryptotracker.services;

import be.tomtruyen.cryptotracker.domain.Crypto;
import be.tomtruyen.cryptotracker.domain.User;
import be.tomtruyen.cryptotracker.interfaces.DatabaseServiceInterface;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

@Service
public class DatabaseService implements DatabaseServiceInterface {
    public Connection connection;

    public DatabaseService() throws SQLException {
        this.connection = getConnection();
    }

    public Connection getConnection() throws SQLException{
        String database = "jdbc:mysql://localhost:3306/cryptotracker?useAffectedRows=true";
        String user = "root";
        String password = "";

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

    public User findUserByEmail(String email) throws SQLException {
        String query = "SELECT * FROM users WHERE email = ? LIMIT 1";

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, email);

        ResultSet rs = preparedStatement.executeQuery();

        User user = null;
        while(rs.next()) {
            int id = rs.getInt("id");
            String password = rs.getString("password");
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

        return preparedStatement.getUpdateCount() <= 0;
    }

    public List<Crypto> getCryptos(int userId) throws SQLException {
        List<Crypto> cryptos = new ArrayList<>();

        String query = "SELECT * FROM crypto WHERE user_id = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, userId);

        ResultSet rs = preparedStatement.executeQuery();
        while(rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            String ticker = rs.getString("ticker");
            int buyAmount = rs.getInt("buy_amount");
            double buyPrice = rs.getDouble("buy_price");
            Date buyDate = rs.getDate("buy_date");
            int sellAmount = rs.getInt("sell_amount");
            double sellPrice = rs.getDouble("sell_price");
            Date sellDate = rs.getDate("sell_date");

            Crypto crypto = new Crypto(id, name, ticker, buyAmount, buyPrice, buyDate, sellAmount, sellPrice, sellDate);
            cryptos.add(crypto);
        }

        return cryptos;

    }
}
