-- UNTESTED --

DROP TABLE Books;

CREATE TABLE Books (
   ID          INT             NOT NULL,
   ISBN        VARCHAR(20)     NOT NULL,
   Title       VARCHAR(50)     NOT NULL,
   Subject     VARCHAR(100)    NOT NULL,
   Author      VARCHAR(100)    NOT NULL,
   PublishDate DATE            NOT NULL,

   PRIMARY KEY(ID)
);

-- UNTESTED --
