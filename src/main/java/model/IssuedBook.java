package dao;

import java.sql.Date;

public class IssuedBook
{
    private int BookId;
    private String UserId;
    private Date IssuedDate;
    private Date ReturnDate;

    public IssuedBook(int bookId,String userId, Date issuedDate,Date returnDate)
    {
        setIssueBookId(bookId);
        setIssueBookUserId(userId);
        setIssuedDate(issuedDate);
        setReturnDate(returnDate);
    }

    public void setIssueBookId(int bookId){this.BookId = bookId;}
    public int getIssueBookId(){return BookId;}

    public void setIssueBookUserId(String userId){this.UserId = userId;}
    public String getIssuedBookUserId(){return  UserId;}

    public void setIssuedDate(Date issuedDate){this.IssuedDate = issuedDate;}
    public Date getIssuedDate(){return  IssuedDate;}

    public void setReturnDate(Date returnDate){this.ReturnDate = returnDate;}
    public Date getReturnDate(){return ReturnDate;}
}
