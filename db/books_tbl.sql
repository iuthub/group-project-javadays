-- UNTESTED --

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

-- UNTESTED --
