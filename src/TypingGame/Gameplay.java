package TypingGame;

import javafx.scene.transform.Scale;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Date;
import java.util.TimerTask;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

public class Gameplay extends JPanel implements KeyListener, ActionListener {
    private boolean play = false;
    private boolean gameOver = false;
    private boolean newGame = true;
    private int level = 0;

    String[] quotes = {"...he's the hero Gotham deserves, but not the one it needs right now. So, we'll hunt him, because he can take it. Because he's not our hero. He's a silent guardian. A watchful protector. A Dark Knight.",
    "People need dramatic examples to shake them out of apathy, and I can't do that as Bruce Wayne. As a man, I'm flesh and blood; I can be ignored, I can be destroyed. But as a symbol... as a symbol I can be incorruptible. I can be everlasting.",
            "A hero can be anyone. Even a man doing something as simple and reassuring as putting a coat around a young boy's shoulders to let him know that the world hadn't ended.",
            "Yeah, I mean, just gotta play our game out there and keep it simple. Can't take these guys lightly, gotta play a full 60. Get pucks to the net with bodies in front. Good group o' guys in here gettin' pucks in deep, sacrificing the body. Need to come out with 2 points.",
    };

    private int lettersCorrect = 0;
    private int lettersIncorrect = 0;

    private javax.swing.Timer timer;
    private String lettersTyped = "";
    private int delay = 2;

    int secondsElapsed = 0;
    double millisElapsed = 0;
    int wpm, cpm, wpm_final, cpm_final, time_final;

    TextBlockGenerator text = new TextBlockGenerator(quotes[0]);
    private BufferedImage img = null;
    private Image scaledImage;
    private BufferedImage finishImg = null;
    private Image scaledFinishImg;

    java.util.Timer secondsTimer = new java.util.Timer();
    java.util.TimerTask task = new java.util.TimerTask(){

        @Override
        public void run() {
            secondsElapsed++;
          //  System.out.println(secondsElapsed);
            // System.out.println("wpm" + wpm);
            //System.out.println("cpm" + cpm);
        }
    };

    java.util.Timer wpmTimer = new java.util.Timer();
    java.util.TimerTask task_wpm = new java.util.TimerTask(){

        @Override
        public void run() {
            millisElapsed++;
            cpm = (int) (((double) lettersCorrect / millisElapsed)* 60000);
            wpm = (int) (((double) lettersCorrect / millisElapsed)* 60000 / 5);
        }
    };


    public Gameplay(){
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new javax.swing.Timer(delay, this);
        timer.start();
        System.out.println("gameplay initialized");

        //read in car image
        try {
            img = ImageIO.read(new File("C:/TypingGame/src/TypingGame/car.png"));
            finishImg = ImageIO.read(new File("C:/TypingGame/src/TypingGame/finish3.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        scaledImage = img.getScaledInstance(90, 36,Image.SCALE_DEFAULT);
        scaledFinishImg = finishImg.getScaledInstance(64, 64, Image.SCALE_DEFAULT);

    }

    public void paint(Graphics g) {
        requestFocus();
        //background
        g.setColor(Color.black);
        int width = Main.width;
        int height = Main.height;
        g.fillRect(1, 1, width, height);

        if (play) {

            //game over
            if (gameOver){

                g.setColor(Color.red);
                g.setFont(new Font("sans serif", Font.BOLD, 40));
                String endMessage = "YOU WON. Press Enter for next.";
                String stats = "TIME: " + time_final + " WPM: " + wpm_final + " CPM: " + cpm_final;
                g.drawString(endMessage, (width - g.getFontMetrics().stringWidth(endMessage)) / 2 , height / 2);
                g.drawString(stats, (width - g.getFontMetrics().stringWidth(stats)) / 2, height / 2 + g.getFontMetrics().getHeight());

            } else {
                text.draw(g, lettersCorrect,lettersIncorrect);
                g.setColor(Color.white);
                g.setFont(new Font("sans serif", Font.BOLD, 30));
                g.drawString("Time: " + secondsElapsed + "s", width - 200, 100);
                g.drawString("WPM: " + wpm, width - 200, 100 + g.getFontMetrics().getHeight());
                g.drawString("CPM: " + cpm, width - 200, 100 + g.getFontMetrics().getHeight() * 2);

                //letters being typed
                g.setColor(Color.white);
                g.setFont(new Font("sans serif", Font.BOLD, 25));
                g.drawString(lettersTyped, 10, 500);

                //car
                int interval = (width - 275) / text.getLength();
                g.drawImage(scaledImage, 100 + interval * lettersCorrect, 400, this);
                g.drawImage(scaledFinishImg, width - 200, 400, this);
            }
            g.dispose();
        }

        //menu screen
        g.setFont(new Font("sans serif", Font.BOLD, 40));
        g.setColor(Color.green);
        String startMessage = "PRESS ENTER TO START";
        g.drawString(startMessage, (width - g.getFontMetrics().stringWidth(startMessage)) / 2, height / 2);

    }



    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        //TODO: create end sequence when all characters typed

        System.out.println("BEFORE\n " + "letters correct: " + lettersCorrect + "\nletters incorrect: " + lettersIncorrect);

        if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE){
        //2 branches here: one if correct letter backspaced, one if incorrect letter backspaced
            if (lettersTyped.length() == 0) {
            } else {

                if (lettersTyped.charAt(lettersTyped.length() - 1) == ' ') {
                    lettersIncorrect--;
                } else if(lettersIncorrect != 0){
                    lettersIncorrect--;
                } else if (lettersTyped.charAt(lettersTyped.length() - 1) == text.getChar(lettersCorrect + lettersIncorrect - 1)) {
                    System.out.println("correct char backspaced");
                    lettersCorrect--;
                } else {
                    lettersIncorrect--;
                }
                System.out.println("backspace hit");
                lettersTyped = lettersTyped.substring(0, lettersTyped.length() - 1);
                System.out.println(lettersTyped);
            }
        }

        //TODO: Add start countdown sequence while showing textblock
        else if (e.getKeyCode() == KeyEvent.VK_ENTER){
            //two branches, one if first starting, second if starting new game

            //next game
            if (gameOver) {
                gameOver = false;
                secondsElapsed = lettersIncorrect = lettersCorrect = 0;
                millisElapsed = 0;
                level++;
                lettersTyped = "";
                text = new TextBlockGenerator(quotes[level]);

            }
            play = true;
            newGame = true;
            secondsTimer.scheduleAtFixedRate(task, 5000, 1000);
            wpmTimer.scheduleAtFixedRate(task_wpm, 5000, 1);
        }

        else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            System.out.println("spacebar hit");

            if (text.getChar(lettersCorrect) == e.getKeyChar() && lettersIncorrect == 0) {
                lettersTyped = ""; // this resets length to zero so if space is incorrect later on then it wont be able to be backspaced
                lettersCorrect++;
            } else {
                lettersTyped += " ";
                lettersIncorrect++;
            }
        }

        else if (e.getKeyCode() == KeyEvent.VK_CAPS_LOCK || e.getKeyCode() == KeyEvent.VK_SHIFT){
        }

        else {
            System.out.println(e.getKeyChar());
            lettersTyped += e.getKeyChar();
            if (e.getKeyChar() == text.getChar(lettersCorrect) && lettersIncorrect == 0) {
                System.out.println("correct");
                lettersCorrect++;
            } else {
                System.out.println("incorrect");
                lettersIncorrect++;
            }
        }

        //check for game over
        if (lettersCorrect == text.getLength()) {
            time_final = secondsElapsed;
            wpm_final = wpm;
            cpm_final = cpm;
            gameOver = true;
            lettersCorrect = 0;
        }
        System.out.println("AFTER\n " + "letters correct: " + lettersCorrect + "\nletters incorrect: " + lettersIncorrect + "\n ---------------------\n");
    }


    //not needed
    @Override
    public void keyTyped(KeyEvent e) {
    }
    @Override
    public void keyReleased(KeyEvent e) {
    }
}
