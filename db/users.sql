DROP TABLE Users;

CREATE TABLE Users (
       userId      VARCHAR(8)  NOT NULL,
       password    VARCHAR(20) NOT NULL,
       firstName   VARCHAR(40) NOT NULL,
       lastName    VARCHAR(40) NOT NULL,
       role        INT         NOT NULL,
       PRIMARY KEY (userId)
);

-- Admins
INSERT INTO Users(userId, password, firstName, lastName, role) VALUES ('U1410000', 'admin', 'Foo', 'Fighters', 0);
INSERT INTO Users(userId, password, firstName, lastName, role) VALUES ('U1410001', 'admin', 'Black', 'Sabbath', 0);

-- Librarians
INSERT INTO Users(userId, password, firstName, lastName, role) VALUES ('U1510000', 'librarian', 'Led', 'Zeppelin', 1);
INSERT INTO Users(userId, password, firstName, lastName, role) VALUES ('U1510001', 'librarian', 'Judas', 'Priest', 1);

-- Students
INSERT INTO Users(userId, password, firstName, lastName, role) VALUES ('U1910000', 'student', 'Pink', 'Floyd', 2);
INSERT INTO Users(userId, password, firstName, lastName, role) VALUES ('U1910001', 'student', 'Deep', 'Purple', 2);
