package main.java;

import java.sql.*;

// Data Access Object - UsersRepository

public class UsersRepository {
    private static UsersRepository instance;
    private final Connection conn;

    private final PreparedStatement getLoginStmt;
    private final PreparedStatement getStmt;

    // Private Constructor for Singleton object
    private UsersRepository() throws SQLException {
        String LOGIN_QUERY = "SELECT 1 FROM Users WHERE UserID=? AND Password=?";
        String GET_QUERY = "SELECT * FROM Users WHERE UserID=?";

        this.conn = DriverManager.getConnection("jdbc:derby:./db/users");
        this.getLoginStmt = this.conn.prepareStatement(LOGIN_QUERY);
        this.getStmt = this.conn.prepareStatement(GET_QUERY);
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
                    result.getString("UserID"),
                    result.getString("Password"),
                    result.getString("FirstName"),
                    result.getString("LastName"),
                    result.getInt("Role")
            );
        }

        return user;
    }
}
