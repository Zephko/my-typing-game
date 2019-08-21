package TypingGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.lang.reflect.Array;
import java.util.Date;
import java.util.TimerTask;
import java.util.Timer;

public class Gameplay extends JPanel implements KeyListener, ActionListener {
    private boolean play = false;
    private boolean gameOver = false;

    private String textblock = "...he's the hero Gotham deserves, but not the one it needs right now. So, we'll hunt him, because he can take it. Because he's not our hero. He's a silent guardian. A watchful protector. A Dark Knight.";
    private int lettersCorrect = 0;
    private int lettersIncorrect = 0;

    private javax.swing.Timer timer;
    private String lettersTyped = "";
    private int delay = 2;

    int secondsElapsed = 0;
    double millisElapsed = 0;
    int wpm, cpm, wpm_final, cpm_final, time_final;

    private TextBlockGenerator text = new TextBlockGenerator(textblock);

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

    public Gameplay(){
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new javax.swing.Timer(delay, this);
        timer.start();

        java.util.Timer wpmTimer = new java.util.Timer();
        java.util.TimerTask task_wpm = new java.util.TimerTask(){

            @Override
            public void run() {
                millisElapsed++;
                cpm = (int) (((double) lettersCorrect / millisElapsed)* 60000);
                wpm = (int) (((double) lettersCorrect / millisElapsed)* 60000 / 5);
            }
        };
        wpmTimer.scheduleAtFixedRate(task_wpm, 0, 1);

        System.out.println("gameplay initialized");

    }

    public void paint(Graphics g) {
        //TODO: Add visual for time and wpm
        //TODO: Add results to end game page
        requestFocus();

        //background
        g.setColor(Color.black);
        int width = Main.width;
        int height = Main.height;
        g.fillRect(1, 1, width, height);

        //TODO: add parameter that tracks how many words correctly typed so far
        if (play) {
            //game over
            if (gameOver){

                g.setColor(Color.red);
                g.setFont(new Font("sans serif", Font.BOLD, 40));
                String endMessage = "YOU WON";
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
            }

            //letters being typed
            g.setColor(Color.white);
            g.setFont(new Font("sans serif", Font.BOLD, 25));
            g.drawString(lettersTyped, 10, 500);

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
        //play = true;
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
                System.out.println(lettersTyped.charAt(lettersTyped.length() - 1));
                System.out.println(text.getChar(lettersCorrect - 1));

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
            play = true;
            secondsTimer.scheduleAtFixedRate(task, 0, 1000);
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
