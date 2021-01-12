package controllers;

import dao.UsersRepository;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

import java.sql.SQLException;

public class AdminSettingsController {
    @FXML private PasswordField txtOldPassword;
    @FXML private PasswordField txtNewPassword;
    @FXML private PasswordField txtRePassword;
    @FXML private Label lblStatus;
    private String adminId;
    private String password;

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    boolean isFilled(TextField txt){
        return !txt.getText().equals("");
    }

    public void changePassword() {
        if (lblStatus.getTextFill() == Color.GREEN){
            lblStatus.setTextFill(Color.RED);
        }

        if (isFilled(txtOldPassword) && isFilled(txtNewPassword) && isFilled(txtRePassword)){
            String newPassword = txtNewPassword.getText();

            if (txtOldPassword.getText().equals(password)){
                if (!newPassword.equals(password)){
                    if (newPassword.equals(txtRePassword.getText())){
                        if (newPassword.length() <= 30){
                            try {
                                UsersRepository.getInstance().changePassword(adminId, password, newPassword);

                                lblStatus.setTextFill(Color.GREEN);
                                lblStatus.setText("Password changed successfully");

                            } catch (SQLException throwables) {
                                lblStatus.setText("Database ERROR!");
                                throwables.printStackTrace();
                            }
                        } else
                            lblStatus.setText("Password length should not exceed 30 length");
                    } else
                        lblStatus.setText("Re-entered password doesn't match new password!");
                } else
                    lblStatus.setText("Old and new passwords cannot be same!");
            } else
                lblStatus.setText("Old password is incorrect!");
        } else
            lblStatus.setText("Not all fields are filled!");
    }
}
