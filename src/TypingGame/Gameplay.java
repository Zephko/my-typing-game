package TypingGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.lang.reflect.Array;

public class Gameplay extends JPanel implements KeyListener, ActionListener {
    private boolean play = false;

    private String textblock = "...he's the hero Gotham deserves, but not the one it needs right now. So, we'll hunt him, because he can take it. Because he's not our hero. He's a silent guardian. A watchful protector. A Dark Knight.";
    private int lettersCorrect = 0;
    private int lettersIncorrect = 0;
    private String lettersTyped = "";

    private Timer timer;
    private int delay = 2;

    private TextBlockGenerator text = new TextBlockGenerator(textblock);

    public Gameplay(){
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay, this);
        timer.start();

    }

    public void paint(Graphics g) {
    //full black background
        //background
        g.setColor(Color.black);
        int width = Main.width;
        int height = Main.height;
        g.fillRect(1, 1, width, height);

        //text block
        //TODO: add parameter that tracks how many words correctly typed so far
        text.draw(g, lettersCorrect,lettersIncorrect);

        //letters being typed
        g.setColor(Color.white);
        g.setFont(new Font("sans serif", Font.BOLD, 25));
        g.drawString(lettersTyped, 10, 500);

        g.dispose();

    }



    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        //TODO: create end sequence when all characters typed

        if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE && lettersTyped.length() != 0){
        //2 branches here: one if correct letter backspaced, one if incorrect letter backspaced
            if (lettersTyped.charAt(lettersTyped.length() - 1) == ' '){

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

        else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            System.out.println("spacebar hit");

            //if current word matches next word in text
            if (text.getChar(lettersCorrect) == e.getKeyChar()) {
                lettersTyped = "";
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
            if (e.getKeyChar() == text.getChar(lettersCorrect)) {
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
