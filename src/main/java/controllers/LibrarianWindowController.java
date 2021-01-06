package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class LibrarianWindowController {
    @FXML public AnchorPane mainAnchorPane;
    @FXML public VBox navbar;
    @FXML public GridPane editBooksGridPane;
    @FXML public GridPane studentsGridPane;
    @FXML Button btnLogOut;

    public void handleHome(ActionEvent actionEvent) {
        studentsGridPane.setVisible(false);
        editBooksGridPane.setVisible(false);
    }

    public void handleEditBooks(ActionEvent actionEvent) {
        studentsGridPane.setVisible(false);
        editBooksGridPane.setVisible(true);
    }

    public void handleStudents(ActionEvent actionEvent) {
        editBooksGridPane.setVisible(false);
        studentsGridPane.setVisible(true);
    }

    public void handleLogOut(ActionEvent actionEvent) throws IOException {
        HandleLogOut.logOut(getClass(), btnLogOut);
    }
}
