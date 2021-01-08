package controllers;
import dao.*;
import dao.IssuedBook;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
    private TableView studentTblIssuedBooks;
    @FXML private ChoiceBox<String> filterBooks;
    private static String userID;
    private StudentRepository studentRepository;
    private IssuedBookRepository issuedBookRepository;
    ObservableList<IssuedBook> issuedBooks;
    private final Connection connection;
    public StudentHomeController() throws SQLException {
        connection = ConnectionManager.getConnection();
        studentRepository = StudentRepository.getInstance();
        issuedBookRepository = IssuedBookRepository.getInstance();
        userID=LoginController.getUserID();

      //  setIssuedBooksByStudent();

    }
    public void initialize() throws SQLException {
        setInfo();
        setIssuedBooksByStudent();
    }
    public void setInfo() throws SQLException {
        ResultSet result;
        PreparedStatement preparedStatement;
        preparedStatement = connection.prepareStatement("Select * FROM Users Where UserId = ?");
        preparedStatement.setString(1, userID);
        result=preparedStatement.executeQuery();
        if (result.next()&&labelNameStudent!=null) {
            labelNameStudent.setText(result.getString("FirstName"));
            labelSurnameStudent.setText(result.getString("LastName"));
            labelIDStudent.setText(userID);
        }
    }
    public void setIssuedBooksByStudent() throws SQLException {

        issuedBooks = issuedBookRepository.getByUser(userID);
        studentTblIssuedBooks.setItems(issuedBooks);


    }
    public void handleStudentBooks(ActionEvent actionEvent){

        FXMLLoader fxmlLoader= new FXMLLoader(getClass().getResource("/res/fxml/studentBooksView.fxml"));
        mainBorderPane.getChildren().remove(mainBorderPane.getCenter());
        try
        {
            mainBorderPane.setCenter(fxmlLoader.load());
        }
        catch (IOException e){e.printStackTrace();}

    }
    public void handleLogOut(ActionEvent actionEvent) throws IOException {
        HandleLogOut.logOut(getClass(), btnLogOut);
    }
    public void handleStudentHome(ActionEvent actionEvent) throws SQLException{
        StudentRepository.getInstance();
        FXMLLoader fxmlLoader= new FXMLLoader(getClass().getResource("/res/fxml/studentHomeView.fxml"));
        mainBorderPane.getChildren().remove(mainBorderPane.getCenter());
        try
        {
            mainBorderPane.setCenter(fxmlLoader.load());
        }
        catch (IOException e){e.printStackTrace();}

    }

}
