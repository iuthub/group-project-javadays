package main.java;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentRepository {
    private static StudentRepository instance;
    private final PreparedStatement getAllStmt;
    private final PreparedStatement getTotalCountStmt;

    private StudentRepository() throws SQLException {
        Connection conn = ConnectionManager.getConnection();
        getAllStmt = conn.prepareStatement("SELECT userId, firstName || ' ' || lastName AS name FROM Users");
        getTotalCountStmt = conn.prepareStatement("SELECT COUNT(*) FROM Users");
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

    public ObservableList<Result> getAll() throws SQLException {
        ObservableList<Result> list = FXCollections.observableArrayList();
        ResultSet result = this.getAllStmt.executeQuery();

        while (result.next()){
            list.add(
                new Result(
                    result.getString("userId"),
                    result.getString("name")
                )
            );
        }
        return list;
    }
}
