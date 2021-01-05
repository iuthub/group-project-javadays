package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
    private static Connection conn;

    private ConnectionManager(){}

    public static Connection getConnection() throws SQLException {
        if (conn == null){
            conn = DriverManager.getConnection("jdbc:derby:./db/LMS");
        }

        return conn;
    }

    public static void closeConnection() throws SQLException {
        if (!conn.isClosed()){
            conn.close();
        }
    }
}

