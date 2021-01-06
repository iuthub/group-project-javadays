package controllers;

import dao.BooksRepository;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import model.Book;
import model.User;

import java.sql.SQLException;

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
    public TableView choosedBooksTable;

    private final BooksRepository repository;
    // endregion

    // region <StartUp>
    public LibrarianRegistrationController() throws SQLException
    {
        repository = BooksRepository.getInstance();
    }

    public void initialize() throws SQLException
    {
        ObservableList<Book> books = repository.getAll();
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
    private void issueHandler()
    {

    }
    // endregion

}
