package controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginStudentStrategy implements LoginStrategy {
    @Override
    public void authenticate(Button btnSubmit) {
        Stage mainAppStage;
        Parent root = null;

        // Changing Stage to the main App
        mainAppStage = (Stage) btnSubmit.getScene().getWindow();
        mainAppStage.setResizable(false);

        try {
            root = FXMLLoader.load(getClass().getResource("/res/fxml/studentWindow.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Scene mainAppScene = new Scene(root, 1280, 720);
        mainAppScene.getStylesheets().add(getClass().getResource("/res/css/style.css").toExternalForm());
        mainAppStage.setScene(mainAppScene);
        mainAppStage.show();
    }
}
