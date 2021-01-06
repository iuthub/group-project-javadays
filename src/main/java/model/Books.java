package model;

import java.time.chrono.IsoChronology;
import java.util.Date;

public class Books
{
    private String bookId;
    private String ISBN;
    private String title;
    private String subject;
    private String author;
    private Date publishedDate;

    public void SetBookId(String bookId){this.bookId = bookId;}
    public String GetBookId(){return bookId;}

    public void SetISBN(String ISBN){this.ISBN = ISBN;}
    public String GetISBN(){return ISBN;}

    public void SetTitle(String title){this.title = title;}
    public String GetTitle(){return title;}

    public void SetSubject(String subject){this.subject = subject;}
    public String GetSubject(){return subject;}

    public void SetAuthor(String author){this.author = author;}
    public String GetAuthor(){return author;}

    public void SetPublishedDate(Date publishedDate){this.publishedDate = publishedDate;}
    public Date GetPublishedDate(){return publishedDate;}
}
