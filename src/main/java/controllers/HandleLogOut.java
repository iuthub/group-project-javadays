package controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class HandleLogOut {
    public static void logOut(Class currentClass, Button btnSubmit) throws IOException {
        Stage mainAppStage;
        Parent root = null;
        root = FXMLLoader.load(currentClass.getResource("/res/fxml/loginDialog.fxml"));

        mainAppStage = (Stage) btnSubmit.getScene().getWindow();
        mainAppStage.setResizable(false);

        Scene mainAppScene = new Scene(root, 1280, 720);
        mainAppScene.getStylesheets().add(currentClass.getResource("/res/css/style.css").toExternalForm());
        mainAppStage.setScene(mainAppScene);
        mainAppStage.show();
    }
}
