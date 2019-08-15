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

    //TODO: change code so that the text block will indicate with color the words correctly/incorrectly typed
    public void draw(Graphics g, int correct, int incorrect) {

        String[] words = text.split("\\s+");
        String newString = "";
        int splitWidth = 8;
        int prev = 0;
        int yCoord = 100;

        for (int i = splitWidth; i < words.length; i += splitWidth) {
            //words[i] += "\n";
            String line = String.join(" ", Arrays.copyOfRange(words, prev, i));
            newString += (line + "\n");
            //g.drawString(line, 100, yCoord);
            yCoord += 10;
            prev = i;
        }

        String line = String.join(" ", Arrays.copyOfRange(words, prev, words.length));
        newString += line;
        //g.drawString(line, 100, yCoord);
        g.setColor(Color.green);
        g.drawString(newString.substring(0, correct), 100, yCoord);

        g.setColor(Color.white);
        g.drawString(newString.substring(correct, text.length() - incorrect), 100 + g.getFontMetrics().charsWidth(text.substring(0, correct).toCharArray(), 0, correct), yCoord);

        g.setColor(Color.red);
        g.drawString(newString.substring(text.length() - incorrect, text.length()), 100 + g.getFontMetrics().charsWidth(text.substring(0, text.length() - incorrect).toCharArray(), 0, text.length() - incorrect), yCoord);
    }

    public char getChar(int position) {

        return text.charAt(position);
    }

}
