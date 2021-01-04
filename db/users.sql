DROP TABLE Users;

CREATE TABLE Users (
       userId      VARCHAR(12) NOT NULL ALWAYS AS IDENTITY,
       password    VARCHAR(10) NOT NULL,
       firstName   VARCHAR(50) NOT NULL,
       lastName    VARCHAR(50) NOT NULL,
       role        INT         NOT NULL,
)

-- Students
INSERT INTO lms.Users(userId, password, firstName, lastName, role) VALUES ('U1910000', 'student', 'Pink', 'Floyd', 2)
INSERT INTO lms.Users(userId, password, firstName, lastName, role) VALUES ('U1910001', 'student', 'Deep', 'Purple', 2)

-- Admins
INSERT INTO lms.Users(userId, password, firstName, lastName, role) VALUES ('U1410000', 'admin', 'Foo', 'Fighters', 0)
INSERT INTO lms.Users(userId, password, firstName, lastName, role) VALUES ('U1410001', 'admin', 'Black', 'Sabbath', 0)

-- Librarians
INSERT INTO lms.Users(userId, password, firstName, lastName, role) VALUES ('U1510000', 'librarian', 'Led', 'Zeppelin', 2)
INSERT INTO lms.Users(userId, password, firstName, lastName, role) VALUES ('U1510001', 'librarian', 'Judas', 'Priest', 2)
