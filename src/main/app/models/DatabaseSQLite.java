package models;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class DatabaseSQLite {
    private static final DatabaseSQLite ins = new DatabaseSQLite();
    private static final String jdbcURL = "jdbc:sqlite:" + System.getProperty("user.dir") + "\\src\\main\\resources\\SQLite\\engData.db";
    private static Connection connection = null;
    private static PreparedStatement preparedStatement = null;
    private static ResultSet resultSet = null;

    private DatabaseSQLite() {
    }

    public static DatabaseSQLite getInstance() {
        return ins;
    }

    public String toOutputString(ResultSet resultSet) throws SQLException {
        String column1Data = resultSet.getString("word");
        column1Data = column1Data.toLowerCase();
        String column2Data = resultSet.getString("pronunciation");
        String column3Data = resultSet.getString("meaning");
        String column4Data = resultSet.getString("synonym");
        String column5Data = resultSet.getString("antonyms");

        String res = column1Data + "\t";
        if (column2Data != null)
            res += column2Data.replace("\\", "").replace("\n", "\\") + "\\";
        if (column3Data != null)
            res += column3Data.replace("\\", "").replace("\n", "\\") + "\\";
        if (column4Data != null)
            res += "*Từ đồng nghĩa:\\" + column4Data.replace("\\", "").replace("\n", "\\") + "\\";
        if (column5Data != null)
            res += "*Từ trái nghĩa:\\" + column5Data.replace("\\", "").replace("\n", "\\");
        return res;
    }

    public String toString(ResultSet resultSet) throws SQLException {
        String column2Data = resultSet.getString("pronunciation");
        String column3Data = resultSet.getString("meaning");
        String column4Data = resultSet.getString("synonym");
        String column5Data = resultSet.getString("antonyms");

        String res = "";
        if (column2Data != null)
            res += column2Data + "\n";
        if (column3Data != null)
            res += column3Data + "\n";
        if (column4Data != null)
            res += "*Từ đồng nghĩa:\n" + column4Data + "\n";
        if (column5Data != null)
            res += "*Từ trái nghĩa:\n" + column5Data;
        return res;
    }

    public String dictionaryLookup(String wordTarget) throws SQLException {
        // SQL query to select the explanation for the given word
        String sqlQuery = "SELECT * FROM engviet WHERE Word = ?";
        preparedStatement = connection.prepareStatement(sqlQuery);

        // Word to search for
        preparedStatement.setString(1, wordTarget);

        // Execute the query
        resultSet = preparedStatement.executeQuery();

        // Check if a result is found
        if (resultSet.next()) {
            String explanation = toString(resultSet);
            return "Explanation for '" + wordTarget + "':\n" + explanation;
        } else {
            return null;
        }
    }

    public ArrayList<Word> dictionarySearcher(String wordTarget) throws SQLException {
        // SQL query to select the explanation for the given word
        String sqlQuery = "SELECT * FROM engviet WHERE Word LIKE ?";
        preparedStatement = connection.prepareStatement(sqlQuery);

        // Word to search for
        preparedStatement.setString(1, wordTarget + "%");

        // Execute the query
        resultSet = preparedStatement.executeQuery();

        ArrayList<Word> res = new ArrayList<>();
        // Check if a result is found
        while (resultSet.next()) {
            String target = resultSet.getString("word");
            String explain = toString(resultSet);
            res.add(new Word(target, explain));
        }

        return res;
    }

    public boolean dictionaryDeleteWord(String wordTarget) throws SQLException {
        if (dictionaryLookup(wordTarget) == null) {
            return false;
        }
        // SQL query to insert a new word into the dictionary table
        String sqlQuery = "DELETE FROM engviet WHERE Word = ?";
        preparedStatement = connection.prepareStatement(sqlQuery);
        preparedStatement.setString(1, wordTarget);
        int rowsAffected = preparedStatement.executeUpdate();

        return rowsAffected > 0;
    }

    public ArrayList<Word> getAllWords() throws SQLException {
        ArrayList<Word> res = new ArrayList<>();
        // SQL query to select the explanation for the given word
        String sqlQuery = "SELECT * FROM engviet";
        preparedStatement = connection.prepareStatement(sqlQuery);

        // Execute the query
        resultSet = preparedStatement.executeQuery();

        // Check if a result is found
        while (resultSet.next()) {
            String target = resultSet.getString("word");
            String explain = toString(resultSet);
            res.add(new Word(target, explain));
        }

        return res;
    }

    public static void main(String[] args) throws FileNotFoundException {
        try {
            // Load the SQLite JDBC driver
            Class.forName("org.sqlite.JDBC");

            // Connect to the SQLite database
            connection = DriverManager.getConnection(jdbcURL);

            ArrayList<Word>  tmp1 = ins.getAllWords();
            for (Word word : tmp1) {
                System.out.println(word.getWordTarget());
            }
            Scanner cin = new Scanner(System.in);
            while (true) {
                String wordTarget = cin.next();
                System.out.println(ins.dictionaryLookup(wordTarget));
                ArrayList<Word> tmp = ins.dictionarySearcher(wordTarget);
                for (Word word : tmp) {
                    System.out.println("- " + word.getWordTarget());
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            // Close resources in the reverse order of their creation
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
