package controllers;

import dao.StudentRepository;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/res/fxml/loginDialog.fxml"));
//        Parent root = FXMLLoader.load(getClass().getResource("/main/res/fxml/adminWindow.fxml"));
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/res/img/favicon.png")));
//        primaryStage.initStyle(StageStyle.DECORATED);
        primaryStage.setTitle("Inha Library");
        primaryStage.setResizable(true);
        Scene scene = new Scene(root, 1280, 720);
        scene.getStylesheets().add(getClass().getResource("/res/css/style.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();

//        TestClass test = new TestClass();
        System.out.println(StudentRepository.getInstance().getStudent("U1001606").getFine());
    }


    public static void main(String[] args) {
        launch(args);
    }
}