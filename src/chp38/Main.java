package chp38;

import chp38.SentimentAnalysis.SentimentAnalysis;

public class Main {
    public static void main(String[] args) {
        String[] dir = new String[1];
        dir[0] = "./";
        try {
            new SentimentAnalysis(dir).run();
        } catch (Throwable t) {
            System.out.println("Thrown: " + t);
            t.printStackTrace(System.out);
        }
    }
}
