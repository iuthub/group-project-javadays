package controllers;

import dao.StudentRepository;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableView;
import model.StudentBorrowedBooks;

import java.sql.SQLException;

public class AdminStudentViewController {
    @FXML private ChoiceBox<String> choiceBoxSearchType;
    @FXML private TableView<StudentBorrowedBooks> tblStudentTable;
    @FXML private Pagination pagination;
    @FXML private Label lblTotalCount;
    private final StudentRepository studentRepository;

    public AdminStudentViewController() throws SQLException{
        studentRepository = StudentRepository.getInstance();
    }

    public void updateTable(int page){
        try {
            this.tblStudentTable.setItems(studentRepository.getForAdmin(page));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void initialize(){
        try {
            choiceBoxSearchType.getItems().add("ID");
            choiceBoxSearchType.getItems().add("Name");
            choiceBoxSearchType.setValue("ID");

            int total = studentRepository.getTotalCount();
            this.lblTotalCount.setText(String.format("Students count: %d", total));
            this.tblStudentTable.setItems(studentRepository.getForAdmin(0));
            pagination.currentPageIndexProperty().addListener((obs, oldIndex, newIndex) -> updateTable(newIndex.intValue()));
            pagination.setPageCount((int) Math.ceil(total / 100.0));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
