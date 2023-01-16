package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

public class Main {
    public static void main(String[] args) {
        // реализуйте алгоритм здесь
        UserService userService = new UserServiceImpl();

        userService.createUsersTable();

        userService.saveUser("Diane", "Nguyen", (byte) 35);
        userService.saveUser("BoJack", "Horseman", (byte) 56);
        userService.saveUser("Mr.", "Peanutbutter", (byte) 55);
        userService.saveUser("Sarah", "Lynn", (byte) 31);

        userService.removeUserById(1);
        userService.getAllUsers();
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
