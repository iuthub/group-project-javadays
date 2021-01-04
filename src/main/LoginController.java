package main;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Locale;

public class LoginController {
    @FXML private Label lblAlert;
    @FXML private BorderPane loginBorderPane;
    @FXML private TextField txtLogin;
    @FXML private PasswordField txtPassword;

    @FXML
    public void authenticate_old() {
        // Creates Dialog with Buttons
        Dialog<ButtonType> dialog = new Dialog<>();

        // Get all data inputted by user in loginDialog.fxml
        String login = txtLogin.getText();
        String password = txtPassword.getText();

        dialog.initOwner(loginBorderPane.getScene().getWindow());
        dialog.setTitle("Result");

        FXMLLoader fxmlLoader = new FXMLLoader();
        boolean isAuthenticated;

        // Check if user inputted correct login and password
        try {
            isAuthenticated = UsersRepository.getInstance().authenticate(login, password);

            if (isAuthenticated){
                fxmlLoader.setLocation(getClass().getResource("successDialog.fxml"));
            } else {
                fxmlLoader.setLocation(getClass().getResource("errorDialog.fxml"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Adding button OK
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);

        // Waiting until user's action is performed
        dialog.showAndWait();
    }

    public void authenticate(){
        String login = txtLogin.getText();
        String password = txtPassword.getText();
        boolean status = false;

        if (!login.equals("") && !password.equals("")){
            if (login.matches("[uU][\\d]{7}")){
                try {
                    status = UsersRepository.getInstance().authenticate(login, password);
                    if (!status) lblAlert.setText("Incorrect login or password!");
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                    lblAlert.setText(String.format("Database Error: %d", throwables.getErrorCode()));
                }
            } else {lblAlert.setText("Login pattern violation!");}

        } else {lblAlert.setText("Not all fields are filled!"); }

        if (status){
            lblAlert.setTextFill(Color.GREEN);
            lblAlert.setText("Success!");
        }
    }

}
