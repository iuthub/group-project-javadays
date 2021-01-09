package controllers;

import dao.BooksRepository;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import model.BookStudentView;
import java.sql.SQLException;


public class StudentBooksController {

    public AnchorPane studentMainAnchorPane;
    public Button btnReserve;
    public Label labelDescription;

    @FXML
    private TableView<BookStudentView> studentTblBooksTable;
    private ObservableList<BookStudentView> Books;

    @FXML
    public void initialize() throws SQLException {
        populateStudentBookView();
    }

    public void getDescription(String ISBN) throws SQLException {
        labelDescription.setText(BooksRepository.getInstance().getDescriptionByISBN(ISBN));
    }

    public void populateStudentBookView() throws SQLException {
        Books = BooksRepository.getInstance().getBookWithBorrowedSt();
        studentTblBooksTable.setItems(Books);
          controlClick();
    }

    public void controlClick() {
        studentTblBooksTable.setRowFactory(tv -> {
            TableRow<BookStudentView> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 1 && (!row.isEmpty())) {
                    BookStudentView chooseBook = row.getItem();
                    try {
                        getDescription(chooseBook.getISBN());
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                    if(chooseBook.getBorrowedStatus().equals("Unavailable"))
                    {
                        btnReserve.setDisable(false);
                        // reserveBook(Student student);
                        // reserve option is not ready
                    }
                }
            });
            return row;
        });
    }



}
