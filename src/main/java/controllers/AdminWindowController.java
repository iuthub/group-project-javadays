package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import model.User;

import java.io.IOException;
import java.sql.SQLException;

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
        System.out.println(currentUser.getFirstName());
    }

    public void handleHomeView(ActionEvent actionEvent) {

        try {
            HandleChangeView.handleChangeScene(getClass(), mainBorderPane, "/res/fxml/adminStudentView.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void handleStudentView(ActionEvent actionEvent) {

        try {
            HandleChangeView.handleChangeScene(getClass(), mainBorderPane, "/res/fxml/adminStudentView.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }

        AdminHomeViewController controller = fxmlLoader.getController();
        controller.setAdminId("U1410000");
        controller.init();

    }

    public void handleLibrarianView(ActionEvent actionEvent) {
       try {
            HandleChangeView.handleChangeScene(getClass(), mainBorderPane, "/res/fxml/adminLibrarianView.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleBookView(ActionEvent actionEvent) {
        try {
            HandleChangeView.handleChangeScene(getClass(), mainBorderPane, "/res/fxml/adminLibrarianView.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleLogOut(ActionEvent actionEvent) throws IOException {
        HandleLogOut.logOut(getClass(), btnLogOut);
    }
}
