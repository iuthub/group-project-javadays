package controllers;

import dao.ConnectionManager;
import dao.Role;
import dao.UsersRepository;
import javafx.collections.ObservableList;
import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TestClass {

    ObservableList<User> users;

    public TestClass() throws SQLException {
        Connection conn = ConnectionManager.getConnection();
        PreparedStatement testPreparedStatement = conn.prepareStatement("INSERT INTO Students(UserID) VALUES(?)");

        users = UsersRepository.getInstance().getAll(Role.STUDENT);

        for (User user : users) {
            testPreparedStatement.setString(1, user.getUserId());
            testPreparedStatement.executeUpdate();
        }


    }
}
