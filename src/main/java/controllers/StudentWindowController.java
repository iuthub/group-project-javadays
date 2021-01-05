package controllers;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class StudentWindowController {
    public BorderPane mainBorderPane;
    public Button btnLogOut;

    public void handleStudentBookView(ActionEvent actionEvent) {
    }

    public void handleFineView(ActionEvent actionEvent) {
    }

    public void handleStudentHomeView(ActionEvent actionEvent) {
    }

    public void handleLogOut(ActionEvent actionEvent) throws IOException {
        HandleLogOut.logOut(getClass(), btnLogOut);
    }
}
