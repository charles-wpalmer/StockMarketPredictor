package chp38;

import chp38.SentimentAnalysis.SentimentAnalysis;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        String[] dir = new String[1];
        dir[0] = "./";

        //dir[0] = "/Users/charlespalmer/Downloads/RedditNews.csv";

        try {
            SentimentAnalysis SA = new SentimentAnalysis(dir[0]);
            SA.handleFromFolder(dir[0]);
            String line = "";
            ArrayList headlines = new ArrayList();

            try (BufferedReader br = new BufferedReader(new FileReader("/Users/charlespalmer/Downloads/RedditNews.csv"))) {

                while ((line = br.readLine()) != null) {

                    String[] headline = line.split(",");

                    if(headline.length > 1) {
                        headlines.add(headline[1]);
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            SA.prepareTrainingFiles(headlines);
            SA.evaluateTest();
        } catch (Throwable t) {
            System.out.println("Thrown: " + t);
            t.printStackTrace(System.out);
        }
    }
}
