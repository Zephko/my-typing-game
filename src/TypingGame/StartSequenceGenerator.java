package TypingGame;

import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

public class StartSequenceGenerator {

    public void StartSequenceGenerator(Graphics g, TextBlockGenerator textblock, int level, int secondsLeft){

        g.setColor(Color.black);
        int width = Main.width;
        int height = Main.height;
        g.fillRect(1, 1, width, height);
        textblock.draw(g, 0, 0);

        g.setFont(new Font("sans serif", Font.BOLD, 30));
        g.setColor(Color.green);
        g.drawString(Integer.toString(secondsLeft), width - 200, 100);
    }
}

/* FAILED COUNTDOWN CODE
        if (newGame){
            newGame = false;
            //set black background
            g.drawRect(1,1, width, height);
            //add text
            text.draw(g, 0, 0);
            //add level
            g.drawString("Level: " + level, 100, height - 200);
            for (int countdown = 5; countdown > 0; countdown--){
                //change number
                g.setFont(new Font("sans serif", Font.BOLD, 30));
                g.setColor(Color.green);
                g.drawString(Integer.toString(countdown), width - 200, 100);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                repaint();
            }
        }
 */