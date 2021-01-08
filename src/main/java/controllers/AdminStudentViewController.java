package controllers;

import dao.StudentRepository;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.AdminWindowStudent;

import java.sql.SQLException;

public class AdminStudentViewController {
    @FXML private ChoiceBox<String> choiceBoxSearchType;
    @FXML private TextField searchField;
    @FXML private Button btnSearch;

    @FXML private Label lblTotalCount;
    @FXML private TableView<AdminWindowStudent> tblStudentsDisplay;
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

    public AdminStudentViewController() throws SQLException{
        sr = StudentRepository.getInstance();
    }

    public void updateTable(int page){
        try {
            this.tblStudentsDisplay.setItems(sr.getForAdmin(page));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    void initTableView() throws SQLException {
        int total = sr.getTotalCount();
        this.lblTotalCount.setText(String.format("Students count: %d", total));
        this.tblStudentsDisplay.setItems(sr.getForAdmin(0));
        pagination.setVisible(true);
        pagination.setPageCount((int) Math.ceil(total / 100.0));
    }

    void handleTableItemSelection(AdminWindowStudent student){
        if (student != null){
            System.out.println(student.getUserId());
            btnBookHistory.setDisable(false);
            btnModify.setDisable(false);
            btnDelete.setDisable(false);
        } else{
            System.out.println("NULL JALAB!");
        }
    }

    public void initialize(){
        try {
            choiceBoxSearchType.getItems().add("User ID");
            choiceBoxSearchType.getItems().add("First name");
            choiceBoxSearchType.getItems().add("Last name");
            choiceBoxSearchType.setValue("User ID");

            pagination.currentPageIndexProperty().addListener(
                    (obs, oldIndex, newIndex) -> updateTable(newIndex.intValue())
            );
            tblStudentsDisplay.getSelectionModel().selectedItemProperty().addListener(
                    (obs, oldSelection, newSelection) -> handleTableItemSelection(newSelection)
            );
            initTableView();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void search(){
        try {
            if (!searchField.getText().equals("")){
                String search_by = choiceBoxSearchType.getValue();
                this.tblStudentsDisplay.setItems(sr.searchForAdmin(search_by, searchField.getText()));
                this.lblTotalCount.setText(String.format("Search results: %d", this.tblStudentsDisplay.getItems().size()));
                this.pagination.setVisible(false);
            } else{
                initTableView();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}
