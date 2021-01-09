package controllers;

import dao.BooksRepository;
import dao.ConnectionManager;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Book;
import model.BookStudentView;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Comparator;

public class StudentBooksController {

    public ChoiceBox<String> filterBooks;
    public AnchorPane studentMainAnchorPane;
    public Button btnReserve;
    public Label labelDescription;

    @FXML
    private TableView<BookStudentView> studentTblBooksTable;
    private ObservableList<BookStudentView> Books;

    @FXML
    public void initialize() throws SQLException {
        populateStudentBookView("suka");
//        filterBooks();
        //controlClick();
    }

    public void filterBooks() throws SQLException {
        String filterChoice;
        String[] ch = {"ISBN", "Title","Author","Subject","Publish date","Borrowed status"};
        if(filterBooks != null) {

            filterBooks.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {

                // if the item of the list is changed
                public void changed(ObservableValue ov, Number value, Number new_value)
                {

                    // set the text for the label to the selected item
                    System.out.println((ch[new_value.intValue()] + " selected"));
                }
            });
//
//

//            System.out.println(filterChoice);
        }
    }

    public void getDescription(String ISBN) throws SQLException {
        labelDescription.setText(BooksRepository.getInstance().getDescriptionByISBN(ISBN));
    }

    public void populateStudentBookView(String filterChoice) throws SQLException {

        Books = BooksRepository.getInstance().getBookWithBorrowedSt();

//        switch (filterChoice) {
//            case "ISBN": Books.stream().sorted(Comparator.comparing(BookStudentView::getISBN));break;
//            case "Title":Books.stream().sorted(Comparator.comparing(BookStudentView::getTitle));break;
//            case "Author":Books.stream().sorted(Comparator.comparing(BookStudentView::getAuthor));break;
//            case "Borrowed status":Books.stream().sorted(Comparator.comparing(BookStudentView::getBorrowedStatus));break;
//            case "Publish date":Books.stream().sorted(Comparator.comparing(BookStudentView::getPublishDate));break;
//            case "Subject":Books.stream().sorted(Comparator.comparing(BookStudentView::getSubject));break;
//            default:Books.stream().sorted(Comparator.comparing(BookStudentView::getISBN));break;
//        }
        studentTblBooksTable.setItems(Books);
//          controlClick();
    }

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



}
