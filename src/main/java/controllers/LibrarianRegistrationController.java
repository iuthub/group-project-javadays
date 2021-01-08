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
    public TableView<Book> choosedBooksTable;
    @FXML
    public TextArea IdField;
    @FXML
    public DatePicker dueDatePicker;
    @FXML
    public Label resultLabel;

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

        if(IdField.getText()==null){
            resultLabel.setText("Operation canceled. Please set user ID");
            return;
        }
        else if(dueDatePicker.getValue()==null){
            resultLabel.setText("Operation canceled. Please set due date");
            return;
        }
        else if(choosedBooksTable.getItems().stream().count()==0) {
            resultLabel.setText("Operation canceled. You didn't choose books");
            return;
        }

        for (Book choosedBook:choosedBooks)
        {
            LocalDate issuedDate = LocalDate.now();
            LocalDate returnDate = dueDatePicker.getValue();
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy"); //Setting date format

            IssuedBook issuedBook = new IssuedBook(choosedBook.getBookID(), IdField.getText(), Date.valueOf(issuedDate), Date.valueOf(returnDate));

            try {
                issuedBookRepository.addIssuedBook(issuedBook);
            }
            catch (SQLException e){
                resultLabel.setText("This user already taken this book! Operation canceled");
                return;
            }
        }

        resultLabel.setText("Book(s) were successfully registered by student");

        IdField.setText(null);
        choosedBooksTable.getItems().clear();
        dueDatePicker.setValue(null);
    }
    // endregion

}
