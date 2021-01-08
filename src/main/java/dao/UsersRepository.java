package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.AdminWindowStudent;
import model.User;
import java.sql.*;

// Data Access Object - dao.UsersRepository

public class UsersRepository {
    private static UsersRepository instance;
    private final PreparedStatement getStmt;
    private final PreparedStatement existsStmt;
    private final PreparedStatement getLoginStmt;
    private final PreparedStatement getCountStmt;
    private final PreparedStatement addUserStmt;
    private final PreparedStatement updUserStmt;
    private final PreparedStatement remUserStmt;
    private final PreparedStatement getForAdminStmt;

    private final String DISPLAY = "SELECT UserID, FirstName || ' ' || LastName AS Name FROM Users WHERE Role = ? ";

    // Private Constructor for Singleton object
    private UsersRepository() throws SQLException {
        String LOGIN = "SELECT 1 FROM Users WHERE UserID=? AND Password=?";
        String GET   = "SELECT * FROM Users WHERE userID=?";
        String EXIST = "SELECT 1 FROM Users WHERE UserID=?";
        String COUNT = "SELECT COUNT(*) FROM Users WHERE Role=?";
        String ADD   = "INSERT INTO Users VALUES(?, ?, ?, ?, ?)";
        String UPD   = "UPDATE Users SET UserID=?, FirstName=?, LastName=?, Password=? WHERE UserID=?";
        String DEL   = "DELETE FROM Users WHERE UserID =?";

        Connection conn = ConnectionManager.getConnection();
        this.getLoginStmt = conn.prepareStatement(LOGIN);
        this.getStmt      = conn.prepareStatement(GET);
        this.existsStmt   = conn.prepareStatement(EXIST);
        this.getCountStmt = conn.prepareStatement(COUNT);
        this.addUserStmt  = conn.prepareStatement(ADD);
        this.remUserStmt  = conn.prepareStatement(DEL);
        this.updUserStmt  = conn.prepareStatement(UPD);
        this.getForAdminStmt = conn.prepareStatement(DISPLAY + "OFFSET ? ROWS FETCH NEXT 100 ROWS ONLY");
    }

    // Singleton object getInstance() method
    public static UsersRepository getInstance() throws SQLException {
        if (instance == null) {
            instance = new UsersRepository();
        }
        return instance;
    }

    private String capitalize(String in){
        return in.substring(0, 1).toUpperCase() + in.substring(1).toLowerCase();
    }

    public boolean isExists(String userId) throws SQLException {
        this.existsStmt.setString(1, userId);
        ResultSet r =  this.existsStmt.executeQuery();
        return r.next();
    }

    /**
     * This method is used in controllers.LoginController class for authorizing model.User
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
                    result.getString("userID"),
                    result.getString("Password"),
                    result.getString("FirstName"),
                    result.getString("LastName"),
                    intToRole(result.getInt("Role"))
            );
        }

        return user;
    }

    public int getTotalCount(Role role) throws SQLException{
        getCountStmt.setInt(1, role.getValue());
        ResultSet result = getCountStmt.executeQuery();
        result.next();
        return result.getInt(1);
    }

    public void add(String userId, String firstName, String lastName, String password, Role role) throws SQLException {
        addUserStmt.setString(1, userId.toUpperCase());
        addUserStmt.setString(2, capitalize(firstName));
        addUserStmt.setString(3, capitalize(lastName));
        addUserStmt.setString(4, capitalize(password));
        addUserStmt.setInt   (5, role.getValue());
        addUserStmt.executeUpdate();
    }

    public void delete(String userId) throws SQLException{
        remUserStmt.setString(1, userId);
        remUserStmt.executeUpdate();
    }

    public void update(String oldId, String newId, String firstName, String lastName, String password) throws SQLException {
        updUserStmt.setString(1, newId);
        updUserStmt.setString(2, firstName);
        updUserStmt.setString(3, lastName);
        updUserStmt.setString(4, password);
        updUserStmt.setString(5, oldId);
        updUserStmt.executeUpdate();
    }

    public ObservableList<AdminWindowStudent> getForAdmin(Role role, int page) throws SQLException {
        ObservableList<AdminWindowStudent> list = FXCollections.observableArrayList();
        getForAdminStmt.setInt(1, role.getValue());
        getForAdminStmt.setInt(2, page * 100);
        ResultSet result = getForAdminStmt.executeQuery();

        while (result.next()){
            list.add(
                new AdminWindowStudent(
                    result.getString("UserID"),
                    result.getString("Name")
                )
            );
        }
        return list;
    }

    public ObservableList<AdminWindowStudent> searchForAdmin(Role role, String type, String search) throws SQLException {
        Connection conn = ConnectionManager.getConnection();
        String cName = "UserId";
        search = capitalize(search);

        switch (type) {
            case "User ID":
                cName = "UserId";
                if (!search.matches("[Uu].+")){
                    search = "U"+search;
                }
                break;
            case "First name":
                cName = "firstName";
                break;
            case "Last name":
                cName = "lastName";
                break;
        }
        String LIKE_QUERY = String.format("AND %s LIKE ?", cName);
        PreparedStatement searchByParamStmt = conn.prepareStatement(DISPLAY + LIKE_QUERY);
        searchByParamStmt.setInt(1, role.getValue());
        searchByParamStmt.setString(2, search + "%");

        ObservableList<AdminWindowStudent> list = FXCollections.observableArrayList();
        ResultSet result = searchByParamStmt.executeQuery();

        while (result.next()){
            list.add(
                new AdminWindowStudent(
                    result.getString("UserID"),
                    result.getString("Name")
                )
            );
        }
        searchByParamStmt.close();
        return list;
    }

    private Role intToRole(int i) {
        return Role.valueOf(i);
    }
}
