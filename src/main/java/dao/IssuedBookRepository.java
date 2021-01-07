package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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

    //region <StartUp>
    private IssuedBookRepository() throws SQLException
    {
        connection = ConnectionManager.getConnection();
    }
    //endregion

    //region <Get>

    //Returns all books borowed by this user
    public ObservableList<IssuedBook> getByUser(String userId) throws SQLException
    {
        ResultSet result;
        ObservableList<IssuedBook> issuedBooksByUser = FXCollections.observableArrayList();

        //https://stackoverflow.com/a/10019205/10304482
        String getIssuedBooksByUser = String.format("Select * From IssuedBooks Where userId = '%s'",userId);

        PreparedStatement getIssuedBooksByUserStat = connection.prepareStatement(getIssuedBooksByUser);

        result = getIssuedBooksByUserStat.executeQuery();

        while (result.next())
        {
            issuedBooksByUser.add(new IssuedBook(
                    result.getInt("BookId"),
                    result.getString("UserId"),
                    result.getDate("IssueDate"),
                    result.getDate("ReturnDate")
                    ));
        }

        return issuedBooksByUser;
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
