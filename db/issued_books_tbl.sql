-- DROP TABLE IssuedBooks;

CREATE TABLE IssuedBooks (
     BookID          INT  NOT NULL REFERENCES Books(BookID),
     UserID          VARCHAR(8)  NOT NULL REFERENCES Users(UserID),
     IssueDate       DATE NOT NULL,
     ReturnDate      DATE,

     PRIMARY KEY (BookID,UserID)
);

