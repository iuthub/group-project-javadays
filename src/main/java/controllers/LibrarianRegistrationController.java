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
    public TableView<Book> choosedBooksTable;
    @FXML
    public TextField IdField;
    @FXML
    public DatePicker dueDatePicker;
    @FXML
    public Button issueButton;

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

        Dialog<ButtonType> dialog = new Dialog<>();

        InformationDialogController informationDialogController = InformationDialogController.getDialog(getClass(),dialog,issueButton,"");

        if(IdField.getText()==""){
            informationDialogController.setLabel("Operation canceled. Please set user ID");
            dialog.showAndWait();
            return;
        }
        else if(dueDatePicker.getValue()==null){
            informationDialogController.setLabel("Operation canceled. Please set due date");
            dialog.showAndWait();
            return;
        }
        else if(choosedBooksTable.getItems().stream().count()==0) {
            informationDialogController.setLabel("Operation canceled. Please choose books");
            dialog.showAndWait();
            return;
        }

        User user = usersRepository.get(IdField.getText());

        if(user==null)
        {
            informationDialogController.setLabel("Entered ID is not valid. Valid pattern:U1234567");
            dialog.showAndWait();
            return;
        }
        else if(user.getRole().getValue()!=2)
        {
            informationDialogController.setLabel("Entered ID is not student's ID please enter another ID");
            dialog.showAndWait();
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
                informationDialogController.setLabel("This user already taken this books! Operation canceled");
                dialog.showAndWait();
                return;
            }
        }

        informationDialogController.setLabel("Book(s) were successfully registered by student");
        dialog.showAndWait();

        IdField.setText("");
        choosedBooksTable.getItems().clear();
        dueDatePicker.setValue(null);
    }
    // endregion

}
