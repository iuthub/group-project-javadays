package controllers;
import dao.*;
import dao.IssuedBook;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import model.IssuedBookRow;
import model.Student;
import model.User;

import java.io.IOException;
import java.sql.*;

public class StudentHomeController {

    @FXML
    private BorderPane mainBorderPane;
    @FXML
    private Button btnLogOut;
    @FXML
    public Label labelNameStudent;
    @FXML
    public Label labelSurnameStudent;
    @FXML
    public Label labelIDStudent;
    @FXML
    private TableView<IssuedBookRow> studentTblIssuedBooks;
    @FXML
    private Label labelFineStudent;
    @FXML
    private Label labelStatusStudent;

    private static String userID;
    private BooksRepository booksRepository;
    private final Connection connection;

    public StudentHomeController() throws SQLException {
        connection = ConnectionManager.getConnection();
        booksRepository = BooksRepository.getInstance();
        userID=LoginController.getUserID();
    }
    public void initialize() throws SQLException {
//        StudentRepository.getInstance().updateStudents();
        setInfo();
        setIssuedBooksByStudent();
    }

    public void setInfo() throws SQLException {
        User user = UsersRepository.getInstance().get(userID);
        if (user != null && labelNameStudent != null) {
            labelNameStudent.setText(user.getFirstName());
            labelSurnameStudent.setText(user.getLastName());
            labelIDStudent.setText(userID);
        }

        Student student = StudentRepository.getInstance().getStudent(userID);
        if (student != null && labelFineStudent != null) {
            labelFineStudent.setText(String.valueOf(student.getFine()));

            if (student.isStatus()) {
                labelStatusStudent.setText("Blocked");
                labelStatusStudent.setStyle("-fx-text-fill: red;");
            } else {
                labelStatusStudent.setText("Active");
                labelStatusStudent.setStyle("-fx-text-fill: green;");
            }
        }
    }

    public void setIssuedBooksByStudent() throws SQLException {
        ObservableList<IssuedBookRow> issuedBooksRows = FXCollections.observableArrayList();
        ObservableList<IssuedBook> issuedBooks= FXCollections.observableArrayList();

            int issuedBookId;
            ResultSet result;
            PreparedStatement preparedStatement;
            ResultSet resultForID;
            PreparedStatement preparedStatementForID;

            issuedBooks = IssuedBookRepository.getInstance().getByUser(userID);
            for (int i = 0; i < issuedBooks.size(); i++) {
                issuedBookId = issuedBooks.get(i).getIssueBookId();
                //taking data from Books database
                preparedStatement = connection.prepareStatement("Select * FROM Books Where BookID = ?");
                preparedStatement.setInt(1, issuedBookId);
                result = preparedStatement.executeQuery();
                if(result.next()) {
                    //taking data from IssuedBooks database
                    preparedStatementForID = connection.prepareStatement("Select * FROM IssuedBooks Where BookID = ?");
                    preparedStatementForID.setInt(1, issuedBookId);
                    resultForID = preparedStatementForID.executeQuery();
                    //setting them to columns
                    if(resultForID.next()) {
                        issuedBooksRows.add(new IssuedBookRow(result.getString(2), result.getString(3), result.getString(5), resultForID.getDate(3)));
                    }
            }
               if(studentTblIssuedBooks!=null) {
                    studentTblIssuedBooks.setItems(issuedBooksRows);
                }
        }

    }


    public void handleStudentBooks(){

        FXMLLoader fxmlLoader= new FXMLLoader(getClass().getResource("/res/fxml/studentBooksView.fxml"));
        mainBorderPane.getChildren().remove(mainBorderPane.getCenter());
        try
        {
            mainBorderPane.setCenter(fxmlLoader.load());
        }
        catch (IOException e){e.printStackTrace();}

    }
    public void handleLogOut() throws IOException {
        HandleLogOut.logOut(getClass(), btnLogOut);
    }

    public void handleStudentHome() throws SQLException{
        StudentRepository.getInstance();
        FXMLLoader fxmlLoader= new FXMLLoader(getClass().getResource("/res/fxml/studentHomeView.fxml"));
        mainBorderPane.getChildren().remove(mainBorderPane.getCenter());
        try {
            mainBorderPane.setCenter(fxmlLoader.load());
        }
        catch (IOException e){e.printStackTrace();}

    }
}