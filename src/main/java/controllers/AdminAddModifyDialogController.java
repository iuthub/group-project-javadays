package controllers;

import dao.Role;
import dao.UsersRepository;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.User;
import java.sql.SQLException;

public class AdminAddModifyDialogController {
    @FXML private TextField txtID;
    @FXML private TextField txtFirstName;
    @FXML private TextField txtLastName;
    @FXML private TextField txtPassword;
    @FXML private Label lblStatus;
    private String userId;

    public void initializeFields(String userId) throws SQLException {
        User u = UsersRepository.getInstance().get(userId);
        this.userId = u.getUserId();
        txtID.setText(u.getUserId());
        txtFirstName.setText(u.getFirstName());
        txtLastName.setText(u.getLastName());
        txtPassword.setText(u.getPassword());
    }

    boolean isFilled(TextField txt){
        return !txt.getText().equals("");
    }

    boolean hasInvalidLength(TextField txt){
        return txt.getText().length() > 30;
    }

    boolean isValidId(String userId){
        return userId.matches("U[\\d]{7}");
    }

    public boolean notValid(){
        if (isFilled(txtID) && isFilled(txtFirstName) && isFilled(txtLastName) && isFilled(txtPassword)){
            String userId = txtID.getText().toUpperCase();

            boolean notSame = true;
            if (this.userId != null){
                notSame = !userId.equals(this.userId);
            }

            if (isValidId(userId)){
                if (notSame){
                    try {
                        boolean isExists = UsersRepository.getInstance().isExists(userId);
                        if (isExists){
                            lblStatus.setText("User with this ID already exists in database!");
                            return true;
                        }
                    } catch (SQLException throwables) {
                        lblStatus.setText("AdminStudentAdd SQL ERROR");
                        throwables.printStackTrace();
                        return true;
                    }
                }
            } else{
                lblStatus.setText("Wrong ID pattern!");
                return true;
            }

            if (hasInvalidLength(txtFirstName) || hasInvalidLength(txtLastName) || hasInvalidLength(txtPassword)){
                lblStatus.setText("Name and password must be less than 30 characters long!");
                return true;
            }

        } else{
            lblStatus.setText("Not all (*) required fields are filled!");
            return true;
        }
        return false;
    }

    public void addStudent(Role role) throws SQLException{
        UsersRepository.getInstance().add(
            txtID.getText(), txtFirstName.getText(),
            txtLastName.getText(), txtPassword.getText(), role
        );
    }

    public void modifyStudent() throws SQLException{
        UsersRepository.getInstance().update(
            this.userId, txtID.getText(),
            txtFirstName.getText(), txtLastName.getText(), txtPassword.getText()
        );
    }



}