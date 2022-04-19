/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SnakeGame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePanel extends JPanel implements ActionListener, KeyListener {
   
    private int[] snake_X_Length = new int[750];
    private int[] snake_Y_Length = new int[750];
    private int lengthOfSnake = 3;
   
    private int[] xPos = {25,50,75,100,125,150,175,200,225,250,275,300,325,350,375,400,425,450,475,500,525,550,575,600,625,650,675,700,725,750,775,800,825,850};
    private int[] yPos = {75,100,125,150,175,200,225,250,275,300,325,350,375,400,425,450,475,500,525,550,575,600,625};
   
    private Random random = new Random();
    private int enemyX, enemyY;
   
    private boolean left = false;
    private boolean right = true;
    private boolean up = false;
    private boolean down = false;
   
    private int moves = 0;
    private int score = 0;
    private boolean gameOver = false;
   
    private ImageIcon snaketitle = new ImageIcon( getClass().getResource("snaketitle.jpg"));
    private ImageIcon leftMouth = new ImageIcon( getClass().getResource("leftMouth.png"));
    private ImageIcon rightMouth = new ImageIcon( getClass().getResource("rightMouth.png"));
    private ImageIcon upMouth = new ImageIcon( getClass().getResource("upMouth.png"));
    private ImageIcon downMouth = new ImageIcon( getClass().getResource("downMouth.png"));
    private ImageIcon snakeImage = new ImageIcon( getClass().getResource("snakeImage.png"));
    private ImageIcon enemy = new ImageIcon( getClass().getResource("enemy.png"));
   
    private Timer timer;
    private int delay = 100;
   
    GamePanel() {
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(true);
       
       
        timer = new Timer(delay, this);
        timer.start();
       
        newEnemy();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
       
        g.setColor(Color.yellow);
        g.drawRect(24, 10, 851, 55); //postion - x(left)  y(top)  width heigth
        g.drawRect(24, 74, 851, 576);
        snaketitle.paintIcon(this, g, 25, 11);
       
        g.setColor(Color.WHITE);
        g.fillRect(25, 75, 850, 575);
       
        if(moves == 0) {
            snake_X_Length[0] = 100;
            snake_X_Length[1] = 75;
            snake_X_Length[2] = 50;
           
            snake_Y_Length[0] = 100;
            snake_Y_Length[1] = 100;
            snake_Y_Length[2] = 100;
        }
       
        if(left) {
            leftMouth.paintIcon(this, g, snake_X_Length[0], snake_Y_Length[0]);
        }
        if(right) {
            rightMouth.paintIcon(this, g, snake_X_Length[0], snake_Y_Length[0]);
        }
        if(up) {
            upMouth.paintIcon(this, g, snake_X_Length[0], snake_Y_Length[0]);
        }
        if(down) {
            downMouth.paintIcon(this, g, snake_X_Length[0], snake_Y_Length[0]);
        }
       
        for(int i = 1; i < lengthOfSnake; i++) {
            snakeImage.paintIcon(this, g, snake_X_Length[i], snake_Y_Length[i]);
        }
       
        enemy.paintIcon(this, g, enemyX, enemyY);
       
        if(gameOver) {
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD,50));
            g.drawString("Game Over", 300, 300);
           
            g.setFont(new Font("Arial", Font.PLAIN,20));
            g.drawString("Press SPACE to Restart", 320, 350);
        }
       
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, 14));
        g.drawString("Score : " + score, 750, 30);
        g.drawString("Length : " + lengthOfSnake, 750, 50);
       
        g.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
       
        for(int i = lengthOfSnake-1; i>0; i--) {
            snake_X_Length[i] = snake_X_Length[i-1];
            snake_Y_Length[i] = snake_Y_Length[i-1];
        }
       
        if(left) {
            snake_X_Length[0] = snake_X_Length[0] - 25;
        }
        if(right) {
            snake_X_Length[0] = snake_X_Length[0] + 25;
        }
        if(up) {
            snake_Y_Length[0] = snake_Y_Length[0] - 25;
        }
        if(down) {
            snake_Y_Length[0] = snake_Y_Length[0] + 25;
        }
       
        if(snake_X_Length[0] > 850) snake_X_Length[0] = 25;
        if(snake_X_Length[0] < 25) snake_X_Length[0] = 850;
       
        if(snake_Y_Length[0] > 625) snake_Y_Length[0] = 75;
        if(snake_Y_Length[0] < 75) snake_Y_Length[0] = 625;
       
        collidesWithEnemy();
        collidesWithBody();
       
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
       
        if(e.getKeyCode() == KeyEvent.VK_SPACE) {
            restart();
        }
       
        if(e.getKeyCode() == KeyEvent.VK_LEFT && (!right)) {
            left = true;
            right = false;
            up = false;
            down = false;
            moves++;
        }
        if(e.getKeyCode() == KeyEvent.VK_RIGHT && (!left)) {
            left = false;
            right = true;
            up = false;
            down = false;
            moves++;
        }
        if(e.getKeyCode() == KeyEvent.VK_UP && (!down)) {
            left = false;
            right = false;
            up = true;
            down = false;
            moves++;
        }
        if(e.getKeyCode() == KeyEvent.VK_DOWN && (!up)) {
            left = false;
            right = false;
            up = false;
            down = true;
            moves++;
        }
    }

    @Override
    public void keyReleased(KeyEvent arg0) {
    }
   
    @Override
    public void keyTyped(KeyEvent arg0) {
    }

    private void newEnemy() {
        enemyX = xPos[random.nextInt(34)];
        enemyY = yPos[random.nextInt(23)];
       
        for(int i = lengthOfSnake-1; i>=0; i--) {
            if(snake_X_Length[i] == enemyX && snake_Y_Length[i] == enemyY) {
                newEnemy();
            }
        }
    }
   
    private void collidesWithEnemy() {
        if(snake_X_Length[0] == enemyX && snake_Y_Length[0] == enemyY) {
            newEnemy();
            lengthOfSnake++;
            score++;
        }
    }
   
    private void collidesWithBody() {
        for(int i =lengthOfSnake-1; i > 0; i--) {
            if(snake_X_Length[i] == snake_X_Length[0] && snake_Y_Length[i] == snake_Y_Length[0]) {
                timer.stop();
                gameOver = true;
            }
        }
    }
   
    private void restart() {
        gameOver = false;
        moves = 0;
        score = 0;
        lengthOfSnake = 3;
        left = false;
        right = true;
        up = false;
        down = false;
        timer.start();
        repaint();
    }
}