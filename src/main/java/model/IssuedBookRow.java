package model;

import java.sql.Date;

// class for adding rows of issued books in student's information
public class IssuedBookRow {

    private String ISBN;
    private String title;
    private String author;
    private Date issuedDate;

    public IssuedBookRow() {}

    public IssuedBookRow(String ISBN, String title, String author, Date issuedDate) {
        this.ISBN = ISBN;
        this.title = title;
        this.author = author;
        this.issuedDate = issuedDate;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getIssuedDate() {
        return issuedDate;
    }

    public void setIssuedDate(Date issuedDate) {
        this.issuedDate = issuedDate;
    }
}