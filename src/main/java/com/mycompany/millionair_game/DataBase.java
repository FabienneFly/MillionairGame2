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

    private final DBManager dbManager;
    private final Connection conn;
    private Statement statement;

    public DataBase() {
        dbManager = new DBManager();
        conn = dbManager.getConnection();

    }

    public void connectBookStoreDB() {
        try {
            this.statement = conn.createStatement();
            this.checkExistedTable("USERS");
            this.statement.addBatch("CREATE  TABLE USERS  (USERID  INT,   USERNAME   VARCHAR(50),   SCORE INT)");
            this.statement.addBatch("INSERT INTO USERS VALUES (1, 'Fabi', 500000), (2, 'Kira', 100000)");
            this.statement.executeBatch();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void showTable() {
        try {
            this.statement = conn.createStatement();
            ResultSet rs = this.statement.executeQuery("SELECT * FROM USERS");

            while (rs.next()) {
                int userID = rs.getInt("USERID");
                String name = rs.getString("USERNAME");
                int score = rs.getInt("SCORE");

                System.out.println("USERID: " + userID);
                System.out.println("NAME: " + name);
                System.out.println("SCORE: " + score);
                System.out.println("-------------------");
            }

            rs.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
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
