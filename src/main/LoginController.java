package main;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.sql.SQLException;

public class LoginController {
    @FXML
    public BorderPane loginBorderPane;

    @FXML
    public Text h1;

    @FXML
    private TextField txtLogin;

    @FXML
    private PasswordField txtPassword;

    @FXML
    public void authenticate() {
        Dialog<ButtonType> dialog = new Dialog<>();

        String login = txtLogin.getText();
        String password = txtPassword.getText();

        dialog.initOwner(loginBorderPane.getScene().getWindow());
        dialog.setTitle("Result");

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("successDialog.fxml"));
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

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.showAndWait();
    }
}
