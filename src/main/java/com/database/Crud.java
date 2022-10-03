package com.database;

import java.sql.*;
import java.util.logging.Logger;


public class Crud {
    private static final Logger logger = Logger.getLogger("Logger");
    private static final String USERNAME = "postgres";
    private static final String URL = "jdbc:postgresql://localhost:5432/test_base";
    private static final String PASSWORD = "1";

    public void createTable() {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             Statement statement = connection.createStatement();) {
            connection.setAutoCommit(false);
            String createTableSQL = "CREATE TABLE users1111" +
                    "(ID INT PRIMARY KEY ," +
                    " FIRST_NAME VARCHAR(50), " +
                    " LAST_NAME VARCHAR(50), " +
                    " AGE INTEGER not null)";
            statement.execute(createTableSQL);
            logger.info("Таблица создана!");
            connection.commit();
        } catch (SQLException e) {
            logger.info(e.getMessage());
        }
    }

    public void deleteUserById() {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM USERS WHERE id = ?;");) {
            preparedStatement.setInt(1, 1);
        } catch (SQLException e) {
            logger.info(e.getMessage());
        }
    }


    public void selectAllUser() {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM users");) {
            connection.setAutoCommit(false);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                long id = rs.getInt("id");
                String firstName = rs.getString("firstName");
                String lastName = rs.getString("lastName");
                int age = rs.getInt("age");
                logger.info(id + "," + firstName + "," + lastName + "," + age);
                connection.commit();
            }
        } catch (SQLException e) {
            logger.info(e.getMessage());
        }
    }


    public void selectUserById() {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM users WHERE id=1");) {
            connection.setAutoCommit(false);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("id");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                int age = rs.getInt("age");
                logger.info(id + "," + firstName + "," + lastName + "," + age);
            } else {
                logger.info("Такого пользователя нет в бд");
            }
            connection.commit();
        } catch (SQLException e) {
            logger.info(e.getMessage());
        }
    }


    public void updateUser(User user) {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement("UPDATE users SET  first_name = ?, last_name = ?, age = ? WHERE id = 5;")) {
            connection.setAutoCommit(false);
            preparedStatement.setString(1, "МИХАСЬ");
            preparedStatement.setString(2, "ЛЫНЬКОУ");
            preparedStatement.setInt(3, 19);

            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            logger.info(e.getMessage());
        }
    }

    public void deleteAllUsers() {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM USERS");) {
            connection.setAutoCommit(false);
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            logger.info(e.getMessage());
        }
    }

    public void addUser() {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO users" +
                     "  (id, first_name, last_name, age) VALUES " +
                     " (?, ?, ?, ?);")) {
            connection.setAutoCommit(false);
            preparedStatement.setInt(1, 2);
            preparedStatement.setString(2, "Tony111");
            preparedStatement.setString(3, "Яё111");
            preparedStatement.setInt(4, 25);
            logger.info(String.valueOf(preparedStatement));
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            logger.info(e.getMessage());
        }
    }
}
