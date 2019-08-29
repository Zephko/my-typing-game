package TypingGame;

import java.awt.*;

public class ScreenElements {

    public ScreenElements() {

    }

    public void printBackground(Graphics g) {
        g.setColor(Color.black);
        g.fillRect(1, 1, Main.width, Main.height);
    }

    public void gameOver(Graphics g, int timeFinal, int wpmFinal, int cpmFinal) {
        g.setColor(Color.red);
        g.setFont(new Font("sans serif", Font.BOLD, 40));
        String endMessage = "YOU WON. Press Enter for next.";
        String stats = "TIME: " + timeFinal + " WPM: " + wpmFinal + " CPM: " + cpmFinal;
        g.drawString(endMessage, (Main.width - g.getFontMetrics().stringWidth(endMessage)) / 2 , Main.height / 2);
        g.drawString(stats, (Main.width - g.getFontMetrics().stringWidth(stats)) / 2, Main.height / 2 + g.getFontMetrics().getHeight());
    }

    public void printStats(Graphics g, int seconds, int wpm, int cpm) {
        g.setColor(Color.white);
        g.setFont(new Font("sans serif", Font.BOLD, 30));
        g.drawString("Time: " + seconds + "s", Main.width - 200, 100);
        g.drawString("WPM: " + wpm, Main.width - 200, 100 + g.getFontMetrics().getHeight());
        g.drawString("CPM: " + cpm, Main.width - 200, 100 + g.getFontMetrics().getHeight() * 2);
    }

    public void printTypedLetters(Graphics g, String lettersTyped) {
        g.setColor(Color.white);
        g.setFont(new Font("sans serif", Font.BOLD, 25));
        g.drawString(lettersTyped, 10, 500);
    }

    public void printMenu(Graphics g) {
        g.setFont(new Font("sans serif", Font.BOLD, 40));
        g.setColor(Color.green);
        String startMessage = "PRESS ENTER TO START";
        g.drawString(startMessage, (Main.width - g.getFontMetrics().stringWidth(startMessage)) / 2, Main.height / 2);
    }
}
