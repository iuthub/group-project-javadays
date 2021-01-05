package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableView;
import java.sql.SQLException;

public class StudentViewController {
    @FXML private ChoiceBox<String> search_type;
    @FXML private TableView<Result> table;
    @FXML private Pagination pagination;
    @FXML private Label total_count;
    private final StudentRepository sr;

    public StudentViewController() throws SQLException{
        sr = StudentRepository.getInstance();
    }

    public void updateTable(int page){
        try {
            this.table.setItems(sr.getForAdmin(page));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void initialize(){
        try {
            search_type.getItems().add("ID");
            search_type.getItems().add("Name");
            search_type.setValue("ID");

            int total = sr.getTotalCount();
            this.total_count.setText(String.format("Students count: %d", total));
            this.table.setItems(sr.getForAdmin(0));
            pagination.currentPageIndexProperty().addListener((obs, oldIndex, newIndex) -> updateTable(newIndex.intValue()));
            pagination.setPageCount((int) Math.ceil(total / 100.0));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
