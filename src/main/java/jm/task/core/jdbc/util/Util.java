package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    // реализуйте настройку соеденения с БД
    private static String url = "jdbc:mysql://localhost:3306/userdb";
    private static String user = "user";
    private static String pswd = "root";
    private static Connection connection;

    private Util() {

    }

    public static Connection getConnection() {

        try {
            if (connection == null) {
                connection = DriverManager.getConnection(url, user, pswd);
            }
        } catch (SQLException e) {
            System.err.println("Error while getting connection with MySQL database: " + e.getMessage());
            e.printStackTrace();
        }

        return connection;
    }

    public static void closeConnection() {
        try {
            if (!connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            System.err.println("Error while closing connection with MySQL database: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
