DROP TABLE Users;

CREATE TABLE Users(
    UserID VARCHAR(30) NOT NULL,
    Password VARCHAR(30) NOT NULL,
    FirstName VARCHAR(30) NOT NULL,
    LastName VARCHAR(30) NOT NULL,
    Role INT NOT NULL

    PRIMARY KEY(UserID)
);

INSERT INTO Users (
    Login,
    Password,
    FirstName,
    LastName,
    Role
)
VALUES
    (
        'U1910223',
        '1182307',
        'Mukhammadsaid',
        'Mamasaidov',
        0
    ),
    (
        'U1910236',
        '1182307',
        'Jasur',
        'Yusupov',
        0
    );