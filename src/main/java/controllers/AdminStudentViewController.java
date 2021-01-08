package controllers;

import dao.Role;
import dao.UsersRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import model.AdminWindowStudent;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Random;

// Originally written by Jasur Yusupov

public class AdminStudentViewController {
    @FXML private ChoiceBox<String> choiceBoxSearchType;
    @FXML private TextField searchField;

    @FXML private Label lblTotalCount;
    @FXML private TableView<AdminWindowStudent> tblStudentsDisplay;
    @FXML private Pagination pagination;

    @FXML private Button btnAdd;

    @FXML private ImageView imgUserPhoto;
    @FXML private Label lblName;
    @FXML private Label lblEmail;
    @FXML private Label lblPhone;
    @FXML private Label lblDepartment;
    @FXML private Label lblAcademicYear;

    @FXML private Button btnBookHistory;
    @FXML private Button btnModify;
    @FXML private Button btnDelete;

    private final UsersRepository ur;
    private String selectedStudentId;

    public AdminStudentViewController() throws SQLException{
        ur = UsersRepository.getInstance();
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

    public void updateTable(int page){
        try {
            this.tblStudentsDisplay.setItems(ur.getForAdmin(Role.STUDENT, page));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    void initTableView() throws SQLException {
        int total = ur.getTotalCount(Role.STUDENT);
        this.lblTotalCount.setText(String.format("Students count: %d", total));
        this.tblStudentsDisplay.setItems(ur.getForAdmin(Role.STUDENT, pagination.getCurrentPageIndex()));
        pagination.setVisible(true);
        pagination.setPageCount((int) Math.ceil(total / 100.0));
    }

    String generateEmail(String name){
        String[] n = name.split(" ");
        return String.format("%s.%s@student.inha.uz", n[0].substring(0, 1).toLowerCase(), n[1].toLowerCase());
    }

    String generatePhone(String userId){
        Random r = new Random();
        String seq = userId.substring(5);
        int a = r.nextInt(8) + 1;
        int b = r.nextInt(9);
        int c = a * 10 + b;
        return String.format("+998 %d %s %d %d", 90 + r.nextInt(3), seq, c, Math.abs(c - a*b));
    }

    String generateDepartment(){
        return new String[]{"SOL", "SOCIE"}[new Random().nextInt(1)];
    }

    String generateAcademicYear(String userId){
        int year = Integer.parseInt(userId.substring(1, 3));
        if (year <= 20 && year >= 14){
            return String.format("20%d", year);
        }
        return String.format("%d", 2014 + new Random().nextInt(6));
    }

    void handleTableItemSelection(AdminWindowStudent student){
        if (student != null){
            selectedStudentId = student.getUserId();

            lblName.setText(student.getName());
            lblEmail.setText(generateEmail(student.getName()));
            lblPhone.setText(generatePhone(student.getUserId()));
            lblDepartment.setText(generateDepartment());
            lblAcademicYear.setText(generateAcademicYear(student.getUserId()));

            btnBookHistory.setDisable(false);
            btnModify.setDisable(false);
            btnDelete.setDisable(false);
        } else{
            selectedStudentId = null;
        }
    }

    public void search(){
        try {
            if (!searchField.getText().equals("")){
                String search_by = choiceBoxSearchType.getValue();
                this.tblStudentsDisplay.setItems(ur.searchForAdmin(Role.STUDENT, search_by, searchField.getText()));
                this.lblTotalCount.setText(String.format("Search results: %d", this.tblStudentsDisplay.getItems().size()));
                this.pagination.setVisible(false);
            } else{
                initTableView();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void createStudent() throws SQLException {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(btnAdd.getScene().getWindow());
        dialog.setTitle("Add Student");
        dialog.setResizable(false);

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/fxml/adminAddModifyDialog.fxml"));

        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException ex){
            System.out.println(ex.getMessage());
        }

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        AdminAddModifyDialogController controller = fxmlLoader.getController();

        final Button okButton = (Button) dialog.getDialogPane().lookupButton(ButtonType.OK);
        okButton.addEventFilter(ActionEvent.ACTION, ae -> {
            if (controller.notValid()) {
                ae.consume();
            }
        });

        Optional<ButtonType> result = dialog.showAndWait();

        if(result.isPresent() && result.get() == ButtonType.OK) {
            controller.addStudent(Role.STUDENT);
        }

        initTableView();
    }

    public void deleteStudent() throws SQLException {
        if (selectedStudentId != null){
            ur.delete(selectedStudentId);
            unselect();
        }
    }

    public void modifyStudent() throws SQLException{
        if (selectedStudentId != null) {
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.initOwner(btnModify.getScene().getWindow());
            dialog.setTitle("Modify Student");
            dialog.setResizable(false);

            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/fxml/adminAddModifyDialog.fxml"));

            try {
                dialog.getDialogPane().setContent(fxmlLoader.load());
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }

            dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
            dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

            AdminAddModifyDialogController controller = fxmlLoader.getController();
            controller.initializeFields(selectedStudentId);

            final Button okButton = (Button) dialog.getDialogPane().lookupButton(ButtonType.OK);
            okButton.addEventFilter(ActionEvent.ACTION, ae -> {
                if (controller.notValid()) {
                    ae.consume();
                }
            });

            Optional<ButtonType> result = dialog.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                controller.modifyStudent();
            }

            unselect();
        }
    }

    void unselect() throws SQLException{
        selectedStudentId = null;
        btnBookHistory.setDisable(true);
        btnDelete.setDisable(true);
        btnModify.setDisable(true);
        lblName.setText("");
        lblEmail.setText(null);
        lblPhone.setText(null);
        initTableView();
    }
}
