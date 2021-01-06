package controllers;

import dao.BooksRepository;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Book;

import java.io.IOError;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LibrarianEditBooksController {
    @FXML public AnchorPane mainAnchorPane;
    @FXML public TabPane booksTabPane;
    @FXML public TableView<Book> tblBooksTable;
    @FXML public VBox mainVBox;
    @FXML public Pagination pagination;

    private ObservableList<Book> tableData;
    public static Book selectedBook;

    void handleTableItemSelection(Book book) {
        if (book != null) {
            selectedBook = book;
        }
    }

    @FXML
    void initialize() throws SQLException {
        tblBooksTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        populateTable();

        // handles row selection on the table
        tblBooksTable.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldSelection, newSelection) -> handleTableItemSelection(newSelection)
        );

        // by default, the first row is selected
        if (tableData.size() > 0) {
            tblBooksTable.getSelectionModel().select(0);
        }
    }

    @FXML
    void handleAddButton(Event event) {
        Label secondLabel = new Label("I'm a Label on new Window");

        StackPane secondaryLayout = new StackPane();
        secondaryLayout.getChildren().add(secondLabel);

        Scene secondScene = new Scene(secondaryLayout, 230, 100);

        // New window (Stage)
        Stage newWindow = new Stage();
        newWindow.setTitle("Second Stage");
        newWindow.setScene(secondScene);

        // Set position of second window, related to primary window.
        newWindow.setX(500);
        newWindow.setY(500);

        newWindow.show();
    }

    @FXML
    void handleEditButton(Event e) {
    }

    @FXML
    void handleDeleteButton(Event e) {
    }

    void populateTable() throws SQLException  {
        tableData = BooksRepository.getInstance().getAll();
        tblBooksTable.setItems(tableData);
    }
}
