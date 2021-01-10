package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import model.Book;

public class LibrarianDeleteBookDialogController {
    @FXML Label bookId;
    @FXML Label isbn;
    @FXML Label title;
    @FXML Label subject;
    @FXML Label author;
    @FXML Label publishDate;

    private Book book;

    @FXML
    public void initialize() {
    }

    private void writeBookToDialog() {
        bookId.setText(String.valueOf(book.getBookID()));
        isbn.setText(book.getISBN());
        title.setText(book.getTitle());
        subject.setText(book.getTitle());
        author.setText(book.getAuthor());
        publishDate.setText(String.valueOf(book.getPublishDate()));
    }

    public Book getBook() { return book; }

    public void setBook(Book book) {
        this.book = book;

        if (this.book != null) {
            writeBookToDialog();
        }
    }
}
