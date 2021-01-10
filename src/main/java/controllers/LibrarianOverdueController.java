package controllers;

import dao.IssuedBook;
import dao.IssuedBookRepository;
import dao.UsersRepository;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import model.User;

import java.sql.SQLException;

public class LibrarianOverdueController {
    @FXML TableView tblStudentsWithOverdue;
    @FXML ChoiceBox choiceBoxSearchType;
    @FXML TextField searchField;

    @FXML
    void initialize() {
        choiceBoxSearchType.getItems().add("UserID");
        choiceBoxSearchType.getItems().add("FirstName");
        choiceBoxSearchType.getItems().add("LastName");
        choiceBoxSearchType.setValue("UserID");

        try {
            populateTable();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    private void populateTable() throws SQLException  {
        tblStudentsWithOverdue.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        tblStudentsWithOverdue.setItems(UsersRepository.getInstance().getAllStudentsWithOverdue());
        tblStudentsWithOverdue.refresh();
    }

    @FXML
    void search(Event event) {}

    @FXML
    void refresh(Event event) {}
}
