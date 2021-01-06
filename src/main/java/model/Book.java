package model;

import java.sql.Date;

public class Book {
    private int BookID;
    private String ISBN;
    private String Title;
    private String Subject;
    private String Author;
    private Date PublishDate;

    public Book(int id, String isbn, String title, String subject, String author, Date publishData) {
        setBookID(id);
        setISBN(isbn);
        setTitle(title);
        setSubject(subject);
        setAuthor(author);
        setPublishDate(publishData);
    }

    public int getBookID() { return BookID; }

    public void setBookID(int id) { this.BookID = id; }

    public String getISBN() { return ISBN; }

    public void setISBN(String ISBN) { this.ISBN = ISBN; }

    public String getTitle() { return Title; }

    public void setTitle(String title) { this.Title = title; }

    public String getSubject() { return Subject; }

    public void setSubject(String subject) { this.Subject = subject; }

    public String getAuthor() { return Author; }

    public void setAuthor(String author) { this.Author = author; }

    public Date getPublishDate() { return PublishDate; }

    public void setPublishDate(Date publishDate) { this.PublishDate = publishDate; }
}