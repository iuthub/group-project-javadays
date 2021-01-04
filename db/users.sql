DROP TABLE Users;

CREATE TABLE Users(
    UserID    VARCHAR(8)  NOT NULL,
    Password  VARCHAR(30) NOT NULL,
    FirstName VARCHAR(30) NOT NULL,
    LastName  VARCHAR(30) NOT NULL,
    Role      INT         NOT NULL,

    PRIMARY KEY (UserID)
);

INSERT INTO Users (UserID, Password, FirstName, LastName, Role)
VALUES ('U1910223', '1234', 'Mukhammadsaid', 'Mamasaidov', 0),
       ('U1910236', '1234', 'Jasur', 'Yusupov', 0),
       ('U0000000', '0000', 'admin', 'admin', 0),
       ('U1111111', '1111', 'lib', 'lib', 1),
       ('U1234567', '1234', 'Marat', 'Shpagat', 2);