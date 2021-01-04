package main.java;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class MainApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/main/res/fxml/loginDialog.fxml"));
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/main/res/img/favicon.png")));
        primaryStage.setTitle("Inha Library");
        primaryStage.setResizable(false);
        Scene scene = new Scene(root, 960, 540);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}


