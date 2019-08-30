package TypingGame;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

public class Quotes {
    private ArrayList<String> quotes;
    private File file;
    private Scanner sc;
    private String path;
    private Random random;
    private ArrayList<Integer> randomOrder;
    private int index;

    public Quotes() throws FileNotFoundException{
        this.quotes = new ArrayList<String>();
        this.path = "C:\\TypingGame\\src\\TypingGame\\quotes.txt";
        this.random = new Random();
        this.index = 0;
        fillList();
        shuffle();  //sets random order
    }

    private void fillList() throws FileNotFoundException {

        file = new File(path);
        sc = new Scanner(file);

        while(sc.hasNextLine()) {
            quotes.add(sc.nextLine());
        }
    }

    public ArrayList<String> getQuotes() {
        return quotes;
    }

    private void shuffle() {
        randomOrder = new ArrayList<Integer>();
        for (int i = 0; i < quotes.size(); i++) {
            randomOrder.add(i);
        }
        Collections.shuffle(randomOrder);
    }

    public String next() {
        System.out.println(randomOrder);
        String quote = quotes.get(randomOrder.get(index));
        index++;
        return quote;
    }
}
