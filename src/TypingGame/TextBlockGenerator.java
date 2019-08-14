package TypingGame;

import java.awt.*;
import java.util.Arrays;

public class TextBlockGenerator {

    public String text;

    public TextBlockGenerator(String textblock) {
        //TODO: set font size
        //TODO: set coordinates

        //
        text = textblock;

    }

    public void draw(Graphics g) {

        String[] words = text.split("\\s+");
        int splitWidth = 8;
        int prev = 0;
        int yCoord = 100;
        g.setColor(Color.black);

        for (int i = splitWidth; i < words.length; i += splitWidth) {
            //words[i] += "\n";
            String line = String.join(" ", Arrays.copyOfRange(words, prev, i));
            g.drawString(line, 100, yCoord);
            yCoord += 10;
            prev = i;
        }

        String line = String.join(" ", Arrays.copyOfRange(words, prev, words.length));
        g.drawString(line, 100, yCoord);
    }

}
