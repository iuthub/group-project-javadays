package controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import model.Book;
import dao.BooksRepository;
import dao.ConnectionManager;

public class LibrarianEditBooksController {
    @FXML public AnchorPane mainAnchorPane;
    @FXML public TableView<Book> tblBooksTable;
    @FXML public BooksRepository booksRepository;
    @FXML public ChoiceBox<String> choiceBoxSearchType;
    @FXML public TextField searchField;

    private ObservableList<Book> tableData;
    private Book selectedBook;

    public LibrarianEditBooksController() throws SQLException {
        booksRepository = BooksRepository.getInstance();
    }

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

        // populate choice box
        choiceBoxSearchType.getItems().add("BookID");
        choiceBoxSearchType.getItems().add("ISBN");
        choiceBoxSearchType.getItems().add("Title");
        choiceBoxSearchType.getItems().add("Author");
        choiceBoxSearchType.getItems().add("Subject");
        choiceBoxSearchType.setValue("BookID");
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
    void handleDeleteButton(Event event) throws SQLException {
        if (selectedBook != null) {
            try {
                var dialog = new Dialog<ButtonType>();
                dialog.initOwner(tblBooksTable.getScene().getWindow());
                dialog.setTitle("Delete Book");
                dialog.setResizable(true);

                var fxmlLoader = new FXMLLoader(getClass().getResource("/res/fxml/librarianDeleteBookDialog.fxml"));

                try {
                    dialog.getDialogPane().setContent(fxmlLoader.load());

                    var controller = fxmlLoader.<LibrarianDeleteBookDialogController>getController();
                    controller.setBook(selectedBook);
                } catch (IOException ioException) {
                    System.err.println(ioException.getMessage());
                }

                dialog.getDialogPane().getButtonTypes().add(ButtonType.YES);
                dialog.getDialogPane().getButtonTypes().add(ButtonType.NO);

                Button okBtn = (Button) dialog.getDialogPane().lookupButton(dialog.getDialogPane().getButtonTypes().get(0));
                Button cancelBtn = (Button) dialog.getDialogPane().lookupButton(dialog.getDialogPane().getButtonTypes().get(1));
                okBtn.getStyleClass().add("delete-button");
                cancelBtn.getStyleClass().add("secondary-button");


                Optional<ButtonType> result = dialog.showAndWait();

                if (result.isPresent() && result.get() == ButtonType.YES) {
                    booksRepository.deleteBook(selectedBook.getBookID());
                } else if (result.isPresent() && result.get() == ButtonType.NO) {
                    dialog.close();
                }
            } catch(SQLException sqlException) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Deleting Book");
                alert.setHeaderText(String.format("Error while deleting the selected book with Book ID: %d", selectedBook.getBookID()));
                alert.setContentText("This might occur because the book is currently on loan.");
                alert.showAndWait();
            }
        }
        selectedBook = null;
        populateTable();

        // by default, the first row is selected
        if (tableData.size() > 0) {
            tblBooksTable.getSelectionModel().select(0);
        }
    }

    @FXML
    void search(Event event) {
        try {
            var searched = booksRepository.search((String) choiceBoxSearchType.getValue(), searchField.getText());
            tblBooksTable.setItems(searched);
            tblBooksTable.refresh();
        } catch(SQLException sqlException) {
            System.err.println(sqlException.getMessage());
        }
    }

    @FXML
    void showAllBooks(Event event) throws SQLException {
        populateTable();
    }

    void populateTable() throws SQLException  {
        tableData = BooksRepository.getInstance().getAll();
        tblBooksTable.setItems(tableData);
        tblBooksTable.refresh();
    }
}
