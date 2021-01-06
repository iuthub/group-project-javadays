package controllers;

import dao.BooksRepository;
import javafx.fxml.FXML;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.sql.SQLException;

public class LibrarianEditBooksController {
    @FXML public AnchorPane mainAnchorPane;
    @FXML public TabPane booksTabPane;
    @FXML public TableView tblBooksTable;
    @FXML public VBox mainVBox;

    @FXML
    void initialize() {
            tblBooksTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    void populateTable() throws SQLException  {
        var allBooks = BooksRepository.getInstance().getAll();
    }
}
