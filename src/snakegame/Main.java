
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SnakeGame;

import java.awt.Color;
import javax.swing.JFrame;

/**
 *
 * @author hp
 */
public class Main {
   
    public static void main(String[] args) {
        JFrame frame = new JFrame("Snake Game");
        frame.setBounds(10,10,905,700); //location and size ..left top width height
        frame.setResizable(false); 
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       
        GamePanel panel = new GamePanel();
        panel.setBackground(Color.RED);
        frame.add(panel);
        frame.setVisible(true);
       
       
    }
}



















