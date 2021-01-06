package controllers;

import dao.StudentRepository;
import dao.UsersRepository;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.SQLException;

public class AdminStudentAddController {
    @FXML private TextField txtID;
    @FXML private TextField txtFirstName;
    @FXML private TextField txtLastName;
    @FXML private TextField txtPassword;
    @FXML private TextField txtEmail;
    @FXML private Label lblStatus;

    boolean isFilled(TextField txt){
        return !txt.getText().equals("");
    }

    boolean hasInvalidLength(TextField txt){
        return txt.getText().length() > 30;
    }

    public boolean validate(){
        if (isFilled(txtID) && isFilled(txtFirstName) && isFilled(txtLastName)){
            String userId = txtID.getText().toUpperCase();

            if (userId.matches("U[\\d]{7}")){
                try {
                    boolean isExists = UsersRepository.getInstance().isExists(userId);
                    if (isExists){
                        lblStatus.setText("User with this ID already exists in database!");
                        return false;
                    }
                } catch (SQLException throwables) {
                    lblStatus.setText("AdminStudentAdd SQL ERROR");
                    throwables.printStackTrace();
                    return false;
                }
            } else{
                lblStatus.setText("Wrong ID pattern!");
                return false;
            }

            if (hasInvalidLength(txtFirstName) || hasInvalidLength(txtLastName) || hasInvalidLength(txtPassword)){
                lblStatus.setText("Name and password must be less than 30 characters long!");
                return false;
            }

        } else{
            lblStatus.setText("Not all (*)required fields are filled!");
            return false;
        }
        return true;
    }

    public void addStudent() throws SQLException{
        StudentRepository.getInstance().add(
            txtID.getText(), txtFirstName.getText(),
            txtLastName.getText(), txtPassword.getText()
        );
    }
}