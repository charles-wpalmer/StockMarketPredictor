package chp38.Core;

import chp38.APIHandler.AlphaVantage;
import chp38.APIHandler.RedditApi;
import chp38.Files.WekaFileWriter;
import chp38.SentimentAnalysis.SentimentAnalysis;
import chp38.WEKA.WekaHandler;

import java.util.ArrayList;

public class AppHandler{

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
        RedditApi Reddit = new RedditApi();

        ArrayList<String> headlines = Reddit.getHeadlines();
        ArrayList<Double> headlineSentiments = new ArrayList();

        for(int c = 0; c < 25; c++){
            headlineSentiments.add(this.SA.detectSentiment(headlines.get(c)));
        }

        ArrayList<Object> prices = this.AV.getDailyPrices();

        prices.addAll(headlineSentiments);
        prices.add("decrease");

        WekaFileWriter.generateTestArfFile("unlabelled.arff", prices, true);
    }

    /**
     * Method to start running the program
     * @throws Exception
     */
    public void run() throws Exception {
        this.SA = new SentimentAnalysis();
        this.AV = new AlphaVantage("DJIA");

        prepareData();

        WekaHandler weka = new WekaHandler();

        weka.buildModel();
        weka.classifyData();
    }

}
