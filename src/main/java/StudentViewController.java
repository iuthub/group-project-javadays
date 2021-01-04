package main.java;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentViewController {
    @FXML public TableView<Result> table;
    private final PreparedStatement getAllStmt;
    private final PreparedStatement getTotalCountStmt;
    @FXML private Label total_count;

    public StudentViewController() throws SQLException {
        Connection conn = ConnectionManager.getConnection();
        getAllStmt = conn.prepareStatement("SELECT userId, firstName || ' ' || lastName AS name FROM Users");
        getTotalCountStmt = conn.prepareStatement("SELECT count(*) FROM Users");
    }

    public ObservableList<Result> getAll() throws SQLException{
        ResultSet result;
        ObservableList<Result> list = FXCollections.observableArrayList();

        result = this.getAllStmt.executeQuery();

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

    public void initialize(){
        try {
            ResultSet r = getTotalCountStmt.executeQuery();
            r.next();
            this.total_count.setText(String.format("Students count: %d", r.getInt(1)));
            this.table.setItems(this.getAll());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}
