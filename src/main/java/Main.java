package main.java;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
//        Parent root = FXMLLoader.load(getClass().getResource("/main/res/fxml/loginDialog.fxml"));
        Parent root = FXMLLoader.load(getClass().getResource("/main/res/fxml/mainApp.fxml"));
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/main/res/img/favicon.png")));
//        primaryStage.initStyle(StageStyle.DECORATED);
        primaryStage.setTitle("Inha Library");
        primaryStage.setResizable(false);
        Scene scene = new Scene(root, 1280, 720);
        scene.getStylesheets().add(getClass().getResource("/main/res/css/style.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}