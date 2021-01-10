package controllers;

import dao.*;
import dao.IssuedBook;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Book;
import model.User;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
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
    public TextField IdField;
    @FXML
    public DatePicker dueDatePicker;
    @FXML
    public Label resultLabel;

    private final BooksRepository bookRepository;
    private final IssuedBookRepository issuedBookRepository;
    private final UsersRepository usersRepository;
    // endregion

    // region <StartUp>
    public LibrarianRegistrationController() throws SQLException
    {
        bookRepository = BooksRepository.getInstance();
        issuedBookRepository = IssuedBookRepository.getInstance();
        usersRepository = UsersRepository.getInstance();
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
    private void issueHandler() throws SQLException, IOException {
        ObservableList<Book> choosedBooks = choosedBooksTable.getItems();

        //https://code.makery.ch/blog/javafx-dialogs-official/
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setTitle("Validation error");
        errorAlert.setHeaderText("Operation canceled");

        if(IdField.getText()==""){
            errorAlert.setContentText("Please set user ID");
            errorAlert.showAndWait();
            return;
        }
        else if(dueDatePicker.getValue()==null){
            errorAlert.setContentText("Please set due date");
            errorAlert.showAndWait();
            return;
        }
        else if(choosedBooksTable.getItems().stream().count()==0) {
            errorAlert.setContentText("You didn't choose books");
            errorAlert.showAndWait();
            return;
        }

        User user = usersRepository.get(IdField.getText());

        if(user.getRole().getValue()!=2)
        {
            errorAlert.setContentText("Entered ID is not student's ID please enter another ID");
            errorAlert.showAndWait();
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
                errorAlert.setContentText("This user already taken this books! Operation canceled");
                errorAlert.showAndWait();
                return;
            }
        }

        Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
        successAlert.setTitle("Success");
        successAlert.setHeaderText("Operation completed.");

        successAlert.setContentText("Book(s) were successfully registered by student");
        successAlert.showAndWait();

        IdField.setText("");
        choosedBooksTable.getItems().clear();
        dueDatePicker.setValue(null);
    }
    // endregion

}
