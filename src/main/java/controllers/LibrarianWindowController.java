package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
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

    public void handleHome(ActionEvent actionEvent) {
        studentsGridPane.setVisible(false);
        booksTabPane.setVisible(false);
    }

    public void handleBooks(ActionEvent actionEvent) {
        tblBooksTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        studentsGridPane.setVisible(false);
        booksTabPane.setVisible(true);
    }

    public void handleStudents(ActionEvent actionEvent) {
        booksTabPane.setVisible(false);
        studentsGridPane.setVisible(true);
    }

    public void handleLogOut(ActionEvent actionEvent) throws IOException {
        HandleLogOut.logOut(getClass(), btnLogOut);
    }
}
