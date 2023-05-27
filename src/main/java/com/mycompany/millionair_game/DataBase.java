/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.millionair_game;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBase {

    private final DbManager dbManager;
    private final Connection conn;
    private Statement statement;

    public DataBase() {
        dbManager = new DbManager();
        conn = dbManager.getConnection();

    }

    public void connectUsersDB() {
        try {
            this.statement = conn.createStatement();
            this.checkExistedTable("USERS");
            this.statement.addBatch("CREATE  TABLE USERS  (USERID  INT,   NAME   VARCHAR(50),   SCORE INT)");
            this.statement.addBatch("INSERT INTO USERS VALUES (1, 'Kira', 100000),\n"
                    + "(2, 'William', 500000)");
            this.statement.executeBatch();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void createPromotionTable() {
        try {
            /* You may need the following SQL statements for this method:
            
            * Create the table:
            CREATE TABLE PROMOTION (CATEGORY VARCHAR(20), DISCOUNT INT);
            
            * Insert records into the table:
            INSERT INTO PROMOTION VALUES ('Fiction', 0),
            ('Non-fiction', 10),
            ('Textbook', 20);
            
             */
            this.statement = conn.createStatement();
            this.checkExistedTable("PROMOTION");
            this.statement.addBatch("CREATE TABLE PROMOTION (CATEGORY VARCHAR(20), DISCOUNT INT)");
            this.statement.addBatch("INSERT INTO PROMOTION VALUES ('Fiction', 0),\n"
                    + "('Non-fiction', 10),\n"
                    + "('Textbook', 20)");
            this.statement.executeBatch();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    public void createWeekSpecialTable(ResultSet rs) {

        try {
            this.checkExistedTable("WEEKSPECIAL");
            this.statement = conn.createStatement();
            this.statement.addBatch("CREATE TABLE WEEKSPECIAL (TITLE   VARCHAR(50), SPECIALPRICE FLOAT)");
            while (rs.next()) {
                String title = rs.getString("TITLE");
                float price = rs.getFloat("PRICE");
                int discount = rs.getInt("DISCOUNT");
                float new_price = price * (100 - discount) / 100;
                this.statement.addBatch("INSERT INTO WEEKSPECIAL VALUES('" + title + "'," + new_price + ")");
            }
            this.statement.executeBatch();
            rs.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public ResultSet getWeekSpecial() {
        /* You may need the following SQL statements for this method:

         * Query multiple tables:
        
          SELECT TITLE, PRICE, DISCOUNT FROM BOOK, PROMOTION WHERE BOOK.CATEGORY=PROMOTION.CATEGORY;

         */

        ResultSet rs = null;
        try {
            rs = this.statement.executeQuery("SELECT TITLE, PRICE, DISCOUNT "
                    + "FROM BOOK, PROMOTION "
                    + "WHERE BOOK.CATEGORY=PROMOTION.CATEGORY");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return rs;

    }

    public void checkExistedTable(String name) {
        try {
            DatabaseMetaData dbmd = this.conn.getMetaData();
            String[] types = {"TABLE"};
            statement = this.conn.createStatement();
            ResultSet rs = dbmd.getTables(null, null, null, types);

            while (rs.next()) {
                String table_name = rs.getString("TABLE_NAME");
                System.out.println(table_name);
                if (table_name.equalsIgnoreCase(name)) {
                    statement.executeUpdate("Drop table " + name);
                    System.out.println("Table " + name + " has been deleted.");
                    break;
                }
            }
            rs.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void closeConnection() {
        this.dbManager.closeConnections();
    }

}
