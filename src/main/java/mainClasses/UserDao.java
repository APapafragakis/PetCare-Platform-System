package mainClasses;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/your_database";
    private static final String DB_USER = "your_username";
    private static final String DB_PASSWORD = "your_password";

    // SQL query to insert a new user into the database
    private static final String INSERT_USER_QUERY = "INSERT INTO users (username, email, password, /* other columns */) VALUES (?, ?, ?, /* other values */)";

    
    public boolean checkUserExists(String username, String email) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            String query = "SELECT * FROM users WHERE username = ? OR email = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, email);
            resultSet = statement.executeQuery();

            return resultSet.next(); 
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            close(connection, statement, resultSet);
        }
    }
    
    public boolean registerUsers(String username, String email, String password,
            String name, String surname, String dob, String gender,
            String userType, boolean indoorAccommodation, boolean outdoorAccommodation,
            boolean hostCat, boolean hostDog, String catPrice, String dogPrice,
            String description, String country, String city, String address,
            String personalPage, String job, String mobilePhone, boolean agreement) {

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USER_QUERY)) {

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, password);

            // Execute the query
            int rowsAffected = preparedStatement.executeUpdate();

            // Check if the registration was successful
            return rowsAffected > 0;
        } catch (SQLException e) {
            // Handle exceptions, log errors, etc.
            e.printStackTrace();
            return false;
        }
    }
    
    private void close(Connection connection, PreparedStatement statement, ResultSet resultSet) {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
