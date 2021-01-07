package controllers;

import java.io.IOException;
import java.sql.SQLException;

import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Pagination;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import model.Book;
import dao.BooksRepository;
import dao.ConnectionManager;

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
    void handleCreateButton(Event event) throws SQLException {
        openWindow("Create Book", null, "CREATE");
    }

    @FXML
    void handleUpdateButton(Event e) throws SQLException {
        openWindow("Update Book", selectedBook, "UPDATE");
    }

    void openWindow(String title, Book book, String state) throws SQLException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/res/fxml/librarianCrudBookForm.fxml"));

            Parent root = loader.load();

            var crudBookController = loader.<LibrarianCrudBookFormController>getController();
            crudBookController.setState(state);
            crudBookController.setBook(book);
            crudBookController.setConnection(ConnectionManager.getConnection());

            Stage newStage = new Stage();
            newStage.setTitle(title);
            newStage.setScene(new Scene(root, 450, 450));
            newStage.setUserData(ConnectionManager.getConnection());
            newStage.showAndWait();

            populateTable();
        } catch(IOException ioe) {
            System.err.println("Could not load view.");
        }
    }

    @FXML
    void handleDeleteButton(Event e) throws SQLException {
        var query = String.format("DELETE FROM Books WHERE BookID=%d", selectedBook.getBookID());
        ConnectionManager.getConnection().prepareStatement(query).executeUpdate();
        populateTable();
        selectedBook = null;
    }

    void populateTable() throws SQLException  {
        tableData = BooksRepository.getInstance().getAll();
        tblBooksTable.setItems(tableData);
        tblBooksTable.refresh();
    }
}
