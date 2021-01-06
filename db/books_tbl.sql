DROP TABLE Books;

CREATE TABLE Books (
   BookID      INT             NOT NULL GENERATED ALWAYS AS IDENTITY,
   ISBN        VARCHAR(20)     NOT NULL,
   Title       VARCHAR(60)     NOT NULL,
   Subject     VARCHAR(100)    NOT NULL,
   Author      VARCHAR(100)    NOT NULL,
   PublishDate DATE            NOT NULL,
   Description VARCHAR(255),

   PRIMARY KEY(BookID)
);

INSERT INTO Books(BookID, ISBN, Title, Subject, Author, PublishDate)
VALUES
(001, '1234qwerty1', '12345qwerty1', '1234qwerty1', '1234qwerty1', '01.01.2020'),
(002, '1234qwerty2', '12345qwerty2', '1234qwerty2', '1234qwerty2', '01.01.2020'),
(003, '1234qwerty3', '12345qwerty3', '1234qwerty3', '1234qwerty3', '01.01.2020'),
(004, '1234qwerty4', '12345qwerty4', '1234qwerty4', '1234qwerty4', '01.01.2020'),
(005, '1234qwerty5', '12345qwerty5', '1234qwerty5', '1234qwerty5', '01.01.2020'),
(006, '1234qwerty6', '12345qwerty6', '1234qwerty6', '1234qwerty6', '01.01.2020');
