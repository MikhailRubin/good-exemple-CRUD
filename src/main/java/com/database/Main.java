package com.database;

import org.postgresql.util.PSQLException;

public class Main {


    public static void main(String[] args) throws PSQLException {
        Crud crud = new Crud();

        crud.createTable();

    }
}
