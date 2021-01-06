package controllers;

import dao.StudentRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class StudentWindowController {

    @FXML
    private BorderPane mainBorderPane;
    @FXML
    private Button btnLogOut;
    @FXML
    private Button btnStudentHome;
    @FXML
    private Button btnStudentBooks;
    private Stage primaryStage;
    @FXML private ChoiceBox<String> filterBooks;

    private final StudentRepository newInst;

    public StudentWindowController() throws SQLException {
        newInst =  StudentRepository.getInstance();
    }

    public void handleStudentBooks(ActionEvent actionEvent)
    {

        FXMLLoader fxmlLoader= new FXMLLoader(getClass().getResource("/res/fxml/studentBooksView.fxml"));
        mainBorderPane.getChildren().remove(mainBorderPane.getCenter());
        try
        {
            mainBorderPane.setCenter(fxmlLoader.load());
        }
        catch (IOException e){e.printStackTrace();}

    }
    public void handleLogOut(ActionEvent actionEvent) throws IOException {
        HandleLogOut.logOut(getClass(), btnLogOut);
    }
    public void handleStudentHome(ActionEvent actionEvent) throws SQLException{
        StudentRepository.getInstance();
        FXMLLoader fxmlLoader= new FXMLLoader(getClass().getResource("/res/fxml/studentHomeView.fxml"));
        mainBorderPane.getChildren().remove(mainBorderPane.getCenter());
        try
        {
            mainBorderPane.setCenter(fxmlLoader.load());
        }
        catch (IOException e){e.printStackTrace();}

    }

}
