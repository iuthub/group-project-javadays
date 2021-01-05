package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class LibrarianWindowController {
    @FXML
    public BorderPane mainBorderPane;
    @FXML
    Button btnLogOut;

    public void handleReturnBooks(ActionEvent actionEvent) {
    }

    public void handleIssueBooks(ActionEvent actionEvent) {
    }

    public void handleLogOut(ActionEvent actionEvent) throws IOException {
        HandleLogOut.logOut(getClass(), btnLogOut);
    }
}
