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
        choiceBoxSearchType.getItems().add("User ID");
        choiceBoxSearchType.getItems().add("First Name");
        choiceBoxSearchType.getItems().add("Last Name");
        choiceBoxSearchType.setValue("User ID");

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
    void search(Event event) {
        try {
            var searched = UsersRepository.getInstance().searchStudentsWithOverdue((String) choiceBoxSearchType.getValue(), searchField.getText());
            tblStudentsWithOverdue.setItems(searched);
            tblStudentsWithOverdue.refresh();
        } catch(SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    @FXML
    void refresh(Event event) throws SQLException {
        populateTable();
        tblStudentsWithOverdue.refresh();
    }
}
