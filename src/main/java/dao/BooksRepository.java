package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Book;
import model.BookStudentView;


import javax.xml.transform.Result;
import java.sql.*;

public class BooksRepository {
    private static BooksRepository instance;

    private final PreparedStatement createStmt;
    private final PreparedStatement getAllStmt;
    private final PreparedStatement updateStmt;
    private final PreparedStatement getById;
    private final PreparedStatement getCountStmt;
    private final PreparedStatement deleteStmt;

    private final PreparedStatement getAllByISBTStmt;
    private final PreparedStatement getCopiesNumByISBTStmt;

    private BooksRepository() throws SQLException {
        String GET_ALL =   "SELECT * FROM Books";
        String GET_BY_ID = "SELECT * FROM Books WHERE bookID= ?";
        String COUNT = "SELECT COUNT(*) FROM Books";
        String CREATE_QUERY = "INSERT INTO Books(BookID, ISBN, Title, Subject, Author, PublishDate) VALUES (?, '?', '?', '?', '?', '?')";
        String UPDATE_QUERY = "UPDATE Books SET BookID = ?, ISBN = ?, Title = ?, Subject = ? , Author = ?, PublishDate = ? WHERE BookID = ?";
        String GET_ALl_BY_ISBN = "SELECT * FROM Books WHERE ISBN=?";
        String DELETE_QUERY = "DELETE FROM Books WHERE BookID=?";
        String GET_NUM_COPIES="SELECT COUNT(*) FROM Books WHERE ISBN=?";


        Connection conn = ConnectionManager.getConnection();
        this.getCopiesNumByISBTStmt=conn.prepareStatement(GET_NUM_COPIES);
        this.createStmt = conn.prepareStatement(CREATE_QUERY);
        this.getAllStmt = conn.prepareStatement(GET_ALL);
        this.updateStmt = conn.prepareStatement(UPDATE_QUERY);
        this.getById    = conn.prepareStatement(GET_BY_ID);
        this.getAllByISBTStmt = conn.prepareStatement(GET_ALl_BY_ISBN);
        this.getCountStmt = conn.prepareStatement(COUNT);
        this.deleteStmt = conn.prepareStatement(DELETE_QUERY);

    }

    public static BooksRepository getInstance() throws SQLException {
        if (instance == null) {
            instance = new BooksRepository();
        }
        return instance;
    }

    public ObservableList<Book> getAll() throws SQLException {
        ResultSet rs = getAllStmt.executeQuery();
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
        String availability="Available";
        int numberOfUnavailable=0;
        int numberOfCopies=0;
        ResultSet rs;
        ObservableList<BookStudentView> list = FXCollections.observableArrayList();
        ResultSet result = this.getAllStmt.executeQuery();
        ResultSet allBooksWithSameISBN;
        ObservableList<dao.IssuedBook> allIssuedBooks=IssuedBookRepository.getInstance().getAllIssued();
        while (result.next()){
            getAllByISBTStmt.setString(1, result.getString("ISBN"));
            allBooksWithSameISBN=getAllByISBTStmt.executeQuery();
            while(allBooksWithSameISBN.next())
            {
//                if(!allIssuedBooks.contains(allBooksWithSameISBN.getString("BookID")))
//                {  availability="Available";}
                for(int i=0;i<allIssuedBooks.size();i++)
                {
                    if(allBooksWithSameISBN.getInt("BookID")==allIssuedBooks.get(i).getIssueBookId())
                    {
                        numberOfUnavailable++;
                    }
                }
            }
            getCopiesNumByISBTStmt.setString(1, result.getString("ISBN"));
            rs=getCopiesNumByISBTStmt.executeQuery();
            if(rs.next()){
                numberOfCopies=rs.getInt(1);
            }
            if(numberOfUnavailable==numberOfCopies){availability="Unavailable";}
            list.add(
                    new BookStudentView(
                            result.getString("ISBN"),
                            result.getString("Title"),
                            result.getString("Author"),
                            result.getString("Subject"),
                            result.getDate  ("PublishDate"),
                            availability
                    )
            );
            availability="Available";
            numberOfUnavailable=0;
        }
        return list;
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
        getAllByISBTStmt.setString(1, ISBN);
        ResultSet result = getAllByISBTStmt.executeQuery();

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

    public void deleteBook(int bookId) throws SQLException {
        deleteStmt.setInt(1, bookId);
        deleteStmt.executeUpdate();
    }
}