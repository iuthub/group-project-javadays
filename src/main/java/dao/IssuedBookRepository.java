package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class IssuedBookRepository
{
    //region <Declarations>
    private static IssuedBookRepository instance;

    private final Connection connection;
    //endregion

    //region <StartUp>
    private IssuedBookRepository() throws SQLException
    {
        connection = ConnectionManager.getConnection();
    }
    //endregion

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
    public void removeIssuedBook(int bookId,String userId) throws SQLException {
        PreparedStatement addIssuedBookStat =
                connection.prepareStatement("Delete From IssuedBooks Where BookId = ? And UserId = ? ");

        addIssuedBookStat.setInt(1,bookId);
        addIssuedBookStat.setString(2,userId);

        addIssuedBookStat.execute();
    }
    //endregion


    public void calculateDifference(int bookId, String userId) throws SQLException {
        PreparedStatement calculateDifferenceStmt = connection.prepareStatement("SELECT {fn TIMESTAMPDIFF(SQL_TSI_DAY, IssueDate, ReturnDate)} AS difference FROM IssuedBooks ");

        ResultSet result = calculateDifferenceStmt.executeQuery();

        if (result.next()) {
            System.out.println(result.getInt("difference"));
        }



    }


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
