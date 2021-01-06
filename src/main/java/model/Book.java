package model;

import java.sql.Date;

public class Book {
    private int id;
    private String isbn;
    private String title;
    private String subject;
    private String author;
    private Date publishData;

    public Book(int id, String isbn, String title, String subject, String author, Date publishData) {
        setId(id);
        setIsbn(isbn);
        setTitle(title);
        setSubject(subject);
        setAuthor(author);
        setPublishData(publishData);
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getIsbn() { return isbn; }

    public void setIsbn(String isbn) { this.isbn = isbn; }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public String getSubject() { return subject; }

    public void setSubject(String subject) { this.subject = subject; }

    public String getAuthor() { return author; }

    public void setAuthor(String author) { this.author = author; }

    public Date getPublishData() { return publishData; }

    public void setPublishData(Date publishData) { this.publishData = publishData; }
}