package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import model.Book;

import java.sql.*;
import java.util.ArrayList;

public class BooksRepository {
    private static BooksRepository instance;

    private final PreparedStatement createStmt;
    private final PreparedStatement getAllStmt;
    private final PreparedStatement updateStmt;

    private BooksRepository() throws SQLException {
        String CREATE_QUERY = new StringBuilder()
                .append("INSERT INTO Books(BookID, ISBN, Title, Subject, Author, PublishDate)\n")
                .append("VALUES (?, '?', '?', '?', '?', '?')")
                .toString();
        String GET_ALL = new StringBuilder()
                .append("SELECT * FROM Books")
                .toString();
        String UPDATE_QUERY = new StringBuilder()
                .append("UPDATE Books\n")
                .append("SET BookID=?, ISBN='?', Title='?', Subject='?', Author='?', PublishDate='?'\n")
                .append("WHERE BookID=?")
                .toString();

        Connection conn = ConnectionManager.getConnection();

        this.createStmt = conn.prepareStatement(CREATE_QUERY);
        this.getAllStmt = conn.prepareStatement(GET_ALL);
        this.updateStmt = conn.prepareStatement(UPDATE_QUERY);
    }

    public static BooksRepository getInstance() throws SQLException {
        if (instance == null) {
            instance = new BooksRepository();
        }
        return instance;
    }

    public ObservableList<Book> getAll() throws SQLException {
        ResultSet rs = getAllStmt.executeQuery();
        ObservableList<Book> result = FXCollections.<Book>observableArrayList();

        while (rs.next()) {
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