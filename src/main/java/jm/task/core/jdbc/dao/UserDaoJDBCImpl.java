package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.*;

public class UserDaoJDBCImpl implements UserDao {
    private Connection con;

    public UserDaoJDBCImpl() {
        con = Util.getInstance().getConnection();
    }

    public void createUsersTable() {
        try (Statement stmt = con.createStatement()) {
            String query = "CREATE TABLE user ( id INT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(255), lastName VARCHAR(255), age INTEGER );";
            stmt.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try (Statement stmt = con.createStatement()) {
            String query = "DROP TABLE IF EXISTS user;";
            stmt.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String sql = "INSERT INTO user ( name, lastName, age ) VALUES (?, ?, ?);";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setString(2, lastName);
            stmt.setInt(3, age);

            stmt.executeUpdate();
            System.out.println(new StringBuffer("User с именем - ").append(name).append(" добавлен в базу данных :)"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try (Statement stmt = con.createStatement()) {
            String query = new StringBuffer("DELETE FROM user WHERE id=").append(id).append(";").toString();
            stmt.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        String sql = "SELECT * FROM user;";

        List<User> list = new LinkedList<>();

        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                String lastName = resultSet.getString("lastName");
                byte age = resultSet.getByte("age");

                list.add(new User(name, lastName, age));
            }

            System.out.println(Arrays.toString(list.toArray()));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public void cleanUsersTable() {
        try (Statement stmt = con.createStatement()) {
            String query = "DELETE FROM user;";
            stmt.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
