package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class LibrarianWindowController {
    @FXML public AnchorPane mainAnchorPane;
    @FXML public VBox navbar;

    @FXML public GridPane editBooksGridPane;
    @FXML public GridPane studentsGridPane;
    @FXML public GridPane registrationPanelGridPane;
    @FXML public GridPane journalGridPane;

    @FXML Button btnLogOut;

    public void handleHome(ActionEvent actionEvent) {
        studentsGridPane.setVisible(false);
        editBooksGridPane.setVisible(false);
        registrationPanelGridPane.setVisible(false);
        journalGridPane.setVisible(false);
    }

    public void handleRegistrationPanel(ActionEvent actionEvent) {
        studentsGridPane.setVisible(false);
        editBooksGridPane.setVisible(false);
        registrationPanelGridPane.setVisible(true);
        journalGridPane.setVisible(false);
    }

    public void handleJournal(ActionEvent actionEvent) {
        studentsGridPane.setVisible(false);
        editBooksGridPane.setVisible(false);
        registrationPanelGridPane.setVisible(false);
        journalGridPane.setVisible(true);
    }

    public void handleEditBooks(ActionEvent actionEvent) {
        studentsGridPane.setVisible(false);
        editBooksGridPane.setVisible(true);
        registrationPanelGridPane.setVisible(false);
        journalGridPane.setVisible(false);
    }

    public void handleStudents(ActionEvent actionEvent) {
        studentsGridPane.setVisible(true);
        editBooksGridPane.setVisible(false);
        registrationPanelGridPane.setVisible(false);
        journalGridPane.setVisible(false);
    }

    public void handleLogOut(ActionEvent actionEvent) throws IOException {
        HandleLogOut.logOut(getClass(), btnLogOut);
    }
}
