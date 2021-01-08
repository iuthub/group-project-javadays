package controllers;

import dao.BooksRepository;
import dao.IssuedBook;
import dao.IssuedBookRepository;
import dao.StudentRepository;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import model.Book;
import model.User;

import java.sql.SQLException;

public class LibrarianJournalController
{
    //region <Declarations>
    @FXML
    public TableView<User> studentsTableView;
    @FXML
    public TableView<Book> choosedBooksTable;

    private StudentRepository studentRepository;
    private IssuedBookRepository issuedBookRepository;
    private BooksRepository booksRepository;
    //endregion

    //region <StartUp>
    public LibrarianJournalController() throws SQLException
    {
        studentRepository = StudentRepository.getInstance();
        issuedBookRepository = IssuedBookRepository.getInstance();
        booksRepository = BooksRepository.getInstance();
    }

    public void initialize() throws SQLException
    {
        studentsTableView.setItems(studentRepository.getStudentsWithBooks());
        
        studentsTableView.setRowFactory( tv ->
        {
            TableRow<User> row = new TableRow<User>();
            row.setOnMouseClicked(event ->
            {
                if(choosedBooksTable.getItems().stream().count()!=0)
                    choosedBooksTable.getItems().clear();
                if (event.getClickCount() == 1 && (! row.isEmpty()) ) {
                    User student = row.getItem();
                    try
                    {
                        ObservableList<IssuedBook> issuedBooks = issuedBookRepository.getByUser(student.getUserId());

                        for (IssuedBook issuedBook:issuedBooks)
                        {
                            var a = booksRepository.getById(issuedBook.getIssueBookId());
                            Book book = booksRepository.getById(issuedBook.getIssueBookId());
                            choosedBooksTable.getItems().add(book);
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
    private void refreshHandle() throws SQLException
    {
        studentsTableView.setItems(studentRepository.getStudentsWithBooks());
    }

    @FXML
    private void returnIssuedBook()throws SQLException
    {
        User selectedStudent = studentsTableView.getSelectionModel().getSelectedItem();
        Book selectedBook = choosedBooksTable.getSelectionModel().getSelectedItem();

        issuedBookRepository.removeIssuedBook(selectedBook.getBookID(),selectedStudent.getUserId());

        choosedBooksTable.getItems().remove(selectedBook);
    }
    //endregion

}
