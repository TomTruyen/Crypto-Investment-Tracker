package be.tomtruyen.cryptotracker.services;

import be.tomtruyen.cryptotracker.domain.CoingeckoCrypto;
import be.tomtruyen.cryptotracker.domain.Crypto;
import be.tomtruyen.cryptotracker.domain.User;
import be.tomtruyen.cryptotracker.interfaces.DatabaseServiceInterface;
import be.tomtruyen.cryptotracker.repositories.CryptoRepository;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Service
public class DatabaseService implements DatabaseServiceInterface {
    public Connection connection;

    public DatabaseService() throws SQLException {
        this.connection = getConnection();
    }

    public Connection getConnection() throws SQLException{
        String database = "jdbc:mysql://localhost:3306/cryptotracker?useAffectedRows=true&zerodatetimebehavior=Converttonull";
        String user = "admin";
        String password = "root";
//        String user = "root";
//        String password = "";

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

    public List<Crypto> getPortfolio(int userId) throws SQLException {
        List<Crypto> cryptos = new ArrayList<>();

        String query = "SELECT * FROM crypto WHERE user_id = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, userId);

        ResultSet rs = preparedStatement.executeQuery();
        while(rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            String ticker = rs.getString("ticker");
            double buyAmount = rs.getDouble("buy_amount");
            double buyPrice = rs.getDouble("buy_price");
            Date buyDate = rs.getDate("buy_date");
            double sellAmount = rs.getDouble("sell_amount");
            double priceAlert = rs.getDouble("price_alert");

            if(sellAmount < buyAmount) {
                Crypto crypto = new Crypto(id, name, ticker, buyAmount, buyPrice, buyDate, sellAmount, 0, null, priceAlert);
                cryptos.add(crypto);
            }
        }

        return cryptos;
    }

    public List<Crypto> getPortfolioHistory(int userId) throws SQLException {
        List<Crypto> cryptos = new ArrayList<>();

        String query = "SELECT * FROM history WHERE user_id = ? ORDER BY sell_date ASC";

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, userId);

        ResultSet rs = preparedStatement.executeQuery();

        while(rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            String ticker = rs.getString("ticker");
            double buyAmount = rs.getDouble("buy_amount");
            double buyPrice = rs.getDouble("buy_price");
            Date buyDate = rs.getDate("buy_date");
            double sellAmount = rs.getDouble("sell_amount");
            double sellPrice = rs.getDouble("sell_price");
            Date sellDate = rs.getDate("sell_date");

            Crypto crypto = new Crypto(id, name, ticker, buyAmount, buyPrice, buyDate, sellAmount, sellPrice, sellDate);
            cryptos.add(crypto);
        }

        return cryptos;
    }

    public void buyCrypto(int id, String name, String ticker, double buyAmount, double buyPrice) throws SQLException {
        String query = "INSERT INTO crypto (user_id, name, ticker, buy_amount, buy_price, buy_date, price_alert) VALUES (?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, id);
        preparedStatement.setString(2, name);
        preparedStatement.setString(3, ticker);
        preparedStatement.setDouble(4, buyAmount);
        preparedStatement.setDouble(5, buyPrice);
        preparedStatement.setDate(6, new Date(System.currentTimeMillis()));
        preparedStatement.setDouble(7, 0);

        preparedStatement.executeUpdate();
    }

    @Override
    public void sellCrypto(int userId, int id, String name, String ticker, double sellAmount, double sellPrice) throws SQLException {
        Crypto crypto = findCryptoById(id);

        if(crypto == null) throw new SQLException();

        // INSERT INTO HISTORY
        String query = "INSERT INTO history (user_id, name, ticker, buy_amount, buy_price, buy_date, sell_amount, sell_price, sell_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, userId);
        preparedStatement.setString(2, name);
        preparedStatement.setString(3, ticker);
        preparedStatement.setDouble(4, crypto.getBuyAmount());
        preparedStatement.setDouble(5, crypto.getBuyPrice());
        preparedStatement.setDate(6, new Date(crypto.getBuyDate().getTime()));
        preparedStatement.setDouble(7, sellAmount);
        preparedStatement.setDouble(8, sellPrice);
        preparedStatement.setDate(9, new Date(System.currentTimeMillis()));

        preparedStatement.executeUpdate();

        // UPDATE PORTFOLIO
        if(crypto.getSellAmount() + sellAmount >= crypto.getBuyAmount()) {
            query = "DELETE FROM crypto WHERE id = ?";

            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);

        } else {
            query = "UPDATE crypto SET sell_amount = ? WHERE id = ?";

            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setDouble(1, crypto.getSellAmount() + sellAmount);
            preparedStatement.setInt(2, id);

        }

        preparedStatement.executeUpdate();
    }

    public Crypto findCryptoById(int id) throws SQLException {
        String query = "SELECT * FROM crypto WHERE id = ? LIMIT 1";

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, id);

        ResultSet rs = preparedStatement.executeQuery();

        Crypto crypto = null;
        while(rs.next()) {
            String name = rs.getString("name");
            String ticker = rs.getString("ticker");
            double buyAmount = rs.getDouble("buy_amount");
            double buyPrice = rs.getDouble("buy_price");
            Date buyDate = rs.getDate("buy_date");
            double sellAmount = rs.getDouble("sell_amount");
            double priceAlert = rs.getDouble("price_alert");

            crypto = new Crypto(id, name, ticker, buyAmount, buyPrice, buyDate, sellAmount, 0, null, priceAlert);
        }

        return crypto;
    }

    public boolean resetPassword(String email, String password) throws SQLException {
        String query = "UPDATE users SET password = ? WHERE email = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, password);
        preparedStatement.setString(2, email);

        preparedStatement.executeUpdate();

        return preparedStatement.getUpdateCount() <= 0;
    }

    public void setPriceAlert(int userId, int id, double alert) throws SQLException{
        String query = "UPDATE crypto SET price_alert = ? WHERE id = ? AND user_id = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setDouble(1, alert);
        preparedStatement.setInt(2, id);
        preparedStatement.setInt(3, userId);

        preparedStatement.executeUpdate();
    }

    public void deletePriceAlert(int userId, int id) throws SQLException {
        String query = "UPDATE crypto SET price_alert = ? WHERE id = ? AND user_id = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setDouble(1, 0);
        preparedStatement.setInt(2, id);
        preparedStatement.setInt(3, userId);

        preparedStatement.executeUpdate();
    }

    public void checkPriceAlerts(CryptoRepository repository) throws SQLException {
        String query = "SELECT user_id, ticker, price_alert, email FROM crypto INNER JOIN users ON crypto.user_id = users.id WHERE price_alert > 0";

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet rs = preparedStatement.executeQuery();

        while(rs.next()) {
            int userId = rs.getInt("user_id");
            String ticker = rs.getString("ticker");
            double priceAlert = rs.getDouble("price_alert");
            String email = rs.getString("email");

            CoingeckoCrypto crypto = repository.find(ticker);

            if(crypto != null) {
                if(crypto.getPrice() >= priceAlert) {
                    EmailService.sendPriceAlertEmail(email, ticker, priceAlert, crypto.getPrice());

                    try {
                        removePriceAlert(userId, ticker);
                    } catch (SQLException ignored) {}
                }
            }
        }
    }

    private void removePriceAlert(int userId, String ticker) throws SQLException {
        String query = "UPDATE crypto SET price_alert = ? WHERE user_id = ? AND ticker = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setDouble(1, 0);
        preparedStatement.setInt(2, userId);
        preparedStatement.setString(3, ticker);

        preparedStatement.executeUpdate();
    }
}
