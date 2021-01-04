package main.java;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableView;
import java.sql.SQLException;

public class StudentViewController {
    @FXML private TableView<Result> table;
    @FXML private Pagination page_controller;
    @FXML private Label total_count;

    public void initialize(){
        try {
            StudentRepository sr = StudentRepository.getInstance();
            this.total_count.setText(String.format("Students count: %d", sr.getTotalCount()));
            this.table.setItems(sr.getAll());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
