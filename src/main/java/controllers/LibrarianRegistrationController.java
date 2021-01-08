package controllers;

import dao.BooksRepository;
import dao.IssuedBook;
import dao.IssuedBookRepository;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Book;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LibrarianRegistrationController
{
    // region <Declaration>
    @FXML
    public TableView<Book> booksTable;
    @FXML
    public Pagination booksPagination;
    @FXML
    public ChoiceBox<String> filterChoiceBox;
    @FXML
    public TableView choosedBooksTable;
    @FXML
    public TextArea IdField;
    @FXML
    public DatePicker dueDatePicker;

    private final BooksRepository bookRepository;
    private final IssuedBookRepository issuedBookRepository;
    // endregion

    // region <StartUp>
    public LibrarianRegistrationController() throws SQLException
    {
        bookRepository = BooksRepository.getInstance();
        issuedBookRepository = IssuedBookRepository.getInstance();
    }

    public void initialize() throws SQLException
    {
        ObservableList<Book> books = bookRepository.getAll();
        booksTable.setItems(books);

        filterChoiceBox.getItems().add("Book Id");
        filterChoiceBox.getItems().add("ISBN");
        filterChoiceBox.getItems().add("Title");
        filterChoiceBox.getItems().add("Author");
        filterChoiceBox.getItems().add("Subject");
        filterChoiceBox.getItems().add("Publish date");
        filterChoiceBox.setValue("Book Id");

        booksTable.setRowFactory( tv -> {
            TableRow<Book> row = new TableRow<Book>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 1 && (! row.isEmpty()) ) {
                    Book selectedBook = row.getItem();
                    if(!choosedBooksTable.getItems().contains(selectedBook))
                        choosedBooksTable.getItems().add(selectedBook);
                }
            });
            return row ;
        });

        choosedBooksTable.setRowFactory( tv -> {
            TableRow<Book> row = new TableRow<Book>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 1 && (! row.isEmpty()) ) {
                    Book selectedBook = row.getItem();
                    choosedBooksTable.getItems().remove(selectedBook);
                }
            });
            return row ;
        });
    }
    // endregion

    // region <Event handlers>
    @FXML
    private void issueHandler() throws SQLException {
        ObservableList<Book> choosedBooks = choosedBooksTable.getItems();

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy"); //Setting date format

        for (Book choosedBook:choosedBooks)
        {
            LocalDate issuedDate = LocalDate.now();
            LocalDate returnDate = dueDatePicker.getValue();

            IssuedBook issuedBook = new IssuedBook(choosedBook.getBookID(),IdField.getText(), Date.valueOf(issuedDate), Date.valueOf(returnDate));

            issuedBookRepository.addIssuedBook(issuedBook);
        }

        IdField.setText(" ");
        choosedBooksTable.getItems().clear();
    }
    // endregion

}
