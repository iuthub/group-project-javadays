-- UNTESTED --

-- DROP TABLE IssuedBooks;

CREATE TABLE IssuedBooks (
     BookID          INT  NOT NULL REFERENCES Books(BookID),
     UserID          VARCHAR(8)  NOT NULL REFERENCES Users(UserID),
     IssueDate       DATE NOT NULL,
     ReturnDate      DATE,

     PRIMARY KEY (BookID,UserID)
);

INSERT INTO IssuedBooks(BookID, UserID, IssueDate, ReturnDate)
VALUES
(1, 'U1000003', '01.01.2020', NULL),
(2, 'U1000003', '01.01.2020', NULL),
(3, 'U1000003', '01.01.2020', NULL),
(4, 'U1000003', '01.01.2020', NULL),
(5, 'U1000008', '01.01.2020', NULL),
(6, 'U1000008', '01.01.2020', NULL),
(7, 'U1000008', '01.01.2020', NULL),
(8, 'U1000008', '01.01.2020', NULL);
