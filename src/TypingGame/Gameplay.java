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

    private String textblock = "...he's the hero Gotham deserves, but not the one it needs right now. So, we'll hunt him, because he can take it. Because he's not our hero. He's a silent guardian. A watchful protector. A Dark Knight.";
    private int lettersCorrect = 0;
    private int lettersIncorrect = 0;
    private String lettersTyped = "";

    private javax.swing.Timer timer;
    private int delay = 2;

    int secondsElapsed = 0;
    double millisElapsed = 0;
    int wpm, cpm;

    private TextBlockGenerator text = new TextBlockGenerator(textblock);

    public Gameplay(){
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new javax.swing.Timer(delay, this);
        timer.start();

        java.util.Timer secondsTimer = new java.util.Timer();
        java.util.TimerTask task = new java.util.TimerTask(){

            @Override
            public void run() {
                secondsElapsed++;
                System.out.println(secondsElapsed);
                System.out.println("wpm" + wpm);
                System.out.println("cpm" + cpm);
            }
        };

        secondsTimer.scheduleAtFixedRate(task, 0, 1000);

        java.util.Timer wpmTimer = new java.util.Timer();
        java.util.TimerTask task_wpm = new java.util.TimerTask(){

            @Override
            public void run() {
                millisElapsed++;
                //wpm = (int) (((double) wordsCorrect / millisElapsed) * 60000);
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

        }
        //game over
        if (lettersCorrect == text.getLength()) {
            g.setColor(Color.red);
            g.setFont(new Font("sans serif", Font.BOLD, 40));
            String endMessage = "YOU WON";
            g.drawString(endMessage, (width - g.getFontMetrics().stringWidth(endMessage)) / 2 , height / 2);

        } else {
            text.draw(g, lettersCorrect,lettersIncorrect);
            g.setColor(Color.white);
            g.setFont(new Font("sans serif", Font.BOLD, 30));
            g.drawString("Time: " + secondsElapsed + "s", width - 150, 100);
            g.drawString("WPM: " + wpm, width - 150, 100 + g.getFontMetrics().getHeight());
            g.drawString("CPM: " + cpm, width - 150, 100 + g.getFontMetrics().getHeight() * 2);
        }

        //letters being typed
        g.setColor(Color.white);
        g.setFont(new Font("sans serif", Font.BOLD, 25));
        g.drawString(lettersTyped, 10, 500);

        g.dispose();

    }



    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();
        play = true;
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        //TODO: create end sequence when all characters typed

        if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE){
        //2 branches here: one if correct letter backspaced, one if incorrect letter backspaced
            if (lettersTyped.length() == 0) {
            } else {
                if (lettersTyped.charAt(lettersTyped.length() - 1) == ' ') {
                    lettersIncorrect--;
                } else if (lettersTyped.charAt(lettersTyped.length() - 1) == text.getChar(lettersCorrect - 1)) {
                    System.out.println("correct char backspaces");
                    lettersCorrect--;
                } else {
                    lettersIncorrect--;
                }
                System.out.println("backspace hit");
                lettersTyped = lettersTyped.substring(0, lettersTyped.length() - 1);
                System.out.println(lettersTyped);
            }
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
    }


    //not needed
    @Override
    public void keyTyped(KeyEvent e) {
    }
    @Override
    public void keyReleased(KeyEvent e) {
    }



}
