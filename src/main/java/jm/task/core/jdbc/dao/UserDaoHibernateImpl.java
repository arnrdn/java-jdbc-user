package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.LinkedList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    private SessionFactory sessionFactory;

    public UserDaoHibernateImpl() {
        sessionFactory = Util.getSessionFactory();
    }


    @Override
    public void createUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            // start transaction
            session.beginTransaction();
            String sql = "CREATE TABLE user ( id INT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(255), lastName VARCHAR(255), age INTEGER );";
            Query query = session.createSQLQuery(sql).addEntity(User.class);
            query.executeUpdate();
            // commit transaction
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            // start transaction
            session.beginTransaction();
            String sql = "DROP TABLE IF EXISTS user;";
            Query query = session.createSQLQuery(sql).addEntity(User.class);
            query.executeUpdate();
            // commit transaction
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        User user = new User(name, lastName, age);
        try (Session session = sessionFactory.openSession()) {
            // start transaction
            session.beginTransaction();
            // save the user obj
            session.save(user);
            // commit transaction
            session.getTransaction().commit();
        } catch (Exception e) {
            System.err.println("Something went wrong :( " + e.getMessage());
            e.printStackTrace();
        }

    }

    @Override
    public void removeUserById(long id) {
        try (Session session = sessionFactory.openSession()) {
            // start transaction
            session.beginTransaction();
            // find and delete user
            User user = session.load(User.class, id);
            session.delete(user);
            // commit transaction
            session.getTransaction().commit();
        } catch (Exception e) {
            System.err.println("Something went wrong :( " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> list = new LinkedList<>();
        try (Session session = sessionFactory.openSession()) {
            // start transaction
            session.beginTransaction();
            // select all users
            list = session.createQuery("FROM User").list();
            // commit transaction
            session.getTransaction().commit();
        } catch (Exception e) {
            System.err.println("Something went wrong :( " + e.getMessage());
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            // start transaction
            session.beginTransaction();
            // delete every user from table
            Query query = session.createQuery("DELETE FROM User");
            query.executeUpdate();
            // commit transaction
            session.getTransaction().commit();
        } catch (Exception e) {
            System.err.println("Something went wrong :( " + e.getMessage());
            e.printStackTrace();
        }
    }
}
