/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.millionair_game;

/**
 *
 * @author User
 */
public class Skipped extends AbstractJoker
{
    public boolean isSkiped()
    {
        return isUsed();
    }
    public void setSkiped(boolean used)
    {
        setUsed(used);
    }
}

    
    
    