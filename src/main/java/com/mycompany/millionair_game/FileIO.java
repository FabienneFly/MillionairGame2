
package com.mycompany.millionair_game;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author FabiF
 */
public class FileIO 
{
    //Reads File helpScreen.txt
    public void helpScreen() 
    {
        try {
            FileReader fileReader = new FileReader("helpScreen.txt");
            try (BufferedReader bufferedReader = new BufferedReader(fileReader)) 
            {
                String line;
                while ((line = bufferedReader.readLine()) != null) 
                {
                    System.out.println(line);
                }
            }
        } 
        catch (IOException e) {
            System.out.println("Error reading help screen file: " + e.getMessage());
        }
    }
}