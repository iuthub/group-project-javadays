package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Book;
import model.Student;

import java.sql.*;
import java.time.Duration;
import java.time.*;
import java.time.format.DateTimeFormatter;

import static java.time.temporal.ChronoUnit.DAYS;

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

    //region <Get>

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

    public int getCount(String userId) throws SQLException{
        PreparedStatement getTotalCount = connection.prepareStatement("SELECT COUNT(*) FROM IssuedBooks WHERE UserID = ?");
        getTotalCount.setString(1, userId);
        ResultSet rs = getTotalCount.executeQuery();
        if (rs.next()){
            return rs.getInt(1);
        }
        return 0;
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

    //region <Utilities>
    public static IssuedBookRepository getInstance() throws SQLException
    {
        if(instance==null)
        {
            instance = new IssuedBookRepository();
        }
        return instance;
    }

    public int calculateFine(int bookId, String userId) throws SQLException {
        PreparedStatement calculateDifferenceStmt = connection.prepareStatement("SELECT * FROM IssuedBooks WHERE BookId=? AND UserId=?");


        calculateDifferenceStmt.setInt(1, bookId);
        calculateDifferenceStmt.setString(2, userId);

        ResultSet result = calculateDifferenceStmt.executeQuery();

        if (result.next()) {

            LocalDate returnDate = LocalDate.parse(result.getString("returnDate"));
            LocalDate studentReturnDate = LocalDate.now();

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy"); //Setting date format

            calculateDifferenceStmt.setInt(1, bookId);
            calculateDifferenceStmt.setString(2, userId);



            long daysBetween = DAYS.between(studentReturnDate, returnDate);

            if (daysBetween < 0) {
                Student student = new Student(userId, 0, false);

                student.setFine((int) Math.abs(daysBetween) * 5);
                student.setStatus(false);

                StudentRepository.getInstance().updateStudent(student.getUserId(), student.getFine(), student.isStatus() ? 1 : 0);

                return student.getFine();
            }
        }

//        ResultSet result = calculateDifferenceStmt.executeQuery();
//
//        while (result.next()) {
//            System.out.println(result.getString("userId"));
//            System.out.println(result.getInt("difference"));
//        }

        return 0;
    }
    //endregion

}

