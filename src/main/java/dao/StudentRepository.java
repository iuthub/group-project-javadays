package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.AdminWindowStudent;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentRepository {
    private static StudentRepository instance;
    private final PreparedStatement getForAdminStmt;
    private final PreparedStatement getTotalCountStmt;
    private final PreparedStatement addStudentStmt;
    private final PreparedStatement remStudentStmt;
    private final PreparedStatement updStudentStmt;
    private final String DISPLAY_QUERY_ADMIN = "SELECT UserID, FirstName || ' ' || LastName AS name FROM Users WHERE Role = 2 ";

    private StudentRepository() throws SQLException {
        Connection conn = ConnectionManager.getConnection();
        String TOTAL_COUNT = "SELECT COUNT(*) FROM Users WHERE Role = 2";
        getForAdminStmt = conn.prepareStatement(DISPLAY_QUERY_ADMIN + "OFFSET ? ROWS FETCH NEXT 100 ROWS ONLY");
        getTotalCountStmt = conn.prepareStatement(TOTAL_COUNT);
        addStudentStmt = conn.prepareStatement("INSERT INTO Users VALUES(?, ?, ?, ?, 2)");
        remStudentStmt = conn.prepareStatement("DELETE FROM Users WHERE UserID = ?");
        updStudentStmt = conn.prepareStatement("UPDATE Users SET UserID = ?, FirstName = ?, LastName = ?, Password = ? WHERE UserID = ?");

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

    public ObservableList<AdminWindowStudent> getForAdmin(int page) throws SQLException {
        ObservableList<AdminWindowStudent> list = FXCollections.observableArrayList();
        this.getForAdminStmt.setInt(1, page * 100);
        ResultSet result = this.getForAdminStmt.executeQuery();

        while (result.next()){
            list.add(
                new AdminWindowStudent(
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

    public ObservableList<AdminWindowStudent> searchForAdmin(String type, String search) throws SQLException {
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

        ObservableList<AdminWindowStudent> list = FXCollections.observableArrayList();
        ResultSet result = searchByParamStmt.executeQuery();

        while (result.next()){
            list.add(
                new AdminWindowStudent(
                    result.getString("UserID"),
                    result.getString("Name")
                )
            );
        }
        searchByParamStmt.close();
        return list;
    }

    public void add(String userId, String firstName, String lastName, String password) throws SQLException {
        addStudentStmt.setString(1, userId.toUpperCase());
        addStudentStmt.setString(2, capitalize(firstName));
        addStudentStmt.setString(3, capitalize(lastName));
        addStudentStmt.setString(4, capitalize(password));
        addStudentStmt.executeUpdate();
    }

    public void delete(String userId) throws SQLException{
        remStudentStmt.setString(1, userId);
        remStudentStmt.executeUpdate();
    }

    public void update(String oldId, String newId, String firstName, String lastName, String password) throws SQLException {
        updStudentStmt.setString(1, newId);
        updStudentStmt.setString(2, firstName);
        updStudentStmt.setString(3, lastName);
        updStudentStmt.setString(4, password);
        updStudentStmt.setString(5, oldId);
        updStudentStmt.executeUpdate();
    }
}
