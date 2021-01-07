package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Book;
import java.sql.*;

public class BooksRepository {
    private static BooksRepository instance;

    private final PreparedStatement createStmt;
    private final PreparedStatement getAllStmt;
    private final PreparedStatement updateStmt;
    private final PreparedStatement getById;

    private BooksRepository() throws SQLException {
        String GET_ALL = "SELECT * FROM Books";
        String GET_BY_ID = "SELECT * FROM Books WHERE bookID= ?";
        String CREATE_QUERY = "INSERT INTO Books(BookID, ISBN, Title, Subject, Author, PublishDate) VALUES (?, '?', '?', '?', '?', '?')";
        String UPDATE_QUERY = "UPDATE Books SET BookID = ?, ISBN = ?, Title = ?, Subject = ? , Author = ?, PublishDate = ? WHERE BookID = ?";

        Connection conn = ConnectionManager.getConnection();
        this.createStmt = conn.prepareStatement(CREATE_QUERY);
        this.getAllStmt = conn.prepareStatement(GET_ALL);
        this.updateStmt = conn.prepareStatement(UPDATE_QUERY);
        this.getById    = conn.prepareStatement(GET_BY_ID);
    }

    public static BooksRepository getInstance() throws SQLException {
        if (instance == null) {
            instance = new BooksRepository();
        }
        return instance;
    }

    public ObservableList<Book> getAll() throws SQLException {
        ResultSet rs = getAllStmt.executeQuery();
        ObservableList<Book> results = FXCollections.observableArrayList();

        while (rs.next()) {
            results.add(new Book(
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
        return results;
    }

    public Book getById(int bookId) throws SQLException {
        Book selectedBook = null;

        getById.setInt(1, bookId);
        ResultSet result = getById.executeQuery();

        if(result.next()) {
            selectedBook = new Book(
                    result.getInt("BookId"),
                    result.getString("ISBN"),
                    result.getString("Title"),
                    result.getString("Subject"),
                    result.getString("Author"),
                    result.getDate("PublishDate"),
                    result.getString("Description")
            );
        }

        return selectedBook;
    }

    public void createBook(int bookId, String isbn, String title, String subject, String author, Date date) throws SQLException{
        createStmt.setInt   (1, bookId);
        createStmt.setString(2, isbn);
        createStmt.setString(3, title);
        createStmt.setString(4, subject);
        createStmt.setString(5, author);
        createStmt.setDate  (6, date);

        createStmt.executeUpdate();
    }

    public void updateBook(int oldBookId, int newBookId, String isbn, String title, String subject, String author, Date date) throws SQLException{
        updateStmt.setInt   (1, newBookId);
        updateStmt.setString(2, isbn);
        updateStmt.setString(3, title);
        updateStmt.setString(4, subject);
        updateStmt.setString(5, author);
        updateStmt.setDate  (6, date);
        updateStmt.setInt   (7, oldBookId);

        updateStmt.executeUpdate();
    }
}