package TypingGame;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class TextBlockGenerator {

    public String text;
    int fontSize = 25;

    public TextBlockGenerator(String textblock) {

        text = textblock;

    }

    //TODO: change code so that the text block will indicate with color the words correctly/incorrectly typed
    public void draw(Graphics g, int correct, int incorrect) {

        String[] words = text.split("\\s+");
        String newString = "";
        int splitWidth = 8;
        int prev = 0;
        int xCoord = 100;
        int yCoord = 100;

        for (int i = splitWidth; i < words.length; i += splitWidth) {
            //words[i] += "\n";
            String line = String.join(" ", Arrays.copyOfRange(words, prev, i));
            newString += (line + " \n");
            //g.drawString(line, 100, yCoord);
            yCoord += 10;
            prev = i;
        }

        String line = String.join(" ", Arrays.copyOfRange(words, prev, words.length));
        newString += line;

        g.setFont(new Font("sans serif", Font.PLAIN, fontSize));
        drawString(g, newString, xCoord, yCoord, correct, incorrect);
    }

    public void drawString(Graphics g, String text, int x, int y, int correct, int incorrect) {
        int letterCount = 0;
        int previousLinesLetters;
        boolean leftoverRed = false;
        int leftoverIncorrect = 0;

        for (String line : text.split("\n")) {
            previousLinesLetters = letterCount;
            letterCount += line.length();

            if (leftoverRed) {
                g.setColor(Color.white);
                g.drawString(line, x, y);

                g.setColor(Color.red);
                g.drawString(line.substring(0, leftoverIncorrect), x, y);
                leftoverRed = false;
            }

            else if (correct >= letterCount) {
                g.setColor(Color.green);
                g.drawString(line, x, y);
            }

            else if (previousLinesLetters >= correct) {
                g.setColor(Color.white);
                g.drawString(line, x, y);
            }

            else {
                //split this line into green and white accordingly
                int numGreenLetters = correct - previousLinesLetters;
                //print greens
                g.setColor(Color.green);
                g.drawString(line.substring(0, numGreenLetters), x, y);

                int greenLettersWidth = g.getFontMetrics().charsWidth(line.substring(0, numGreenLetters).toCharArray(), 0, numGreenLetters);

                //print whites
                g.setColor(Color.white);
                g.drawString(line.substring(numGreenLetters), x + greenLettersWidth, y);

                //print reds
                int index;
                if (numGreenLetters + incorrect > line.length()){
                    //leftover incorrects should go to next line
                    index = line.length();
                    leftoverIncorrect = numGreenLetters + incorrect - line.length();
                    leftoverRed = true;

                } else{
                    index = numGreenLetters + incorrect;
                }
                g.setColor(Color.red);
                g.drawString(line.substring(numGreenLetters, index), x + greenLettersWidth, y);

            }
            y += g.getFontMetrics().getHeight();
        }
    }

    public char getChar(int position) {
        return text.charAt(position);
    }

    public int getLength() {
        return text.length();
    }

}
