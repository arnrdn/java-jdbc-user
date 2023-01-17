package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
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
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            // start transaction
            transaction = session.beginTransaction();
            String sql = "CREATE TABLE user ( id INT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(255), lastName VARCHAR(255), age INTEGER );";
            Query query = session.createSQLQuery(sql).addEntity(User.class);
            query.executeUpdate();
            // commit transaction
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.err.println("Error while creating table: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            // start transaction
            transaction = session.beginTransaction();
            String sql = "DROP TABLE IF EXISTS user;";
            Query query = session.createSQLQuery(sql).addEntity(User.class);
            query.executeUpdate();
            // commit transaction
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.err.println("Error while dropping the table: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        User user = new User(name, lastName, age);
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            // start transaction
            transaction = session.beginTransaction();
            // save the user obj
            session.save(user);
            // commit transaction
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.err.println("Error while trying to add a new user: " + e.getMessage());
            e.printStackTrace();
        }

    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            // start transaction
            transaction = session.beginTransaction();
            // find and delete user
            User user = session.load(User.class, id);
            session.delete(user);
            // commit transaction
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.err.println("Error while trying to remove user by id " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> list = new LinkedList<>();
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            // start transaction
            transaction = session.beginTransaction();
            // select all users
            list = session.createQuery("FROM User").list();
            // commit transaction
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.err.println("Error while trying to get all users: " + e.getMessage());
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public void cleanUsersTable() {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            // start transaction
            transaction = session.beginTransaction();
            // delete every user from table
            Query query = session.createQuery("DELETE FROM User");
            query.executeUpdate();
            // commit transaction
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.err.println("Error while trying to clean the table: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
