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



    public void handleSubmit(){
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
