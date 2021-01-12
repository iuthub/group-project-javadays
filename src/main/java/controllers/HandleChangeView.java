package controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class HandleChangeView {
    public static void handleChangeScene(Class currentClass, BorderPane mainBorderPane, String filename) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(currentClass.getResource(filename));
        mainBorderPane.getChildren().remove(mainBorderPane.getCenter());
        try {
            mainBorderPane.setCenter(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
