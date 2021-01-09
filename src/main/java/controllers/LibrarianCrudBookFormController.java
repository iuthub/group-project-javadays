package controllers;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Book;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

public class LibrarianCrudBookFormController {
    @FXML TextField bookId;
    @FXML TextField isbn;
    @FXML TextField title;
    @FXML TextField subject;
    @FXML TextField author;
    @FXML DatePicker publishDate;
    @FXML TextArea description;
    @FXML Button button;

    private Book book;
    private Connection connection;

    private String state;

    private final String create = new StringBuilder()
            .append("INSERT INTO Books(BookID, ISBN, Title, Subject, Author, PublishDate, Description)\n")
            .append("VALUES(?, ?, ?, ?, ?, ?, ?)")
            .toString();

    private String update = new StringBuilder()
            .append("UPDATE Books\n")
            .append("SET BookID=?, ISBN=?, Title=?, Subject=?, Author=?, PublishDate=?, Description=?\n")
            .append("WHERE BookID=%s")
            .toString();


    @FXML
    void handleButton(Event event) throws SQLException {
        PreparedStatement stmt;

        if (getState().equals("CREATE")) {
            stmt = connection.prepareStatement(create);
        } else if (getState().equals("UPDATE")) {
            stmt = connection.prepareStatement(update);
        } else {
            stmt = connection.prepareStatement("DESCRIBE Books");
            closeWindow();
        }

        changeBook();

        stmt.setInt(1, book.getBookID());
        stmt.setString(2, book.getISBN());
        stmt.setString(3, book.getTitle());
        stmt.setString(4, book.getSubject());
        stmt.setString(5, book.getAuthor());
        stmt.setDate(6, book.getPublishDate());
        stmt.setString(7, book.getDescription());

        stmt.executeUpdate();
        closeWindow();
    }

    private void closeWindow() {
        ((Stage) button.getScene().getWindow()).close();
    }

    void changeBook() {
        if (book == null) {
            book = new Book();
        }

        try {
            book.setBookID(Integer.parseInt(bookId.getText().trim()));
            book.setTitle(title.getText());
            book.setISBN(isbn.getText());
            book.setSubject(subject.getText());
            book.setAuthor(author.getText());
            book.setPublishDate(java.sql.Date.valueOf(publishDate.getValue()));
            book.setDescription(description.getText());
        } catch(NumberFormatException ignored) {}
    }

    void populateFieldsFromBook() {
        bookId.setText(String.valueOf(book.getBookID()));
        title.setText(book.getTitle());
        isbn.setText(book.getISBN());
        subject.setText(book.getSubject());
        author.setText(book.getAuthor());
        publishDate.setValue(book.getPublishDate().toLocalDate());
        description.setText(book.getDescription());
    }

    public void setBook(Book book) {
        this.book = book;

        if (getState().equals("CREATE")) {
            update = String.format(update, "?");
        } else if (getState().equals("UPDATE")) {
            update = String.format(update, this.book.getBookID());
            populateFieldsFromBook();
        } else {
            closeWindow();
        }
        button.setText(getState());
    }

    public Book getBook() { return book; }

    public void setConnection(Connection connection) { this.connection = connection; }

    public Connection getConnection() { return connection; }

    public String getState() { return state; }

    public void setState(String state) { this.state = state; }
}