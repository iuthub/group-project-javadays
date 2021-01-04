DROP TABLE Users;

CREATE TABLE Users(
    UserID    VARCHAR(8)  NOT NULL,
    Password  VARCHAR(30) NOT NULL,
    FirstName VARCHAR(30) NOT NULL,
    LastName  VARCHAR(30) NOT NULL,
    Role      INT         NOT NULL,

    PRIMARY KEY(UserID)
);

-- Admins
INSERT INTO Users(UserID, Password, FirstName, LastName, Role) VALUES ('U1410000', 'admin', 'Foo', 'Fighters', 0);
INSERT INTO Users(UserID, Password, FirstName, LastName, Role) VALUES ('U1410001', 'admin', 'Black', 'Sabbath', 0);

-- Librarians
INSERT INTO Users(UserID, Password, FirstName, LastName, Role) VALUES ('U1510000', 'librarian', 'Led', 'Zeppelin', 1);
INSERT INTO Users(UserID, Password, FirstName, LastName, Role) VALUES ('U1510001', 'librarian', 'Judas', 'Priest', 1);

-- Students
INSERT INTO Users (UserID, Password, FirstName, LastName, Role) VALUES ('U1910223', '1234', 'Mukhammadsaid', 'Mamasaidov', 2);
INSERT INTO Users (UserID, Password, FirstName, LastName, Role) VALUES ('U1910236', '1234', 'Jasur', 'Yusupov', 2);