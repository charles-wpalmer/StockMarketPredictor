package chp38;

import chp38.APIHandler.RedditApi;
import chp38.SentimentAnalysis.SentimentAnalysis;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class Main {
    /**
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
                        //headlines.add(headline[1]);
                        SA.addTestData(headline[1]);
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            //SA.prepareTrainingFiles(headlines);
            SA.evaluateTest();
        } catch (Throwable t) {
            System.out.println("Thrown: " + t);
            t.printStackTrace(System.out);
        }
    }
 */

    public void handleTrainingData(){
        // TODO;
        //  -   Get training data for the model (.arff file)
        //  -   Incrementally run it through NB model
        //  -
    }

    public void prepareData(){
        // TODO;
        //  -   Gather news headlines from reddit (Reddit API handler)
        //  -   Perform Sentiment Analysis (through SA -> HeadlineChunker)
        //  -   Start to build the object, and add in the features

        //SentimentAnalysis SA = new SentimentAnalysis();
        //SA.detectSentiment(headline);
    }

    public static void main(String[] args) throws Exception {
        // Build model with training data
        // Prepare data, and run it through the model

        SentimentAnalysis SA = new SentimentAnalysis();

        //try (BufferedReader br = new BufferedReader(new FileReader("/Users/charlespalmer/Downloads/RedditNews.csv"))) {
            //while ((line = br.readLine()) != null) {

                //String[] headline = line.split(",");
                //if (headline.length > 1) {
                    //SA.detectSentiment("Britain Sounds Allies Out About Invoking NATO Treaty");
                //}
            //}
        //} catch (Exception e){
            //e.printStackTrace();
        //}



        RedditApi r = new RedditApi();

        ArrayList headlines = r.getHeadlines();

        for(int c = 0; c < 25; c++){
            SA.detectSentiment(headlines.get(c).toString());
        }
    }
}
