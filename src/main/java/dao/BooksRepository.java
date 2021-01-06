package dao;

import java.sql.*;

public class BooksRepository {
    private static BooksRepository instance;

    private final PreparedStatement createStmt;

    private BooksRepository() throws SQLException {
        String CREATE_QUERY = new StringBuilder()
                .append("INSERT INTO Books(ISBN, Title, Subject, Author, PublishDate)\n")
                .append("VALUES ('?', '?', '?', '?')")
                .toString();

        Connection conn = ConnectionManager.getConnection();

        this.createStmt = conn.prepareStatement(CREATE_QUERY);
    }

    public static BooksRepository getInstance() throws SQLException {
        if (instance == null) {
            instance = new BooksRepository();
        }
        return instance;
    }
}