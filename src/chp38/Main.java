package chp38;

import chp38.APIHandler.AlphaVantage;
import chp38.APIHandler.RedditApi;
import chp38.Files.WekaFileWriter;
import chp38.SentimentAnalysis.SentimentAnalysis;

import java.util.ArrayList;

class Handler{

    /**
     * Class SentimentAnalysis
     */
    private SentimentAnalysis SA;

    /**
     * Class AlphaVantage
     */
    private AlphaVantage AV;

    /**
     * Method to handle/generate the training data
     */
    public void handleTrainingData(){
        // TODO;
        //  -   Get training data for the model (.arff file)
        //  -   Incrementally run it through NB model
        //  -
    }

    /**
     * Method to prepare
     * @throws Exception
     */
    public void prepareData() throws Exception {
        // TODO;
        //  -   Start to build the object, and add in the features
        //      -> Using headlineSentiments
        //      -> prices

        RedditApi Reddit = new RedditApi();

        ArrayList<String> headlines = Reddit.getHeadlines();
        ArrayList<Double> headlineSentiments = new ArrayList();

        for(int c = 0; c < 25; c++){
            headlineSentiments.add(this.SA.detectSentiment(headlines.get(c)));
        }

        ArrayList<Object> prices = this.AV.getDailyPrices();

        prices.addAll(headlineSentiments);
        prices.add("increase");

        WekaFileWriter.generateTestArfFile("Test.arf", prices, true);
    }

    /**
     * Method to start running the program
     * @throws Exception
     */
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
