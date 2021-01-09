package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Book;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class IssuedBookRepository
{
    //region <Declarations>
    private static IssuedBookRepository instance;

    private final Connection connection;
    //endregion
    private final PreparedStatement getAll;
    //region <StartUp>
    private IssuedBookRepository() throws SQLException
    {
        String GET_ALL_ISSUED="SELECT * FROM IssuedBooks";
        connection = ConnectionManager.getConnection();
        this.getAll=connection.prepareStatement(GET_ALL_ISSUED);
    }
    //endregion
    public ObservableList<dao.IssuedBook> getAllIssued() throws SQLException {
        ResultSet rs = getAll.executeQuery();
        ObservableList<dao.IssuedBook> results = FXCollections.observableArrayList();

        while (rs.next()) {
            results.add(new dao.IssuedBook(
                            rs.getInt   (1),
                            rs.getString(2),
                            rs.getDate(3),
                            rs.getDate(4)
                    )
            );
        }
        return results;
    }
    //region <Get>

    //Returns all books borowed by this user
    public ObservableList<dao.IssuedBook> getByUser(String userId) throws SQLException
    {
        ResultSet result;
        ObservableList<dao.IssuedBook> issuedBooksByUser = FXCollections.observableArrayList();

        //https://stackoverflow.com/a/10019205/10304482
        String getIssuedBooksByUser = String.format("Select * From IssuedBooks Where userId = '%s'",userId);

        PreparedStatement getIssuedBooksByUserStat = connection.prepareStatement(getIssuedBooksByUser);

        result = getIssuedBooksByUserStat.executeQuery();

        while (result.next())
        {
            issuedBooksByUser.add(new dao.IssuedBook(
                    result.getInt("BookId"),
                    result.getString("UserId"),
                    result.getDate("IssueDate"),
                    result.getDate("ReturnDate")
                    ));
        }

        return issuedBooksByUser;
    }
    //endregion

    //region <Add>
    public void addIssuedBook(dao.IssuedBook issuedBook) throws SQLException
    {
        PreparedStatement addIssuedBookStat =
                connection.prepareStatement("INSERT INTO IssuedBooks(BookID, UserID, IssueDate, ReturnDate) " +
                        "Values (?,?,?,?)");

        addIssuedBookStat.setInt(1,issuedBook.getIssueBookId());
        addIssuedBookStat.setString(2,issuedBook.getIssuedBookUserId());
        addIssuedBookStat.setDate(3,issuedBook.getIssuedDate());
        addIssuedBookStat.setDate(4,issuedBook.getReturnDate());

        addIssuedBookStat.execute();
    }
    //endregion

    //region <Delete>
    public void removeIssuedBook(int bookId,String userId) throws SQLException
    {
        PreparedStatement addIssuedBookStat =
                connection.prepareStatement("Delete From IssuedBooks Where IssuedBooks.BookId = ? And IssuedBooks.UserId = ? ");

        addIssuedBookStat.setInt(1,bookId);
        addIssuedBookStat.setString(2,userId);

        addIssuedBookStat.execute();
    }
    //endregion


    //region <Utilities>
    public static IssuedBookRepository getInstance() throws SQLException
    {
        if(instance==null)
        {
            instance = new IssuedBookRepository();
        }
        return instance;
    }
    //endregion
}
