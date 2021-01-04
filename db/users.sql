DROP TABLE Users;

CREATE TABLE Users(
    UserID    VARCHAR(8)  NOT NULL,
    Password  VARCHAR(30) NOT NULL,
    FirstName VARCHAR(30) NOT NULL,
    LastName  VARCHAR(30) NOT NULL,
    Role      INT         NOT NULL
);

INSERT INTO Users (UserID, Password, FirstName, LastName, Role)
VALUES ('U1910223', '1234', 'Mukhammadsaid', 'Mamasaidov', 0),
       ('U1910236', '1234', 'Jasur', 'Yusupov', 0);