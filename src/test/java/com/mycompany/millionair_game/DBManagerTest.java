/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package com.mycompany.millionair_game;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.InjectMocks;
import org.junit.Before;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.ResultSet;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
//ChatGPT suggested to use Mockito
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class DBManagerTest {

    //With the help of ChatGpt
    @Mock
    Connection mockConnection;
    @Mock
    Statement mockStatement;
    @Mock
    ResultSet mockResultSet;
    @InjectMocks
    DBManager dbManager = DBManager.getInstance();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void testGetConnection() throws Exception {
        assertNotNull(dbManager.getConnection());
    }

    @Test
    public void testQueryDB() throws Exception {
        when(mockConnection.createStatement()).thenReturn(mockStatement);
        when(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true).thenReturn(false);

        ResultSet resultSet = dbManager.queryDB("SELECT * FROM USERS");
        assertTrue(resultSet.next());
        assertFalse(resultSet.next());
    }

    @Test
    public void testUpdateDB() throws Exception {
        when(mockConnection.createStatement()).thenReturn(mockStatement);
        dbManager.updateDB("UPDATE USERS SET SCORE = 100 WHERE USERNAME = 'Test'");
        verify(mockStatement).executeUpdate("UPDATE USERS SET SCORE = 100 WHERE USERNAME = 'Test'");
    }

    @Test
    public void testDeleteAllUsers() throws Exception {
        when(mockConnection.createStatement()).thenReturn(mockStatement);
        dbManager.deleteAllUsers();
        verify(mockStatement).executeUpdate("DELETE FROM USERS");
    }

    @Test
    public void testSavePlayerStats() throws Exception {
        Player player = new Player("Test", 100);
        when(mockConnection.createStatement()).thenReturn(mockStatement);
        dbManager.savePlayerStats(player);
        String sql = "INSERT INTO USERS(USERNAME, SCORE) VALUES ('" + player.getName() + "', " + player.getMoney() + ")";
        verify(mockStatement).executeUpdate(sql);
    }
}
