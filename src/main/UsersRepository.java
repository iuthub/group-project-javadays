package main;

import java.sql.*;

// Data Access Object - UsersRepository

public class UsersRepository {
    private static UsersRepository instance;
    private final String DATABASE_URL = "jdbc:derby:./db/users";
    private final String GET_USER_PASSWORD_QUERY = "SELECT * FROM Users WHERE Login=? AND Password=?";
    private final String GET_QUERY = "SELECT * FROM Users WHERE Login=?";

    private Connection conn;

    private PreparedStatement getUserPassStmt;
    private PreparedStatement getStmt;

    // Private Constructor for Singleton object
    private UsersRepository() throws SQLException {
        this.conn = DriverManager.getConnection(DATABASE_URL);
        this.getUserPassStmt = this.conn.prepareStatement(GET_USER_PASSWORD_QUERY);
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
     * @param login User's login
     * @param password User's password
     * @return If user authenticated or not
     * @throws SQLException If there is not a valid query
     */
    public boolean authenticate(String login, String password) throws SQLException {
        ResultSet result;

        this.getUserPassStmt.setString(1, login);
        this.getUserPassStmt.setString(2, password);

        result = this.getUserPassStmt.executeQuery();

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
