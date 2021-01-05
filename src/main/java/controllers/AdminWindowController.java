package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import model.User;

import java.io.IOException;

public class AdminWindowController {
    private static User currentUser;

    @FXML
    public BorderPane mainBorderPane;

    @FXML
    public Button btnLogOut;

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User currentUser) {
        AdminWindowController.currentUser = currentUser;
    }

    public void handleStudentView(ActionEvent actionEvent) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/main/res/fxml/studentView.fxml"));
        mainBorderPane.getChildren().remove(mainBorderPane.getCenter());
        try {
            mainBorderPane.setCenter(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleLibrarianView(ActionEvent actionEvent) {
    }

    public void handleBookView(ActionEvent actionEvent) {
    }

    public void handleLogOut(ActionEvent actionEvent) throws IOException {
        HandleLogOut.logOut(getClass(), btnLogOut);
    }
}
