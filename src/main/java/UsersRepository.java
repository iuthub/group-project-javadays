package main.java;

import java.sql.*;

// Data Access Object - UsersRepository

public class UsersRepository {
    private static UsersRepository instance;
    private final PreparedStatement getLoginStmt;
    private final PreparedStatement getStmt;

    // Private Constructor for Singleton object
    private UsersRepository() throws SQLException {
        String LOGIN_QUERY = "SELECT 1 FROM Users WHERE userId=? AND password=?";
        String GET_QUERY = "SELECT * FROM Users WHERE userId=?";

        Connection conn = ConnectionManager.getConnection();
        this.getLoginStmt = conn.prepareStatement(LOGIN_QUERY);
        this.getStmt = conn.prepareStatement(GET_QUERY);
    }

    // Singleton object getInstance() method
    public static UsersRepository getInstance() throws SQLException {
        if (instance == null) {
            instance = new UsersRepository();
        }
        return instance;
    }

    /**
     * This method is used in LoginController class for authorizing User
     * @param uid User's login
     * @param password User's password
     * @return If user authenticated or not
     * @throws SQLException If there is not a valid query
     */
    public boolean authenticate(String uid, String password) throws SQLException {
        ResultSet result;

        this.getLoginStmt.setString(1, uid);
        this.getLoginStmt.setString(2, password);

        result = this.getLoginStmt.executeQuery();
        return result.next();
    }

    public User get(String userID) throws SQLException {
        ResultSet result;
        User user = null;

        this.getStmt.setString(1, userID);

        result = this.getStmt.executeQuery();

        if (result.next()) {
            user = new User(
                    result.getString("userId"),
                    result.getString("password"),
                    result.getString("firstName"),
                    result.getString("lastName"),
                    intToRole(result.getInt("role"))
            );
        }

        return user;
    }

    private int roleToInt(Role role) {
        switch (role) {
            case ADMIN:
                return 0;
            case LIBRARIAN:
                return 1;
            case STUDENT:
                return 2;
            default:
                return -1;
        }
    }

    private Role intToRole(int i) {
        switch(i) {
            case 0:
                return Role.ADMIN;
            case 1:
                return Role.LIBRARIAN;
            case 2:
                return Role.STUDENT;
            default:
                return null;
        }
    }
}
