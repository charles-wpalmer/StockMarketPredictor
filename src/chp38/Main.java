package chp38;

import chp38.APIHandler.AlphaVantage;
import chp38.APIHandler.RedditApi;
import chp38.SentimentAnalysis.SentimentAnalysis;

import java.util.ArrayList;
import java.util.Arrays;

class Handler{

    private SentimentAnalysis SA;

    private AlphaVantage AV;

    public void handleTrainingData(){
        // TODO;
        //  -   Get training data for the model (.arff file)
        //  -   Incrementally run it through NB model
        //  -
    }

    public void prepareData() throws Exception {
        // TODO;
        //  -   Start to build the object, and add in the features
        //      -> Using headlineSentiments
        //      -> prices

        RedditApi Reddit = new RedditApi();

        ArrayList<String> headlines = Reddit.getHeadlines();
        double[] headlineSentiments = new double[25];

        for(int c = 0; c < 25; c++){
            headlineSentiments[c] = this.SA.detectSentiment(headlines.get(c));
        }

        String[] prices = this.AV.getDailyPrices();
        System.out.println(Arrays.toString(headlineSentiments));
    }

    public void run() throws Exception {
        this.SA = new SentimentAnalysis();
        this.AV = new AlphaVantage("DJIA");

        prepareData();
    }

}


public class Main {
    public static void main(String[] args) throws Exception {
        // Build model with training data
        // Prepare data, and run it through the model
        Handler han = new Handler();

        han.run();
    }
}
