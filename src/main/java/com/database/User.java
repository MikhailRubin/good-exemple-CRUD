package com.database;


import java.sql.*;

public class User {

    private int id;
    private String first_name;
    private String last_name;
    private int age;
    private static final String USERNAME = "postgres";
    private static final String URL = "jdbc:postgresql://localhost:5432/test_base";
    private static final String PASSWORD = "1";

    public User() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", age=" + age +
                '}';
    }


    public void createTable() {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             Statement statement = connection.createStatement();) {
            String createTableSQL = "CREATE TABLE users111" +
                    "(ID INT PRIMARY KEY ," +
                    " FIRST_NAME VARCHAR(50), " +
                    " LAST_NAME VARCHAR(50), " +
                    " AGE INTEGER not null)";
            statement.execute(createTableSQL);
            System.out.println(createTableSQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteUserById() {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM USERS WHERE id = ?;");) {
            preparedStatement.setInt(1, 1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void selectAllUser() {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM users");) {
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                id = rs.getInt("id");
                first_name = rs.getString("first_name");
                last_name = rs.getString("last_name");
                age = rs.getInt("age");
                System.out.println(id + "," + first_name + "," + last_name + "," + age);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void selectUserById() {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM users WHERE id=51");) {
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("id");
                String first_name = rs.getString("first_name");
                String last_name = rs.getString("last_name");
                int age = rs.getInt("age");
                System.out.println(id + "," + first_name + "," + last_name + "," + age);
            } else {
                System.out.println("Такого пользователя нет в бд");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void updateUser() {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement("UPDATE users SET  first_name = ?, last_name = ?, age = ? WHERE id = 5;")) {
            preparedStatement.setString(1, "МИХАСЬ");
            preparedStatement.setString(2, "ЛЫНЬКОУ");
            preparedStatement.setInt(3, 19);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteAllUsers() {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM USERS");) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
