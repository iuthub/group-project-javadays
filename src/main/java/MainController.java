package main.java;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class MainController {
    private static User currentUser;

    @FXML
    public BorderPane mainBorderPane;

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User currentUser) {
        MainController.currentUser = currentUser;
    }

    public void handleStudentView(ActionEvent actionEvent) {
//        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/main/res/fxml/studentView.fxml"));
//        mainBorderPane.getChildren().remove(mainBorderPane.getCenter());
//        try {
//            mainBorderPane.setCenter(fxmlLoader.load());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    public void handleLibrarianView(ActionEvent actionEvent) {
    }

    public void handleBookView(ActionEvent actionEvent) {
    }
}
