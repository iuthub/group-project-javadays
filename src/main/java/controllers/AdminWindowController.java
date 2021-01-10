package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
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
    }

    public void initialize() {
        handleHomeView();
    }

    public void handleHomeView() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/res/fxml/adminHomeView.fxml"));
        mainBorderPane.getChildren().remove(mainBorderPane.getCenter());
        try {
            mainBorderPane.setCenter(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        AdminHomeViewController controller = fxmlLoader.getController();
        controller.setAdminId(getCurrentUser().getUserID());

        try {
            controller.init();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void handleStudentView() {
        try {
            HandleChangeView.handleChangeScene(getClass(), mainBorderPane, "/res/fxml/adminStudentView.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleLibrarianView() {
       try {
            HandleChangeView.handleChangeScene(getClass(), mainBorderPane, "/res/fxml/adminLibrarianView.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleBookView() {
        try {
            HandleChangeView.handleChangeScene(getClass(), mainBorderPane, "/res/fxml/librarianEditBooksView.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleSettingsView() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/res/fxml/adminSettingsView.fxml"));
        mainBorderPane.getChildren().remove(mainBorderPane.getCenter());
        try {
            mainBorderPane.setCenter(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        AdminSettingsController controller = fxmlLoader.getController();
        controller.setAdminId(getCurrentUser().getUserID());
        controller.setPassword(getCurrentUser().getPassword());
    }

    public void handleLogOut() throws IOException {
        HandleLogOut.logOut(getClass(), btnLogOut);
    }
}
