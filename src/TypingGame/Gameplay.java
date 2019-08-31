package TypingGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileNotFoundException;
import java.util.TimerTask;

public class Gameplay extends JPanel implements KeyListener, ActionListener {
    private boolean play;
    private boolean gameOver;

    private Quotes quotes;

    private int lettersCorrect;
    private int lettersIncorrect;
    private String lettersTyped;

    private javax.swing.Timer timer;
    private int delay;

    private int secondsElapsed;
    private double millisElapsed;
    private int wpm, cpm, wpm_final, cpm_final, time_final;

    private TextBlockGenerator text;
    private Car car;
    private ScreenElements screen = new ScreenElements();

    private java.util.Timer secondsTimer;
    private java.util.TimerTask task;

    private java.util.Timer wpmTimer;
    private java.util.TimerTask task_wpm;


    public Gameplay() throws FileNotFoundException {
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        this.delay = 2;
        timer = new javax.swing.Timer(delay, this);
        timer.start();

        play = false;
        gameOver = false;
        lettersCorrect = 0;
        lettersIncorrect = 0;
        lettersTyped = "";
        quotes = new Quotes();
        text = new TextBlockGenerator(quotes.next());
        car = new Car(100, text.getLength());
        setTimers();

    }

    public void paint(Graphics g) {
        requestFocus();
        screen.printBackground(g);

        if (play) {

            if (gameOver) {
                screen.gameOver(g, time_final, wpm_final, cpm_final);

            } else {
                text.draw(g, lettersCorrect, lettersIncorrect);
                screen.printStats(g, secondsElapsed, wpm, cpm);
                screen.printTypedLetters(g, lettersTyped);
                car.moveCar(g, lettersCorrect);

            }
            g.dispose();
        }

        screen.printMenu(g);

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
            backSpace();
        } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            enter();
        } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            space();
        } else if (e.getKeyCode() == KeyEvent.VK_CAPS_LOCK || e.getKeyCode() == KeyEvent.VK_SHIFT) {
            //no input, skip
        } else {
            checkChar(e);
        }

        //check for game over
        if (lettersCorrect == text.getLength()) {
            time_final = secondsElapsed;
            wpm_final = wpm;
            cpm_final = cpm;
            gameOver = true;
            lettersCorrect = 0;
        }
    }


    //not needed
    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    private void backSpace() {
        if (lettersTyped.length() == 0) {
        } else {
            if (lettersTyped.charAt(lettersTyped.length() - 1) == ' ') {
                lettersIncorrect--;
            } else if (lettersIncorrect != 0) {
                lettersIncorrect--;
            } else if (lettersTyped.charAt(lettersTyped.length() - 1) == text.getChar(lettersCorrect + lettersIncorrect - 1)) {
                lettersCorrect--;
            } else {
                lettersIncorrect--;
            }
            lettersTyped = lettersTyped.substring(0, lettersTyped.length() - 1);
        }
    }

    private void enter() {

        if (gameOver) {
            gameOver = false;
            secondsElapsed = lettersIncorrect = lettersCorrect = 0;
            millisElapsed = 0;
            lettersTyped = "";
            text = new TextBlockGenerator(quotes.next());
            car = new Car(100, text.getLength());

        }
        play = true;
        secondsTimer.cancel();
        wpmTimer.cancel();
        setTimers();
        secondsTimer.scheduleAtFixedRate(task, 0, 1000);
        wpmTimer.scheduleAtFixedRate(task_wpm, 0, 1);
    }

    private void space() {
        if (text.getChar(lettersCorrect) == ' ' && lettersIncorrect == 0) {
            lettersTyped = ""; // this resets length to zero so if space is incorrect later on then it wont be able to be backspaced
            lettersCorrect++;
        } else {
            lettersTyped += " ";
            lettersIncorrect++;
        }
    }

    private void checkChar(KeyEvent e) {
        lettersTyped += e.getKeyChar();
        if (e.getKeyChar() == text.getChar(lettersCorrect) && lettersIncorrect == 0) {
            System.out.println("correct");
            lettersCorrect++;
        } else {
            System.out.println("incorrect");
            lettersIncorrect++;
        }
    }

    private void setTimers() {
        this.secondsElapsed = 0;
        this.millisElapsed = 0;

        this.secondsTimer = new java.util.Timer();
        task = new java.util.TimerTask() {
            @Override
            public void run() {
                secondsElapsed++;
            }
        };

        this.wpmTimer = new java.util.Timer();
        task_wpm = new java.util.TimerTask() {
            @Override
            public void run() {
                millisElapsed++;
                cpm = (int) (((double) lettersCorrect / millisElapsed) * 60000);
                wpm = (int) (((double) lettersCorrect / millisElapsed) * 60000 / 5);
            }
        };


    }
}
