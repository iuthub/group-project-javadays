package controllers;

import dao.StudentRepository;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import model.Student;

import java.sql.SQLException;

public class StudentStatusController {
    @FXML private Label lblStatus;
    @FXML private Label lblFine;

    public void init(String studentId) throws SQLException {
        Student st = StudentRepository.getInstance().getStudent(studentId);

        boolean status = st.isStatus();
        String style = status? "label-status-blocked": "label-status-active";
        String text = status? "BLOCKED": "ACTIVE";

        lblStatus.getStyleClass().setAll(style);
        lblStatus.setText(text);

        lblFine.setText(String.format("%s $", st.getFine()));
    }
}
