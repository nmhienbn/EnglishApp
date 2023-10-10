package models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class DatabaseConnect {

    public static void main(String[] args) {
        // Database connection parameters
        String jdbcURL = "jdbc:mysql://localhost:3307/englishapp";
        String username = "root";
        String password = "minhhien";


        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            // Establish a database connection
            connection = DriverManager.getConnection(jdbcURL, username, password);

            // SQL query to select the explanation for the given word
            String sqlQuery = "SELECT word_explain FROM dictionary WHERE word_target = ?";
            preparedStatement = connection.prepareStatement(sqlQuery);
            Scanner cin = new Scanner(System.in);
            while (true) {
                // Word to search for
                String wordTarget = cin.next();
                preparedStatement.setString(1, wordTarget);

                // Execute the query
                resultSet = preparedStatement.executeQuery();

                // Check if a result is found
                if (resultSet.next()) {
                    String explanation = resultSet.getString("word_explain");
                    System.out.println("Explanation for '" + wordTarget + "':\n" + explanation);
                } else {
                    System.out.println("Word not found in the dictionary.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                // Close resources
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
