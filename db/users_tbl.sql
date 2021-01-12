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
INSERT INTO Users(UserID, Password, FirstName, LastName, Role) VALUES
    ('U1400000', '1234', 'Jasur', 'Yusupov', 0),
    ('U1400001', '1234', 'Mukhammadsaid', 'Mamasaidov', 0);

-- Librarians
INSERT INTO Users(UserID, Password, FirstName, LastName, Role) VALUES
    ('U1530000', '1234', 'Bekzod', 'Allaev', 1),
    ('U1530001', '1234', 'Asadbek', 'Khasanov', 1);

-- Students
INSERT INTO Users (UserID, Password, FirstName, LastName, Role) VALUES
    ('U1910000', '1234', 'Saidakbar', 'Saydakhmedov', 2);

INSERT INTO Users (UserID, Password, FirstName, LastName, Role) VALUES
    ('U1910223', '1234', 'Mukhammadsaid', 'Mamasaidov', 2),
    ('U1910236', '1234', 'Jasur', 'Yusupov', 2),
    ('U1910103', '1234', 'Asadbek', 'Khasanov', 2),
    ('U1910222', '1234', 'Saidakbar', 'Saydakhmedov', 2),
    ('U1910240', '1234', 'Bekzod', 'Allaev', 2);