-- DROP TABLE IssuedBooks;

CREATE TABLE IssuedBooks (
   bookId          INT  NOT NULL REFERENCES Books(id),
   userId          INT  NOT NULL REFERENCES Users(id),
   issuedDate      DATE NOT NULL,
   returnedDate    DATE ,
   PRIMARY KEY (bookId,userId)
);

INSERT INTO IssuedBooks(bookId, userId, issuedDate, returnedDate)
VALUES
('0001', 'U1910000', '01.01.2020', ''),
('0001', 'U1910000', '01.01.2020', ''),
('0002', 'U1910000', '01.01.2020', ''),
('0002', 'U1910000', '01.01.2020', ''),
('0003', 'U1910001', '01.01.2020', ''),
('0003', 'U1910001', '01.01.2020', ''),
('0004', 'U1910001', '01.01.2020', ''),
('0004', 'U1910001', '01.01.2020', ''),
