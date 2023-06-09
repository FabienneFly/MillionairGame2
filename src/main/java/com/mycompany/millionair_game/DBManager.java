/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.millionair_game;

/**
 *
 * @author FabiF
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public final class DBManager {

    private static DBManager instance = null;

    private static final String URL = "jdbc:derby:MillionairDB;create=true";

    private static final String USER_NAME = "abc"; //your DB username
    private static final String PASSWORD = "abc";//your DB password

    Connection conn;

    public DBManager() {
        establishConnection();
    }

    public Connection getConnection() {
        return this.conn;
    }

    //Establish connection
    public void establishConnection() {
        if (this.conn == null) {
            try {
                conn = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
                System.out.println(URL + " Get Connected Successfully ....");
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    public static DBManager getInstance() {
        if (instance == null) {
            instance = new DBManager();
        }
        return instance;
    }

    public void printUsersTable() {
        String sql = "SELECT * FROM USERS";
        ResultSet resultSet = null;

        try {
            Statement statement = conn.createStatement();
            resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                String name = resultSet.getString("USERNAME");
                int score = resultSet.getInt("SCORE");

                System.out.println("USERNAME: " + name);
                System.out.println("SCORE: " + score);
                System.out.println("-------------------");
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    public void deleteAllUsers() {
        String sql = "DELETE FROM USERS";

        try {
            Statement statement = conn.createStatement();
            int rowsDeleted = statement.executeUpdate(sql);

            if (rowsDeleted > 0) {
                System.out.println("All records were deleted from USERS table");
            } else {
                System.out.println("No records to delete in USERS table");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void closeConnections() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    public void savePlayerStats(Player player) {
        String sql = "INSERT INTO USERS(USERNAME, SCORE) VALUES ('"
                + player.getName() + "', "
                + player.getMoney() + ")";

        updateDB(sql);
    }

    public ResultSet queryDB(String sql) {

        Connection connection = this.conn;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return resultSet;
    }

    public void updateDB(String sql) {

        Connection connection = this.conn;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.createStatement();
            statement.executeUpdate(sql);

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

}
