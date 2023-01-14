package jm.task.core.jdbc.util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    // реализуйте настройку соеденения с БД
    private static String url = "jdbc:mysql://localhost:3306/userdb";
    private static String user = "user";
    private static String pswd = "root";
    private static Connection connection;

    private Util() {
        try {
            if (connection == null) {
                connection = DriverManager.getConnection(url, user, pswd);
            }
        } catch (SQLException e) {
            System.err.println("Error while getting connection with MySQL database: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static class SingletonHolder {
        public static final Util HOLDER_INSTANCE = new Util();
    }

    public Connection getConnection() {
        return connection;
    }

    public static Util getInstance() {
        return SingletonHolder.HOLDER_INSTANCE;
    }

}
