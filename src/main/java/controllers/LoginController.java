package controllers;

import dao.UsersRepository;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.User;

import java.io.IOException;
import java.sql.SQLException;

public class LoginController {
    @FXML private Button btnSubmit;
    @FXML private Label lblAlert;
    @FXML private BorderPane loginBorderPane;
    @FXML private TextField txtUserID;
    @FXML private PasswordField txtPassword;

    /**
     * This method verifies if user submitted correct login and password.
     * If UserID is not of the form "UXXXXXXX" where X is a digit, it asks user to re-submit
     * credits to Jasur Yusupov
     */
    public void handleSubmit() throws IOException {
        Stage mainAppStage;
        Parent root;

        String userID = txtUserID.getText().toUpperCase();
        String password = txtPassword.getText();
        boolean status = true;


        if (!userID.equals("") && !password.equals("")){
            if (userID.matches("U[\\d]{7}")){
                try {
                    status = UsersRepository.getInstance().authenticate(userID, password);
                    if (!status) lblAlert.setText("Incorrect login or password!");
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                    lblAlert.setText(String.format("Database Error: %d", throwables.getErrorCode()));
                }
            } else {lblAlert.setText("Login pattern violation!");}

        } else {lblAlert.setText("Not all fields are filled!"); }

        // If user is authenticated
        if (status){
            lblAlert.setTextFill(Color.GREEN);
            lblAlert.setText("Success!");

            User currentUser = null;
            try {
                currentUser = UsersRepository.getInstance().get(userID);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            // Changing Stage to the main App
            mainAppStage = (Stage) btnSubmit.getScene().getWindow();
            mainAppStage.setResizable(false);

            assert currentUser != null;
            int roleType = currentUser.getRole().getValue();
            System.out.println(roleType);
            
            switch (roleType) {
                case 0:
                    root = FXMLLoader.load(getClass().getResource("/res/fxml/adminWindow.fxml"));
                    break;
                case 1:
                    root = FXMLLoader.load(getClass().getResource("/res/fxml/librarianWindow.fxml"));
                    break;
                case 2:
                    root = FXMLLoader.load(getClass().getResource("/res/fxml/studentWindow.fxml"));
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + roleType);
            }
            
            Scene mainAppScene = new Scene(root, 1280, 720);
            mainAppScene.getStylesheets().add(getClass().getResource("/res/css/style.css").toExternalForm());
            mainAppStage.setScene(mainAppScene);
            mainAppStage.show();
        }
        
        
    }
}
