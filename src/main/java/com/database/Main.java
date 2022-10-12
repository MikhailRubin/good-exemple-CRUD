package com.database;

import org.postgresql.util.PSQLException;

public class Main {

    public static void main(String[] args) throws PSQLException {
        Crud crud = new Crud();

        //crud.createTable("newTable");
        //crud.addUser(new User(2, "ЯКАДЗЕ", "ДИМА", 50));
        //crud.updateUser(new User(2, "САШКА", "РЫЖИК", 46, null));
        //crud.selectUserById(10);
        //crud.selectAllUser();
        //crud.selectAllAddress();
        //crud.deleteUserById(3);
        //crud.deleteAllUsers();
        //crud.createNewTable("user_address");
        //crud.addAddress(new Address(2, "Минск", "Советская", 10));
        //crud.deleteUsersById(3);
        crud.selectAllUsers();
        crud.selectUserByHouse(20);
    }
}
