package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentRepository {
    private static StudentRepository instance;

    Connection connection;

    private StudentRepository() throws SQLException {
        connection = ConnectionManager.getConnection();
    }

    // Singleton object getInstance() method
    public static StudentRepository getInstance() throws SQLException {
        if (instance == null) {
            instance = new StudentRepository();
        }
        return instance;
    }

    //Returns student who borrowed book
    public ObservableList<User> getStudentsWithBooks() throws SQLException {
        ObservableList<User> students = FXCollections.observableArrayList(); //Students who borrowed books
        ObservableList<String> IDs = FXCollections.observableArrayList(); //Student's IDs who borrowed book

        //IDs of students who borrowed book
        String studentIDsStat = "SELECT Users.UserID FROM Users INNER JOIN IssuedBooks " +
                                "ON Users.UserID = IssuedBooks.UserID GROUP BY Users.UserID";

        PreparedStatement studentsWithBookStatement = connection.prepareStatement(studentIDsStat);
        PreparedStatement studentWithBookStat = connection.prepareStatement("SELECT * FROM Users WHERE UserID = ?");

        ResultSet result = studentsWithBookStatement.executeQuery();
        while(result.next()) {
            IDs.add(result.getString("UserID"));
        }

        for (String id:IDs) {
            studentWithBookStat.setString(1, id);

            result = studentWithBookStat.executeQuery();
            if(result.next()) {
                students.add(
                    new User(
                        result.getString("UserID"),
                        result.getString("Password"),
                        result.getString("FirstName"),
                        result.getString("LastName"),
                        null
                    )
                );
            }
        }
        return students;
    }
}
