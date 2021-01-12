package controllers;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Book;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
        if (checkIfAnyFieldIsEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("One of the fields is blank.");
            alert.showAndWait();
            return;
        }

        if (!isbnIsValid(isbn.getText())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid ISBN inputted.");
            alert.showAndWait();
            return;
        }

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

        try {
            stmt.executeUpdate();
        } catch (SQLException sqlException) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(String.format("Book with ID = %d already exists.", book.getBookID()));
            alert.showAndWait();
            return;
        }
        closeWindow();
    }

    private boolean checkIfAnyFieldIsEmpty() {
        return bookId.getText() == null || bookId.getText().trim().isEmpty() ||
                isbn.getText() == null || isbn.getText().trim().isEmpty() ||
                title.getText() == null || title.getText().trim().isEmpty() ||
                subject.getText() == null || subject.getText().trim().isEmpty() ||
                author.getText() == null || author.getText().trim().isEmpty() ||
                publishDate.getValue() == null ||
                description.getText() == null || description.getText().trim().isEmpty();
    }

    private boolean isbnIsValid(String isbn) {
        int n = isbn.length();
        if (n != 10) {
            return false;
        }

        int sum = 0;
        for (int i = 0; i < 9; i++) {
            int digit = isbn.charAt(i) - '0';
            if (0 > digit || 9 < digit) {
                return false;
            }
            sum += (digit * (10 - i));
        }

        char last = isbn.charAt(9);
        if (last != 'X' && (last < '0' || last > '9')) {
            return false;
        }

        sum += ((last == 'X') ? 10 : (last - '0'));

        return (sum % 11 == 0);
    }

    @FXML
    void handleCancel(Event event) {
        this.closeWindow();
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
