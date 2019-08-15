package TypingGame;

import javax.swing.*;
import java.awt.*;

public class Main {

    static int width;
    static int height;

    public static void main(String[] args){
        JFrame obj = new JFrame();
        Gameplay gamePlay = new Gameplay();

        Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();

        width = screensize.width;
        height = screensize.height;

        obj.setBounds(10, 10, width, height);
        obj.setTitle("Typing Game");
        obj.setVisible(true);
        obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        obj.add(gamePlay);

        System.out.println("setup done");

    }
}
