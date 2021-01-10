package controllers;

import dao.BooksRepository;
import dao.IssuedBookRepository;
import dao.Role;
import dao.UsersRepository;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import model.User;

import java.sql.SQLException;

public class LibrarianHomeController{
    @FXML
    private Label lblUserID;
    @FXML private Label lblUserName;
    @FXML private Label lblIssuedBooksCount;
    @FXML private Label lblStudentsCount;
    @FXML private Label lblBooksCount;
    private String librarianId;

    public void setLibrarianId(String librarianId) {
        this.librarianId = librarianId;
    }

    public void init() throws SQLException {
        UsersRepository ur = UsersRepository.getInstance();
        IssuedBookRepository issuedBookRepository = IssuedBookRepository.getInstance();
        int issuedBooksCount = issuedBookRepository.getAllIssued().size();
        int students = ur.getTotalCount(Role.STUDENT);
        int books = BooksRepository.getInstance().getTotalCount();

        lblIssuedBooksCount.setText(Integer.toString(issuedBooksCount));
        lblStudentsCount.setText(Integer.toString(students));
        lblBooksCount.setText(Integer.toString(books));

        User u = ur.get(librarianId);
        lblUserID.setText(u.getUserId());
        lblUserName.setText(String.format("%s %s", u.getFirstName(), u.getLastName()));
    }

}
