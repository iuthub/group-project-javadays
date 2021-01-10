package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Student;
import model.User;

import java.sql.*;

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

    public Student getStudent(String userId) throws SQLException {
        PreparedStatement getStudentFineStmt = this.connection.prepareStatement("SELECT * FROM Students WHERE UserId=?");
        getStudentFineStmt.setString(1, userId);

        ResultSet result = getStudentFineStmt.executeQuery();
        if (result.next()) {
            return new Student(
                    result.getString("userID"),
                    result.getInt("fine"),
                    result.getInt("status") == 1
            );
        }
        return null;
    }

//    public void updateStudents() throws SQLException {
//
//        ObservableList<User> users;
//        PreparedStatement updateStudentsStmt = this.connection.prepareStatement("INSERT INTO Students(UserID) VALUES(?)");
//
//        users = UsersRepository.getInstance().getAll(Role.STUDENT);
//
//        for (User user : users) {
//            updateStudentsStmt.setString(1, user.getUserId());
//            updateStudentsStmt.executeUpdate();
//        }
//    }


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
