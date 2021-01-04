-- DROP TABLE Books;

CREATE TABLE Books (
   id          INT             NOT NULL GENERATED ALWAYS AS IDENTITY,
   isbn        VARCHAR(20)     NOT NULL,
   title       VARCHAR(50)     NOT NULL,
   subject     VARCHAR(100)    NOT NULL,
   author      VARCHAR(100)    NOT NULL,
   publishDate DATE            NOT NULL,
   description VARCHAR(255)    NOT NULL
);

INSERT INTO Books(isbn, title, subject, author, publishDate, description)
VALUES
('0001', 'test-title-1', 'test-subject-1', 'test-author-1', '01.01.2020', ''),
('0001', 'test-title-1', 'test-subject-1', 'test-author-1', '01.01.2020', ''),
('0002', 'test-title-2', 'test-subject-2', 'test-author-1', '01.01.2010', ''),
('0002', 'test-title-2', 'test-subject-2', 'test-author-1', '01.01.2010', ''),
('0003', 'test-title-3', 'test-subject-3', 'test-author-1', '01.01.2000', ''),
('0003', 'test-title-3', 'test-subject-3', 'test-author-1', '01.01.2000', ''),
('0004', 'test-title-4', 'test-subject-4', 'test-author-1', '01.01.2008', ''),
('0004', 'test-title-4', 'test-subject-4', 'test-author-1', '01.01.2008', ''),
('0004', 'test-title-4', 'test-subject-4', 'test-author-1', '01.01.2008', ''),
('0004', 'test-title-4', 'test-subject-4', 'test-author-1', '01.01.2008', ''),
('0004', 'test-title-4', 'test-subject-4', 'test-author-1', '01.01.2008', '')
