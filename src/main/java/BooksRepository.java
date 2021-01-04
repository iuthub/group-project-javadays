package main.java;

import java.sql.*;

public class BooksRepository {
    private static BooksRepository instance;
    private final Connection connection;

    private final PreparedStatement createStmt;

    private BooksRepository() throws SQLException {
        String createBookQuery = "INSERT INTO Books(isbn, title, subject, author, publishDate, description) " +
                                  "VALUES ('?', '?', '?', '?', '?')";

        this.connection = DriverManager.getConnection("jdbc:derby:./db/books");

        this.createStmt = connection.prepareStatement(createBookQuery);
    }

    public static BooksRepository getInstance() throws SQLException {
        if (instance == null) {
            instance = new BooksRepository();
        }
        return instance;
    }
}
