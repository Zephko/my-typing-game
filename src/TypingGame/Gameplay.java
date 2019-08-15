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
    private int wordstyped = 0;
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
    /* //full black background
        //background
        g.setColor(Color.black);
        int width = Main.width;
        int height = Main.height;
        g.fillRect(1, 1, width, height);
    */
        //text block
        text.draw(g);

        //letters being typed
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
        System.out.println(e.getKeyChar());
        lettersTyped += e.getKeyChar();


        if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE && lettersTyped.length() != 0){
            lettersTyped = lettersTyped.substring(0, lettersTyped.length() - 1);
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
