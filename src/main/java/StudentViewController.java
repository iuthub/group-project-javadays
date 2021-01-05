package main.java;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.SQLException;

public class StudentViewController {
    @FXML private ChoiceBox<String> search_type;
    @FXML private TextField search_field;
    @FXML private Button search_button;

    @FXML private Label total_count;
    @FXML private TableView<Result> table;
    @FXML private Pagination pagination;

    @FXML private Label lblName;
    @FXML private Label lblEmail;
    @FXML private Label lblPhone;
    @FXML private Label lblDepartment;
    @FXML private Label lblAcademicYear;

    @FXML private Button btnBookHistory;
    @FXML private Button btnModify;
    @FXML private Button btnDelete;

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

    void initTableView() throws SQLException {
        int total = sr.getTotalCount();
        this.total_count.setText(String.format("Students count: %d", total));
        this.table.setItems(sr.getForAdmin(0));
        pagination.setVisible(true);
        pagination.setPageCount((int) Math.ceil(total / 100.0));
    }

    void handleTableItemSelection(String uid){
        System.out.println(uid);
        btnBookHistory.setDisable(false);
        btnModify.setDisable(false);
        btnDelete.setDisable(false);


    }

    public void initialize(){
        try {
            search_type.getItems().add("User ID");
            search_type.getItems().add("First name");
            search_type.getItems().add("Last name");
            search_type.setValue("User ID");

            pagination.currentPageIndexProperty().addListener(
                    (obs, oldIndex, newIndex) -> updateTable(newIndex.intValue())
            );
            table.getSelectionModel().selectedItemProperty().addListener(
                    (obs, oldSelection, newSelection) -> handleTableItemSelection(newSelection.getUid())
            );
            initTableView();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void search(){
        try {
            if (!search_field.getText().equals("")){
                String search_by = search_type.getValue();
                this.table.setItems(sr.searchForAdmin(search_by, search_field.getText()));
                this.total_count.setText(String.format("Search results: %d", this.table.getItems().size()));
                this.pagination.setVisible(false);
            } else{
                initTableView();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}
