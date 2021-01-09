package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Book;
import model.BookStudentView;
import model.StudentBorrowedBooks;

import java.sql.*;

public class BooksRepository {
    private static BooksRepository instance;

    private final PreparedStatement createStmt;
    private final PreparedStatement getAllStmt;
    private final PreparedStatement updateStmt;
    private final PreparedStatement getById;
    private final PreparedStatement getUniqueISBNStmt;
    private final PreparedStatement getDescriptionByISBTStmt;

    private BooksRepository() throws SQLException {
        String GET_ALL =   "SELECT * FROM Books";
        String GET_BY_ID = "SELECT * FROM Books WHERE bookID= ?";
        String CREATE_QUERY = "INSERT INTO Books(BookID, ISBN, Title, Subject, Author, PublishDate) VALUES (?, '?', '?', '?', '?', '?')";
        String UPDATE_QUERY = "UPDATE Books SET BookID = ?, ISBN = ?, Title = ?, Subject = ? , Author = ?, PublishDate = ? WHERE BookID = ?";
        String GET_DES_BY_ISBN = "SELECT * FROM Books WHERE ISBN=?";
        String GET_UNIQUE_ISBN = "SELECT DISTINCT ISBN FROM Books";

        Connection conn = ConnectionManager.getConnection();
        this.createStmt = conn.prepareStatement(CREATE_QUERY);
        this.getAllStmt = conn.prepareStatement(GET_ALL);
        this.updateStmt = conn.prepareStatement(UPDATE_QUERY);
        this.getById    = conn.prepareStatement(GET_BY_ID);
        this.getUniqueISBNStmt = conn.prepareStatement(GET_UNIQUE_ISBN);
        this.getDescriptionByISBTStmt = conn.prepareStatement(GET_DES_BY_ISBN);
    }

    public static BooksRepository getInstance() throws SQLException {
        if (instance == null) {
            instance = new BooksRepository();
        }
        return instance;
    }

    public ObservableList<Book> getAll() throws SQLException {
        ResultSet rs = getUniqueISBNStmt.executeQuery();
        ObservableList<Book> results = FXCollections.observableArrayList();

        while (rs.next()) {
            results.add(new Book(
                    rs.getInt   ("BookID"),
                    rs.getString("ISBN"),
                    rs.getString("Title"),
                    rs.getString("Subject"),
                    rs.getString("Author"),
                    rs.getDate  ("PublishDate"),
                    rs.getString("Description")
                )
            );
        }
        return results;
    }

//    students book view with borrowed status S@iD

    public ObservableList<BookStudentView> getBookWithBorrowedSt() throws SQLException {

        ObservableList<BookStudentView> list = FXCollections.observableArrayList();
        ResultSet result = this.getAllStmt.executeQuery();

        while (result.next()){
            list.add(
                    new BookStudentView(
                            result.getString("ISBN"),
                            result.getString("Title"),
                            result.getString("Author"),
                            result.getString("Subject"),
                            result.getDate  ("PublishDate"),
                            "Availibility"
                    )
            );
        }
        return list;


//        ResultSet rs = getUniqueISBNStmt.executeQuery(); //all distinct books
//
//        ObservableList<BookStudentView> selectedBook = FXCollections.observableArrayList(); //books with Borrowed status
//
//        Connection conn = ConnectionManager.getConnection();
//        PreparedStatement copiesOfBook;
//        ResultSet copiesOfOneBook;
//
//        PreparedStatement copiesOfOneBookIssuedStm;
//        ResultSet copiesOfOneBookIssued;
//        boolean key=true;
//
//        while (rs.next()) {
//            int issuedCopies=0; // issuedCopies of one book
//            int copies=0; //copies of one book
//            //get rows with same ISBN
//            String borrowedStatus = "Available";
//            copiesOfBook=conn.prepareStatement("SELECT * FROM Books WHERE ISBN=?");
//            copiesOfBook.setString(1, rs.getString(2));
//            copiesOfOneBook=copiesOfBook.executeQuery();
//
//
////            while(copiesOfOneBook.next()) //to identify borrowed status of book
////            {
////                copiesOfOneBookIssuedStm=conn.prepareStatement("SELECT UserID FROM IssuedBooks WHERE BookID=?");
////                copiesOfOneBookIssuedStm.setInt(1,copiesOfOneBook.getInt(1));
////                copiesOfOneBookIssued=copiesOfOneBookIssuedStm.executeQuery();
////                if(copiesOfOneBookIssued!=null)
////                {
////                    issuedCopies++;
////                }
////
////            }
//            copiesOfBook=conn.prepareStatement("SELECT COUNT(*) FROM Books WHERE ISBN=?");
//            copies=copiesOfBook.executeUpdate();
//
//            if(copies==issuedCopies){borrowedStatus="Unavailable";}
//            selectedBook.add(new BookStudentView(
//                            rs.getString("ISBN"),
//                            rs.getString("Title"),
//                            rs.getString("Author"),
//                            rs.getString("Subject"),
//                            rs.getDate  ("PublishDate"),
//                            borrowedStatus
//                    )
//            );
//        }
//
//        return selectedBook;
    }

    public Book getById(int bookId) throws SQLException {
        Book selectedBook = null;

        getById.setInt(1, bookId);
        ResultSet result = getById.executeQuery();

        if(result.next()) {
            selectedBook = new Book(
                    result.getInt   ("BookId"),
                    result.getString("ISBN"),
                    result.getString("Title"),
                    result.getString("Subject"),
                    result.getString("Author"),
                    result.getDate  ("PublishDate"),
                    result.getString("Description")
            );
        }

        return selectedBook;
    }

    public String getDescriptionByISBN(String ISBN) throws SQLException {
        Book selectedBook = null;
        getDescriptionByISBTStmt.setString(1, ISBN);
        ResultSet result = getDescriptionByISBTStmt.executeQuery();

        if (result.next()) {
            return result.getString("Description");
        }

        return null;
    }

    public void createBook(int bookId, String isbn, String title, String subject, String author, Date date) throws SQLException{
        createStmt.setInt   (1, bookId);
        createStmt.setString(2, isbn);
        createStmt.setString(3, title);
        createStmt.setString(4, subject);
        createStmt.setString(5, author);
        createStmt.setDate  (6, date);

        createStmt.executeUpdate();
    }

    public void updateBook(int oldBookId, int newBookId, String isbn, String title, String subject, String author, Date date) throws SQLException{
        updateStmt.setInt   (1, newBookId);
        updateStmt.setString(2, isbn);
        updateStmt.setString(3, title);
        updateStmt.setString(4, subject);
        updateStmt.setString(5, author);
        updateStmt.setDate  (6, date);
        updateStmt.setInt   (7, oldBookId);

        updateStmt.executeUpdate();
    }
}