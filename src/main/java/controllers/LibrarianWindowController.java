package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import model.User;

import java.io.IOException;
import java.sql.SQLException;

public class LibrarianWindowController {
    @FXML public AnchorPane mainAnchorPane;
    @FXML public VBox navbar;

    @FXML public GridPane editBooksGridPane;
    @FXML public GridPane studentsGridPane;
    @FXML public GridPane registrationPanelGridPane;
    @FXML public GridPane journalGridPane;
    @FXML public BorderPane mainBorderPane;

    @FXML Button btnLogOut;

    private static User librarian;

    public static void setCurrentUser(User librarian) { LibrarianWindowController.librarian = librarian; }

    public void initialize(){ handleHome();}

    public void handleHome() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/res/fxml/LibrarianHomeView.fxml"));
        mainBorderPane.getChildren().remove(mainBorderPane.getCenter());

        try{
            mainBorderPane.setCenter(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }

        LibrarianHomeController controller = fxmlLoader.getController();
        controller.setLibrarianId(librarian.getUserID());

        try {
            controller.init();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void handleRegistrationPanel(ActionEvent actionEvent) {
        try {
            HandleChangeView.handleChangeScene(getClass(), mainBorderPane, "/res/fxml/librarianRegistrationView.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleJournal(ActionEvent actionEvent) {

        try {
            HandleChangeView.handleChangeScene(getClass(), mainBorderPane, "/res/fxml/librarianJournalView.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleEditBooks(ActionEvent actionEvent) {
        try {
            HandleChangeView.handleChangeScene(getClass(), mainBorderPane, "/res/fxml/librarianEditBooksView.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleStudents(ActionEvent actionEvent) {
        try {
            HandleChangeView.handleChangeScene(getClass(), mainBorderPane, "/res/fxml/adminStudentView.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleOverdue(ActionEvent actionEvent) {
        try {
            HandleChangeView.handleChangeScene(getClass(), mainBorderPane, "/res/fxml/librarianOverdueView.fxml");
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void handleLogOut(ActionEvent actionEvent) throws IOException {
        HandleLogOut.logOut(getClass(), btnLogOut);
    }
}
