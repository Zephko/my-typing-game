package TypingGame;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Car {

    private int start;
    private int end;
    private BufferedImage carRawImg;
    private BufferedImage finishRawImg;
    private Image car;
    private Image finishLine;
    private int y;
    private int textLength;


    public Car(int start, int textLength) {
        this.start = start;
        this.y = 400;
        this.textLength = textLength;

        //load in images
        try {
            carRawImg = ImageIO.read(new File("C:/TypingGame/src/TypingGame/car.png"));
            finishRawImg = ImageIO.read(new File("C:/TypingGame/src/TypingGame/finish3.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.car = scaleDownCar(90, 36);
        this.finishLine = scaleDownFinish(64, 64);

    }

    private Image scaleDownCar(int width, int height) {
        //recommended 90 x 36
        return carRawImg.getScaledInstance(width, height, Image.SCALE_DEFAULT);
    }

    private Image scaleDownFinish(int width, int height) {
        //recommended 64 x 64
        return finishRawImg.getScaledInstance(width, height, Image.SCALE_DEFAULT);
    }

//    public void drawStart(Graphics g) {
//        g.drawImage(car, start, y, null);
//        g.drawImage(finishLine, start + Main.width - 250, y, null);
//        System.out.println("drawing...");
//    }

    public void moveCar(Graphics g, int lettersCorrect) {
        if (lettersCorrect == 0) {
            g.drawImage(car, start, y, null);
            g.drawImage(finishLine, start + Main.width - 250, y, null);

        }

        int interval = (Main.width - 250) / textLength;
        g.drawImage(car, start + (interval * lettersCorrect), y, null);
        g.drawImage(finishLine, start + Main.width - 250, y, null);
    }

}
