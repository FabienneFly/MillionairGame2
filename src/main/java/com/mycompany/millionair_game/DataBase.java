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

    DBManager dbManager = DBManager.getInstance();
    private final Connection conn;
    private Statement statement;

    public DataBase() {
        dbManager = DBManager.getInstance();
        conn = dbManager.getConnection();
        connectDB();
        checkAndCreateTable("USERS");

    }

    public void connectDB() {
        try {
            this.statement = conn.createStatement();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void checkAndCreateTable(String tableName) {
        try {
            DatabaseMetaData dbmd = conn.getMetaData();
            ResultSet tables = dbmd.getTables(null, null, tableName.toUpperCase(), null);

            if (!tables.next()) {
                statement.executeUpdate("CREATE TABLE " + tableName + " (USERNAME VARCHAR(50), SCORE INT)");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public String showTable() {
        StringBuilder tableString = new StringBuilder();
        try {
            this.statement = conn.createStatement();
            ResultSet rs = this.statement.executeQuery("SELECT * FROM USERS");

            while (rs.next()) {
                String name = rs.getString("USERNAME");
                int score = rs.getInt("SCORE");

                tableString.append("NAME: ").append(name).append("\n");
                tableString.append("SCORE: ").append(score).append("\n");
                tableString.append("-------------------\n");
            }

            rs.close();
        } catch (SQLException ex) {
            tableString.append(ex.getMessage());
        }
        return tableString.toString();
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
