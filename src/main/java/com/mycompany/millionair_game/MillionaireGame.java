package com.mycompany.millionair_game;

/**
 *
 * @author FabiF
 */
public class MillionaireGame {

    public static void main(String[] args) {
        //New instance of GameStart

        new GUI().setVisible(true);
<<<<<<< Updated upstream
=======
        
        
          DataBase db = new DataBase();
        db.connectBookStoreDB();
        db.showTable();
        db.closeConnection();
        
>>>>>>> Stashed changes
    }

}
