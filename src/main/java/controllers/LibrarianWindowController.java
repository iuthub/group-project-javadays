package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class LibrarianWindowController {
    @FXML public AnchorPane mainAnchorPane;
    @FXML public VBox navbar;
    @FXML public TabPane booksTabPane;
    @FXML public TableView tblBooksTable;
    @FXML public GridPane studentsGridPane;
    @FXML Button btnLogOut;
    @FXML
    BorderPane mainBorderPane;

    public void handleHome(ActionEvent actionEvent) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/res/fxml/adminStudentView.fxml"));
        mainBorderPane.getChildren().remove(mainBorderPane.getCenter());
        try {
            mainBorderPane.setCenter(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleRegistrationPanel(ActionEvent actionEvent) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/res/fxml/registrationView.fxml"));
        mainBorderPane.getChildren().remove(mainBorderPane.getCenter());
        try {
            mainBorderPane.setCenter(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleJournal(ActionEvent actionEvent) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/res/fxml/journalView.fxml"));
        mainBorderPane.getChildren().remove(mainBorderPane.getCenter());
        try {
            mainBorderPane.setCenter(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleLogOut(ActionEvent actionEvent) throws IOException {
        HandleLogOut.logOut(getClass(), btnLogOut);
    }
}
