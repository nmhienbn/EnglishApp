package models.databases;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class DatabaseMySQL extends DatabaseSQL {
    private static final DatabaseMySQL ins = new DatabaseMySQL();
    private static final String jdbcURL = "jdbc:mysql://localhost:3307/englishapp";
    private static String username = "root";
    private static String password = "minhhien";

    private static Connection connection = null;
    private static PreparedStatement preparedStatement = null;
    private static ResultSet resultSet = null;

    private DatabaseMySQL() {
    }

    public static DatabaseMySQL getInstance() {
        return ins;
    }

    private void getAccessToDatabase() {
        Scanner cin = new Scanner(System.in);
        System.out.print("Enter username: ");
        username = cin.next();
        System.out.print("Enter password: ");
        password = cin.next();
    }

    @Override
    public String dictionaryLookup(String wordTarget) throws SQLException {

        // SQL query to select the explanation for the given word
        String sqlQuery = "SELECT word_explain FROM dictionary WHERE word_target = ?";
        preparedStatement = connection.prepareStatement(sqlQuery);

        // Word to search for
        preparedStatement.setString(1, wordTarget);

        // Execute the query
        resultSet = preparedStatement.executeQuery();

        // Check if a result is found
        if (resultSet.next()) {
            String explanation = resultSet.getString("word_explain");
            return "Explanation for '" + wordTarget + "':\n" + explanation;
        } else {
            return null;
        }
    }

    @Override
    public ArrayList<Word> dictionarySearcher(String wordTarget) throws SQLException {

        // SQL query to select the explanation for the given word
        String sqlQuery = "SELECT word_target, word_explain FROM dictionary WHERE word_target LIKE ?";
        preparedStatement = connection.prepareStatement(sqlQuery);

        // Word to search for
        preparedStatement.setString(1, wordTarget + "%");

        // Execute the query
        resultSet = preparedStatement.executeQuery();

        ArrayList<Word> res = new ArrayList<>();
        // Check if a result is found
        while (resultSet.next()) {
            String target = resultSet.getString("word_target");
            String explain = resultSet.getString("word_explain");
            res.add(new Word(target, explain));
        }

        return res;
    }

    @Override
    public boolean dictionaryAddWord(String wordTarget, String wordExplain) throws SQLException {
        if (dictionaryLookup(wordTarget) != null) {
            return false;
        }
        // SQL query to insert a new word into the dictionary table
        String sqlQuery = "INSERT INTO dictionary (word_target, word_explain) VALUES (?, ?)";
        preparedStatement = connection.prepareStatement(sqlQuery);
        preparedStatement.setString(1, wordTarget);
        preparedStatement.setString(2, wordExplain);
        int rowsAffected = preparedStatement.executeUpdate();

        return rowsAffected > 0;
    }

    @Override
    public boolean dictionaryDeleteWord(String wordTarget) throws SQLException {
        // Disable safe delete mode
        connection.setAutoCommit(false);
        connection.createStatement().execute("SET SQL_SAFE_UPDATES = 0");

        // SQL query to delete the word by word_target
        String deleteQuery = "DELETE FROM dictionary WHERE word_target = ?";
        PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery);
        deleteStatement.setString(1, wordTarget);

        // Execute the delete query
        int rowsAffected = deleteStatement.executeUpdate();

        // Revert safe delete mode to 1 and commit the transaction
        connection.createStatement().execute("SET SQL_SAFE_UPDATES = 1");
        connection.commit();

        return rowsAffected > 0;
    }

    @Override
    public ArrayList<Word> getAllWords() throws SQLException {
        return dictionarySearcher("");
    }

    public static void main(String[] args) {
//        getAccessToDatabase();

        try {
            // Establish a database connection
            connection = DriverManager.getConnection(jdbcURL, username, password);
            ArrayList<Word> tmp1 = ins.getAllWords();
            for (Word word : tmp1) {
                System.out.println(word.getWordTarget());
            }
            Scanner cin = new Scanner(System.in);
            while (cin.hasNext()) {
                String wordTarget = cin.next();
                System.out.println(ins.dictionaryLookup(wordTarget));
                ArrayList<Word> tmp = ins.dictionarySearcher(wordTarget);
                for (Word word : tmp) {
                    System.out.println("- " + word.getWordTarget());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        } finally {
            try {
                // Close resources
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace(System.out);
            }
        }
    }
}
