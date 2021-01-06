-- UNTESTED --

-- DROP TABLE IssuedBooks;

CREATE TABLE IssuedBooks (
     BookID          INT  NOT NULL REFERENCES Books(BookID),
     UserID          VARCHAR(8)  NOT NULL REFERENCES Users(UserID),
     IssueDate       DATE NOT NULL,
     ReturnDate      DATE,

     PRIMARY KEY (BookID,UserID)
);

/*
INSERT INTO IssuedBooks(BookID, UserID, IssueDate, ReturnDate)
VALUES
('0001', 'U1910223', '01.01.2020', ''),
('0001', 'U1910223', '01.01.2020', ''),
('0002', 'U1910223', '01.01.2020', ''),
('0002', 'U1910223', '01.01.2020', ''),
('0003', 'U1910001', '01.01.2020', ''),
('0003', 'U1910001', '01.01.2020', ''),
('0004', 'U1910001', '01.01.2020', ''),
('0004', 'U1910001', '01.01.2020', '');
*/
-- UNTESTED --
