package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import model.Book;

import java.sql.*;
import java.util.ArrayList;

public class BooksRepository {
    private static BooksRepository instance;
    private Connection connection;

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

        connection = ConnectionManager.getConnection();

        this.createStmt = connection.prepareStatement(CREATE_QUERY);
        this.getAllStmt = connection.prepareStatement(GET_ALL);
        this.updateStmt = connection.prepareStatement(UPDATE_QUERY);
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
                            rs.getDate("PublishDate"),
                            rs.getString("Description")
                    )
            );
        }
        return result;
    }


    public Book getById(int bookId) throws SQLException
    {
        ResultSet result;
        Book selectedBook = new Book();

        String selectBook = String.format("Select * From Books Where bookId=%d",bookId);

        try
        {
            PreparedStatement selectBookStat = connection.prepareStatement(selectBook);
            result = selectBookStat.executeQuery();

            if(result.next())
                selectedBook = new Book(result.getInt("BookId"),
                        result.getString("ISBN"),
                        result.getString("Title"),
                        result.getString("Subject"),
                        result.getString("Author"),
                        result.getDate("PublishDate"),
                        ""
                        );
        }
        catch (SQLException exception)
        {
            exception.fillInStackTrace();
        }

        return selectedBook;
    }
}