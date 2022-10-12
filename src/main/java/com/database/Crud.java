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
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM users_id_seq");) {
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
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO users " +
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

    public void createNewTable(String nameTable) {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             Statement statement = connection.createStatement();) {
            connection.setAutoCommit(false);
            String createTableSQL = "CREATE TABLE " + nameTable +
                    "(id INT PRIMARY KEY," +
                    " city VARCHAR(50), " +
                    " street VARCHAR(50), " +
                    " house INTEGER NOT NULL)";
            statement.execute(createTableSQL);
            logger.info("Таблица " + nameTable + " создана!");
            connection.commit();
        } catch (SQLException e) {
            logger.info(e.getMessage());
        }
    }

    public Address addAddress(Address address) {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO user_address" +
                     "  (id, city, street, house) VALUES " +
                     " (?, ?, ?, ?);")) {
            connection.setAutoCommit(false);
            preparedStatement.setInt(1, address.getId());
            preparedStatement.setString(2, address.getCity());
            preparedStatement.setString(3, address.getStreet());
            preparedStatement.setInt(4, address.getHouse());
            preparedStatement.executeUpdate();
            logger.info(address + " успешно добавлен");
            connection.commit();
        } catch (SQLException e) {
            logger.info(e.getMessage());
        }
        return address;
    }

    public List<User> selectAllUsers() {
        List<User> users = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM users JOIN user_address ON users.Id = user_address.Id");) {
            connection.setAutoCommit(false);
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                int id = result.getInt("id");
                String firstName = result.getString("first_name");
                String lastName = result.getString("last_name");
                int age = result.getInt("age");
                String city = result.getString("city");
                String street = result.getString("street");
                int house = result.getInt("house");
                User user = new User(id, firstName, lastName, age);
                while (result == null) {
                    users.add(user);
                }
                logger.info(id + "," + firstName + "," + lastName + "," + age + "," + city + "," + street + "," + house);
                connection.commit();
            }
        } catch (SQLException e) {
            logger.info(e.getMessage());
        }
        return users;
    }

    public void deleteUsersById(int id) {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM users user_address WHERE id = ?");) {
            preparedStatement.setInt(1, id);
            connection.setAutoCommit(false);
            int result = preparedStatement.executeUpdate();
            logger.info("Удалено пользователей с id " + id + ":" + result);
            connection.commit();
        } catch (SQLException e) {
            logger.info(e.getMessage());
        }
    }

    public List<Address> selectAllAddress() {
        List<Address> addresss = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM user_address");) {
            connection.setAutoCommit(false);
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                int id = result.getInt("id");
                String city = result.getString("city");
                String street = result.getString("street");
                int house = result.getInt("house");
                Address address = new Address(id, city, street, house);
                while (result == null) {
                    addresss.add(address);
                }
                logger.info(id + "," + city + "," + street + "," + house);
                connection.commit();
            }
        } catch (SQLException e) {
            logger.info(e.getMessage());
        }
        return addresss;
    }

    public List<User> selectUserByHouse(int house) {
        List<User> users = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM users JOIN user_address ON users.Id = user_address.Id WHERE house = ?");) {
            preparedStatement.setInt(1, house);
            connection.setAutoCommit(false);
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                int idUser = result.getInt("id");
                String firstName = result.getString("first_name");
                String lastName = result.getString("last_name");
                int age = result.getInt("age");
                String city = result.getString("city");
                String street = result.getString("street");
                User user = new User(idUser, firstName, lastName, age);
                while (result == null) {
                    users.add(user);
                }
                logger.info(idUser + "," + firstName + "," + lastName + "," + age + "," + city + "," + street + "," + house);
                connection.commit();
            }
        } catch (SQLException e) {
            logger.info(e.getMessage());
        }
        return users;
    }
}

