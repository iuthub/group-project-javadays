package controllers;

import dao.BooksRepository;
import dao.IssuedBook;
import dao.IssuedBookRepository;
import dao.StudentRepository;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import model.Book;
import model.User;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

public class LibrarianJournalController
{
    @FXML public Button btnReturn;
    //region <Declarations>
    @FXML private TableView<User> studentsTableView;
    @FXML private TableView<Book> chosenBooksTable;

    private final StudentRepository studentRepository;
    private final IssuedBookRepository issuedBookRepository;
    private final BooksRepository booksRepository;
    //endregion

    //region <StartUp>
    public LibrarianJournalController() throws SQLException {
        studentRepository = StudentRepository.getInstance();
        issuedBookRepository = IssuedBookRepository.getInstance();
        booksRepository = BooksRepository.getInstance();
    }

    public void initialize() throws SQLException {
        studentsTableView.setItems(studentRepository.getStudentsWithBooks());
        
        studentsTableView.setRowFactory( tv ->
        {
            TableRow<User> row = new TableRow<User>();
            row.setOnMouseClicked(event ->
            {
                if(chosenBooksTable.getItems().stream().count()!=0)
                    chosenBooksTable.getItems().clear();
                if (event.getClickCount() == 1 && (! row.isEmpty()) ) {
                    User student = row.getItem();
                    try
                    {
                        ObservableList<IssuedBook> issuedBooks = issuedBookRepository.getByUser(student.getUserId());

                        for (IssuedBook issuedBook:issuedBooks)
                        {
                            var a = booksRepository.getById(issuedBook.getIssueBookId());
                            Book book = booksRepository.getById(issuedBook.getIssueBookId());
                            chosenBooksTable.getItems().add(book);
                        }
                    }
                    catch (SQLException throwables)
                    {
                        throwables.printStackTrace();
                    }
                }
            });
            return row ;
        });
    }
    //endregion


    //region <Event Handlers>
    @FXML
    private void refreshHandle() throws SQLException {
        studentsTableView.setItems(studentRepository.getStudentsWithBooks());
    }

    @FXML
    private void returnIssuedBook() throws SQLException {

        Dialog<ButtonType> dialog = new Dialog<>();

        InformationDialogController informationDialogController = InformationDialogController.getDialog(getClass(),dialog, btnReturn, "", "/res/fxml/informationDialog.fxml");

        User selectedStudent = studentsTableView.getSelectionModel().getSelectedItem();
        Book selectedBook = chosenBooksTable.getSelectionModel().getSelectedItem();


        int fine = IssuedBookRepository.getInstance().calculateFine(selectedBook.getBookID(), selectedStudent.getUserId());

        if (fine == 0) {
            informationDialogController.setLabel("Successfully returned");
        } else {
            informationDialogController.setLabel("Issued fine: " + fine + "$");
        }

        dialog.showAndWait();

        issuedBookRepository.removeIssuedBook(selectedBook.getBookID(),selectedStudent.getUserId());

        chosenBooksTable.getItems().remove(selectedBook);
    }
    //endregion

}
