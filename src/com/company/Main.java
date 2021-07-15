package com.company;

import java.sql.*;
import java.sql.DriverManager;
import java.sql.Statement;

public class Main {

    public static void main(String[] args) throws Exception {
        //-------------------------------------------------- Подключение к БД
        String url = "jdbc:mysql://localhost:3306/Book"; // наша бд
        String userName = "root"; // доступ
        String password = "";
        Class.forName("com.mysql.cj.jdbc.Driver"); // драйвер соединения
        try (Connection c = DriverManager.getConnection(url, userName, password); // само соединение
             Statement stmt = c.createStatement()) {
            c.setAutoCommit(false);
            //-------------------------------------------------- Создание таблицы
            stmt.execute("DROP TABLE IF EXISTS Book");
            String sql = "CREATE TABLE IF NOT EXISTS Book " +
                    "(ID INT PRIMARY KEY     NOT NULL," +
                    " NAME           TEXT    NOT NULL, " +
                    " AGE            INT     NOT NULL, " +
                    " ADDRESS        CHAR(50), " +
                    " PRICE         REAL)"; // создание таблицы в sql
            stmt.executeUpdate(sql); // обновить бд

            //-------------------------------------------------- Заполнение
            sql = "INSERT INTO Book (ID,NAME,AGE,ADDRESS,PRICE) " +
                    "VALUES (1, 'OOP', 45, 'USA', 2000.00 );";
            stmt.executeUpdate(sql); // обновление действий по запросу sql, втавка полей в бд / INSERT(вставка)

            Savepoint savepoint = c.setSavepoint(); // место сохранения в коде, до куда будет возврат

            sql = "INSERT INTO Book (ID,NAME,AGE,ADDRESS,PRICE) " +
                    "VALUES (2, 'War and pice', 25, 'Ru', 2000.00 );";
            stmt.executeUpdate(sql); // обновление действий по запросу sql, втавка полей в бд / INSERT(вставка)
            sql = "INSERT INTO Book (ID,NAME,AGE,ADDRESS,PRICE) " +
                    "VALUES (3, 'Gold', 35, 'En', 2000.00 );";
            stmt.executeUpdate(sql); // обновление действий по запросу sql, втавка полей в бд / INSERT(вставка)
            sql = "INSERT INTO Book (ID,NAME,AGE,ADDRESS,PRICE) " +
                    "VALUES (4, 'Potato and Father', 17, 'By', 2000.00 );";
            stmt.executeUpdate(sql); // обновление действий по запросу sql, втавка полей в бд / INSERT(вставка)

            c.rollback(savepoint); // откатывает до точки сохранения, без неё, до самого начала соединения
            c.commit(); // сохраняем изменения
            c.releaseSavepoint(savepoint); // говорим до куда был сделан откат
        }
    }
}
