package model;

import java.sql.Date;

public class BookStudentView {
        private String ISBN;
    private String Title;
    private String Subject;
    private String Author;
    private Date PublishDate;
    private String BorrowedStatus;

    public BookStudentView(){}

    public BookStudentView( String isbn, String title, String subject, String author, Date publishData, String borrowedStatus) {

        setISBN(isbn);
        setTitle(title);
        setSubject(subject);
        setAuthor(author);
        setPublishDate(publishData);
        setBorrowedStatus(borrowedStatus);
    }


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

    public void setBorrowedStatus(String borrowedStatus) { this.BorrowedStatus = borrowedStatus; }

    public String getBorrowedStatus() { return BorrowedStatus; }
}