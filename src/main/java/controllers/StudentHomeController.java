package controllers;
import dao.*;
import dao.IssuedBook;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import model.Book;
import model.BookStudentView;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Comparator;

import static java.lang.System.currentTimeMillis;

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
    private ChoiceBox<String> filterBooks;
    @FXML
    private TableView<BookStudentView> studentTblBooksTable;
    @FXML
    private Label labelFineStudent;
    @FXML
    private Label labelStatusStudent;
    @FXML
    private Label labelDescription;
    @FXML
    private Button btnReserve;
    private static String userID;
    private BooksRepository booksRepository;
    private IssuedBookRepository issuedBookRepository;
    ObservableList<BookStudentView> Books;
    private final Connection connection;
    public StudentHomeController() throws SQLException {
        connection = ConnectionManager.getConnection();
        booksRepository=BooksRepository.getInstance();
        userID=LoginController.getUserID();
    }
    public void initialize() throws SQLException {
        setInfo();
        //setIssuedBooksByStudent();
        //setCurrentFine();
        //filterBooks();
        //controlClick();

    }
//    public void filterBooks() throws SQLException {
//        String filterChoice;
//        String[] ch = {"ISBN","Title","Author","Subject","Publish date","Borrowed status"};
//        if(filterBooks!=null)
//        {
//            filterBooks.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
//
//                @Override
//                public void changed(ObservableValue ov, Number value, Number new_value)
//                {
//                    filterChoice = ch[new_value.intValue()];
//                }
//            });
//            //filterChoice=filterBooks.getValue();
//            populateStudentBookView(filterChoice);
//        }
//    }
//    public void getDescription(String ISBN) throws SQLException
//    {
//        ResultSet result;
//        PreparedStatement preparedStatement;
//        preparedStatement=connection.prepareStatement("SELECT * FROM Books WHERE ISBN=?");
//        preparedStatement.setString(1,ISBN);
//        result=preparedStatement.executeQuery();
//        labelDescription.setText(result.getString(7));
//    }
//    public void controlClick() throws SQLException {
//
//        studentTblBooksTable.setRowFactory(tv -> {
//            TableRow<BookStudentView> row = new TableRow<>();
//            row.setOnMouseClicked(event -> {
//                if (event.getClickCount() == 2 && (!row.isEmpty())) {
//                    BookStudentView chooseBook = row.getItem();
//                    try {
//                        getDescription(chooseBook.getISBN());
//                    } catch (SQLException throwables) {
//                        throwables.printStackTrace();
//                    }
//                    if(chooseBook.getBorrowedStatus()=="Available")
//                    {
//                        btnReserve.setDisable(false);
//                        // reserveBook(Student student);
//                        //reserve option is not ready
//                    }
//                }
//            });
//            return row;
//        });
//    }
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

//    public void populateStudentBookView(String filterChoice) throws SQLException
//    {
//
//        Books=booksRepository.getBookWithBorrowedSt();
//        switch (filterChoice) {
//            case "ISBN": Books.stream().sorted(Comparator.comparing(BookStudentView::getISBN));break;
//            case "Title":Books.stream().sorted(Comparator.comparing(BookStudentView::getTitle));break;
//            case "Author":Books.stream().sorted(Comparator.comparing(BookStudentView::getAuthor));break;
//            case "Borrowed status":Books.stream().sorted(Comparator.comparing(BookStudentView::getBorrowedStatus));break;
//            case "Publish date":Books.stream().sorted(Comparator.comparing(BookStudentView::getPublishDate));break;
//            case "Subject":Books.stream().sorted(Comparator.comparing(BookStudentView::getSubject));break;
//            default:Books.stream().sorted(Comparator.comparing(BookStudentView::getISBN));break;
//        }
//        studentTblBooksTable.setItems(Books);
//          controlClick();
//    }


//    public void setIssuedBooksByStudent() throws SQLException {
//        ObservableList<issuedBooksRow> issuedBooksRows= FXCollections.observableArrayList();
//        ObservableList<IssuedBook> issuedBooks= FXCollections.observableArrayList();
//            int issuedBookId;
//            ResultSet result;
//            PreparedStatement preparedStatement;
//            ResultSet resultForID;
//            PreparedStatement preparedStatementForID;
//            issuedBooks = issuedBookRepository.getByUser(userID);
//            for (int i = 0; i < issuedBooks.size(); i++) {
//
//                issuedBookId = issuedBooks.get(i).getIssueBookId();
//                //taking data from Books database
//                preparedStatement = connection.prepareStatement("Select * FROM Books Where BookID = ?");
//                preparedStatement.setInt(1, issuedBookId);
//                result = preparedStatement.executeQuery();
//                if(result.next()) {
//                    //taking data from IssuedBooks database
//                    preparedStatementForID = connection.prepareStatement("Select * FROM IssuedBooks Where BookID = ?");
//                    preparedStatementForID.setInt(1, issuedBookId);
//                    resultForID = preparedStatementForID.executeQuery();
//                    //setting them to columns
//                    if(resultForID.next()) {
//                        issuedBooksRows.add(new issuedBooksRow(result.getString(2), result.getString(3), result.getString(5), resultForID.getDate(3)));
//                    }
//            }
//               if(studentTblIssuedBooks!=null) {
//                    studentTblIssuedBooks.setItems(issuedBooksRows);
//                }
//        }
//
//    }


//    public void setCurrentFine() throws SQLException
//    {
//        Date issuedDate;
//        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
//        LocalDateTime currentDate = LocalDateTime.now();
//        long currentFine=0;
//        int lateMonths=0;
//        int numberOfBooks=0;
//        ObservableList<IssuedBook> issuedBooks = issuedBookRepository.getByUser(userID);
//       for (int i=0;i < issuedBooks.size();i++) {
//           issuedDate = issuedBooks.get(i).getIssuedDate();
//
//           if(((currentTimeMillis()-issuedDate.getTime())/100000)>26298)
//           {
//               lateMonths=Math.round((((currentTimeMillis()-issuedDate.getTime())/100000)-26298)/(26298*100000));
//                currentFine+=lateMonths*5;
//
//           }
//
//       }
//       if(currentFine!=0){
//           if(labelFineStudent!=null&&labelStatusStudent!=null)
//           {
//               labelFineStudent.setText(currentFine+"$ for late return of "+numberOfBooks+" books (5$ for each month)!");
//               labelStatusStudent.setText("BANNED");
//           }
//       }
//
//    }


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
// class for adding rows of issued books in student's information
class issuedBooksRow
{

    private String ISBN;
    private String Title;
    private String Author;
    private Date IssuedDate;

    public issuedBooksRow(){}

    public issuedBooksRow(String isbn, String title, String author, Date issuedDate) {

        setISBN(isbn);
        setTitle(title);
        setAuthor(author);
        setIssuedDate(issuedDate);
    }

    public String getISBN() { return ISBN; }

    public void setISBN(String ISBN) { this.ISBN = ISBN; }

    public String getTitle() { return Title; }

    public void setTitle(String title) { this.Title = title; }

    public String getAuthor() { return Author; }

    public void setAuthor(String author) { this.Author = author; }

    public Date getIssuedDate() { return IssuedDate; }

    public void setIssuedDate(Date issuedDate) { this.IssuedDate = issuedDate; }

}