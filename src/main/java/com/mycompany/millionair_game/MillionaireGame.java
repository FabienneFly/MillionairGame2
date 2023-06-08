package com.mycompany.millionair_game;

/**
 *
 * @author FabiF
 */
public class MillionaireGame 
{

    public static void main(String[] args) {

        //New instance of GameStart
        Player player = new Player("", 0);
        new Login(player).setVisible(true);
        GameStart gameStart = new GameStart();
        gameStart.start();


    }

}
