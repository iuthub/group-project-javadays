package dao;

import model.Book;

import java.sql.*;
import java.util.ArrayList;

public class BooksRepository {
    private static BooksRepository instance;

    private final PreparedStatement createStmt;
    private final PreparedStatement getAllStmt;

    private BooksRepository() throws SQLException {
        String CREATE_QUERY = new StringBuilder()
                .append("INSERT INTO Books(ISBN, Title, Subject, Author, PublishDate)\n")
                .append("VALUES ('?', '?', '?', '?')")
                .toString();
        String GET_ALL = new StringBuilder()
                .append("SELECT * FROM Books")
                .toString();

        Connection conn = ConnectionManager.getConnection();

        this.createStmt = conn.prepareStatement(CREATE_QUERY);
        this.getAllStmt = conn.prepareStatement(GET_ALL);
    }

    public static BooksRepository getInstance() throws SQLException {
        if (instance == null) {
            instance = new BooksRepository();
        }
        return instance;
    }

    public ArrayList<Book> getAll() throws SQLException {
        ResultSet rs = getAllStmt.executeQuery();
        var result = new ArrayList<Book>();

        while (!rs.next()) {
            result.add(new Book(
                            rs.getInt("BookID"),
                            rs.getString("ISBN"),
                            rs.getString("Title"),
                            rs.getString("Subject"),
                            rs.getString("Author"),
                            rs.getDate("PublishDate")
                    )
            );
        }
        return result;
    }
}