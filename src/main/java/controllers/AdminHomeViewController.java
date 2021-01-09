package controllers;

import dao.BooksRepository;
import dao.Role;
import dao.UsersRepository;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import model.User;

import java.sql.SQLException;

public class AdminHomeViewController {
    @FXML private Label lblUserID;
    @FXML private Label lblUserName;
    @FXML private Label lblLibrariansCount;
    @FXML private Label lblStudentsCount;
    @FXML private Label lblBooksCount;
    private String adminId;

    public void setAdminId(String adminId) {
        System.out.println(adminId);
        this.adminId = adminId;
    }

    public void init() throws SQLException {
        System.out.println(adminId);
        UsersRepository ur = UsersRepository.getInstance();
        int librarians = ur.getTotalCount(Role.LIBRARIAN);
        int students = ur.getTotalCount(Role.STUDENT);
        int books = BooksRepository.getInstance().getTotalCount();

        lblLibrariansCount.setText(Integer.toString(librarians));
        lblStudentsCount.setText(Integer.toString(students));
        lblBooksCount.setText(Integer.toString(books));

        User u = ur.get(adminId);
        lblUserID.setText(u.getUserId());
        lblUserName.setText(String.format("%s %s", u.getFirstName(), u.getLastName()));
    }
}
