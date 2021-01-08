package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.StudentBorrowedBooks;
import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;

public class StudentRepository {
    private static StudentRepository instance;
    private final PreparedStatement getForAdminStmt;
    private final PreparedStatement getTotalCountStmt;
    private final String DISPLAY_QUERY_ADMIN = "SELECT userId, firstName || ' ' || lastName AS name FROM Users WHERE role = 2 ";

    Connection connection;

    private StudentRepository() throws SQLException {
        connection = ConnectionManager.getConnection();
        String TOTAL_COUNT = "SELECT COUNT(*) FROM Users WHERE role = 2";
        getForAdminStmt = connection.prepareStatement(DISPLAY_QUERY_ADMIN + "OFFSET ? ROWS FETCH NEXT 100 ROWS ONLY");
        getTotalCountStmt = connection.prepareStatement(TOTAL_COUNT);
    }

    // Singleton object getInstance() method
    public static StudentRepository getInstance() throws SQLException {
        if (instance == null) {
            instance = new StudentRepository();
        }
        return instance;
    }

    public int getTotalCount() throws SQLException{
        ResultSet result = getTotalCountStmt.executeQuery();
        result.next();
        return result.getInt(1);
    }

    public ObservableList<StudentBorrowedBooks> getForAdmin(int page) throws SQLException {
        ObservableList<StudentBorrowedBooks> list = FXCollections.observableArrayList();
        this.getForAdminStmt.setInt(1, page * 100);
        ResultSet result = this.getForAdminStmt.executeQuery();

        while (result.next()){
            list.add(
                new StudentBorrowedBooks(
                    result.getString("UserID"),
                    result.getString("Name")
                )
            );
        }
        return list;
    }

    private String capitalize(String in){
        return in.substring(0, 1).toUpperCase() + in.substring(1).toLowerCase();
    }

    public ObservableList<StudentBorrowedBooks> searchForAdmin(String type, String search) throws SQLException {
        Connection conn = ConnectionManager.getConnection();
        String cName = "UserId";
        search = capitalize(search);

        switch (type) {
            case "User ID":
                cName = "UserId";
                if (!search.matches("[Uu].+")){
                    search = "U"+search;
                }
                break;
            case "First name":
                cName = "firstName";
                break;
            case "Last name":
                cName = "lastName";
                break;
        }
        String LIKE_QUERY = String.format("AND %s LIKE ?", cName);
        PreparedStatement searchByParamStmt = conn.prepareStatement(DISPLAY_QUERY_ADMIN + LIKE_QUERY);
        searchByParamStmt.setString(1, search + "%");

        ObservableList<StudentBorrowedBooks> list = FXCollections.observableArrayList();
        ResultSet result = searchByParamStmt.executeQuery();

        while (result.next()){
            list.add(
                new StudentBorrowedBooks(
                    result.getString("userId"),
                    result.getString("name")
                )
            );
        }
        searchByParamStmt.close();
        return list;
    }

    //Returns student who borrowed book
    public ObservableList<User> getStudentsWithBooks() throws SQLException
    {
        ResultSet result;
        ObservableList<User> students = FXCollections.observableArrayList();//Students who borrowed books
        ObservableList<String> IDs = FXCollections.observableArrayList();//Student's IDs who borrowed book

        //IDs of students who borrowed book
        String studentIDsStat = "Select Users.UserId From Users" +
                " Inner Join IssuedBooks  " +
                "On Users.UserId = IssuedBooks.UserId " +
                "Group By Users.UserId";

        PreparedStatement studentsWithBookStatement = connection.prepareStatement(studentIDsStat);

        result = studentsWithBookStatement.executeQuery();

        while (result.next())
            IDs.add(result.getString("UserId"));

        for (String id:IDs)
        {
            String studentWithBooks = String.format("Select * From Users Where userId= '%s'",id);

            PreparedStatement studentWithBookStat = connection.prepareStatement(studentWithBooks);

            result = studentWithBookStat.executeQuery();

            if(result.next())
            {
                User student = new User(result.getString("UserId"),
                        result.getString("password"),
                        result.getString("FirstName"),
                        result.getString("LastName"),
                        null);

                students.add(student);
            }
        }

        return students;
    }
}
