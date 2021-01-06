package controllers;

import dao.StudentRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import model.StudentBorrowedBooks;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

public class AdminStudentViewController {
    @FXML private ChoiceBox<String> choiceBoxSearchType;
    @FXML private TextField searchField;
    @FXML private Button btnSearch;

    @FXML private Label lblTotalCount;
    @FXML private TableView<StudentBorrowedBooks> tblStudentsDisplay;
    @FXML private Pagination pagination;

    @FXML private Button btnAdd;

    @FXML private ImageView imgStudentPhoto;
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

    void handleTableItemSelection(StudentBorrowedBooks student){
        if (student != null){
            System.out.println(student.getUserId());
            btnBookHistory.setDisable(false);
            btnModify.setDisable(false);
            btnDelete.setDisable(false);
        } else{
            System.out.println("NULL!");
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

    public void createStudent() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(btnAdd.getScene().getWindow());
        dialog.setTitle("Add Student");

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/res/fxml/adminStudentCreateDialog.fxml"));

        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException ex){
            System.out.println(ex.getMessage());
        }

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        AdminStudentAddController controller = fxmlLoader.getController();

        Optional<ButtonType> result = dialog.showAndWait();

        if(result.isPresent() && result.get()==ButtonType.OK) {
            System.out.println("Button pressed!");
        }
    }
}
