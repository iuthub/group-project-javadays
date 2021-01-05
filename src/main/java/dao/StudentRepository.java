package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.StudentBorrowedBooks;

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

    private StudentRepository() throws SQLException {
        Connection conn = ConnectionManager.getConnection();
        String TOTAL_COUNT = "SELECT COUNT(*) FROM Users WHERE role = 2";
        getForAdminStmt = conn.prepareStatement(DISPLAY_QUERY_ADMIN + "OFFSET ? ROWS FETCH NEXT 100 ROWS ONLY");
        getTotalCountStmt = conn.prepareStatement(TOTAL_COUNT);
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

    public ObservableList<Result> searchForAdmin(String type, String search) throws SQLException {
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

        ObservableList<Result> list = FXCollections.observableArrayList();
        ResultSet result = searchByParamStmt.executeQuery();

        while (result.next()){
            list.add(
                new Result(
                    result.getString("userId"),
                    result.getString("name")
                )
            );
        }
        searchByParamStmt.close();
        return list;
    }
}
