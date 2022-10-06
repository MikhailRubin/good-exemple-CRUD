package com.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class Crud {
    private static final Logger logger = Logger.getLogger("Logger");
    private static final String USERNAME = "postgres";
    private static final String URL = "jdbc:postgresql://localhost:5432/test_base";
    private static final String PASSWORD = "1";

    public void createTable(String nameTable) {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             Statement statement = connection.createStatement();) {
            connection.setAutoCommit(false);
            String createTableSQL = "CREATE TABLE " + nameTable +
                    "(id INT PRIMARY KEY," +
                    " firstName VARCHAR(50), " +
                    " lastName VARCHAR(50), " +
                    " age INTEGER NOT NULL)";
            statement.execute(createTableSQL);
            logger.info("Таблица " + nameTable + " создана!");
            connection.commit();
        } catch (SQLException e) {
            logger.info(e.getMessage());
        }
    }

    public void deleteUserById(int id) {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM users WHERE id = ?");) {
            preparedStatement.setInt(1, id);
            connection.setAutoCommit(false);
            int result = preparedStatement.executeUpdate();
            logger.info("Удалено пользователей с id " + id + ":" + result);
            connection.commit();
        } catch (SQLException e) {
            logger.info(e.getMessage());
        }
    }

    public List<User> selectAllUser() {
        List<User> users = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM users");) {
            connection.setAutoCommit(false);
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                int id = result.getInt("id");
                String firstName = result.getString("first_name");
                String lastName = result.getString("last_name");
                int age = result.getInt("age");
                User user = new User(id, firstName, lastName, age);
                while (result == null) {
                    users.add(user);
                }
                logger.info(id + "," + firstName + "," + lastName + "," + age);
                connection.commit();
            }
        } catch (SQLException e) {
            logger.info(e.getMessage());
        }
        return users;
    }

    public User selectUserById(int id) {
        User user = null;
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM users WHERE id= " + id);) {
            connection.setAutoCommit(false);
            ResultSet result = preparedStatement.executeQuery();
            if (result.next()) {
                int idUser = result.getInt("id");
                String firstName = result.getString("first_name");
                String lastName = result.getString("last_name");
                int age = result.getInt("age");
                user = new User(idUser, firstName, lastName, age);
                logger.info(idUser + "," + firstName + "," + lastName + "," + age);
            } else {
                logger.info("Пользователя с id " + id + " в БД нет");
            }
            connection.commit();
        } catch (SQLException e) {
            logger.info(e.getMessage());
        }
        return user;
    }

    public User updateUser(User user) {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement("UPDATE users SET  first_name = ?, last_name = ?, age = ? WHERE id = ?");) {
            connection.setAutoCommit(false);
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setInt(3, user.getAge());
            preparedStatement.setInt(4, user.getId());
            preparedStatement.executeUpdate();
            logger.info("Пользователь успешно обновлен");
            connection.commit();
        } catch (SQLException e) {
            logger.info(e.getMessage());
        }
        return user;
    }

    public void deleteAllUsers() {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM users");) {
            connection.setAutoCommit(false);
            preparedStatement.executeUpdate();
            logger.info("Все пользователи удалены");
            connection.commit();
        } catch (SQLException e) {
            logger.info(e.getMessage());
        }
    }

    public User addUser(User user) {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO users" +
                     "  (id, first_name, last_name, age) VALUES " +
                     " (?, ?, ?, ?);")) {
            connection.setAutoCommit(false);
            preparedStatement.setInt(1, user.getId());
            preparedStatement.setString(2, user.getFirstName());
            preparedStatement.setString(3, user.getLastName());
            preparedStatement.setInt(4, user.getAge());
            preparedStatement.executeUpdate();
            logger.info(user + " успешно добавлен");
            connection.commit();
        } catch (SQLException e) {
            logger.info(e.getMessage());
        }
        return user;
    }
}

