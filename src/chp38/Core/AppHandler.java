package chp38.Core;

import chp38.APIHandler.AlphaVantage;
import chp38.APIHandler.RedditApi;
import chp38.APIHandler.ServerAPI;
import chp38.Files.CSVFileReader;
import chp38.Files.WekaFileWriter;
import chp38.SentimentAnalysis.SentimentAnalysis;
import chp38.WEKA.WekaHandler;
import org.json.simple.parser.ParseException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class AppHandler{

    /**
     * Variable to hold the name of the Comodity/market to predict
     */
    private String Comodity;

    /**
     * Class SentimentAnalysis
     */
    private SentimentAnalysis SA;

    /**
     * Class AlphaVantage
     */
    private AlphaVantage AV;

    /**
     * Weka Handler
     */
    private WekaHandler weka;

    /**
     * Variable to hold the path to training data.
     */
    private String trainingFile = "./labelled.arff";

    /**
     * Variable to hold the path to training data.
     */
    private String redditNews = "./RedditNews.csv";

    /**
     * Variable to hold the path to test data.
     */
    private String testFile = "./unlabelled.arff";

    /**
     * List of news headlines
     */
    private ArrayList<String> NewsHeadlines;

    public AppHandler(String comodity){
        this.Comodity = comodity;

        this.weka = new WekaHandler();
        this.SA = new SentimentAnalysis();
        this.AV = new AlphaVantage(this.Comodity);
    }

    /**
     * Method to handle/generate the training data
     */
    public void handleTrainingData() throws IOException {
        ArrayList<String> objects = CSVFileReader.readNewsFile(this.redditNews, this.SA);

        WekaFileWriter.generateTrainingArfFile(this.trainingFile, objects);

    }

    /**
     * Method to prepare the training data. Pulls data from the Reddit API
     *
     * @throws Exception
     */
    private void prepareData() throws Exception {
        RedditApi Reddit = new RedditApi();

        this.NewsHeadlines = Reddit.getHeadlines();

        this.buildArffFile(this.NewsHeadlines);
    }

    /**
     * Method to prepare the training data. Parses the file provided by the user
     *
     * @param file
     * @throws Exception
     */
    private void prepareData(String file) throws Exception {

        this.NewsHeadlines = CSVFileReader.readUserNewsFile(file);

        this.buildArffFile(this.NewsHeadlines);

    }

    /**
     * Method to build the arff file. Detects the sentiment of the provided news headlines,
     * and combines it with the stock market price information.
     *
     * @param headlines
     * @throws IOException
     * @throws ParseException
     */
    private void buildArffFile(ArrayList<String> headlines) throws IOException, ParseException {
        ArrayList<Double> headlineSentiments = new ArrayList();

        for(int c = 0; c < 25; c++){
            headlineSentiments.add(this.SA.detectSentiment(headlines.get(c)));
        }

        ArrayList<Object> prices = this.AV.getDailyPrices();

        prices.addAll(headlineSentiments);
        prices.add("decrease");

        WekaFileWriter.generateTestArfFile(this.testFile, prices);
    }

    /**
     * Display the menu, to gain user inputs.
     *
     * @throws Exception
     */
    public void displayMenu() throws Exception {
        Scanner reader = new Scanner(System.in);
        String newsSource = "";

        System.out.println("Would you like to provide your own News Headlines? (0 for no 1 for yes)");
        int news = reader.nextInt();

        if(news == 1){
            reader.nextLine();
            System.out.println("Please provide the path to the file");
            newsSource = reader.nextLine();
        }


        System.out.println("Would you like to provide your own Training Data? (0 for no 1 for yes)");
        int training = reader.nextInt();

        if(training == 1){
            reader.nextLine();
            System.out.println("Please provide the path to the file");
            this.trainingFile = reader.nextLine();
        }

        this.handleInputs(news, training, newsSource);
    }

    /**
     * Handle the user inputs from the menu, and run the appropriate methods.
     *
     * @param news
     * @param training
     * @param newsFile
     * @throws Exception
     */
    private void handleInputs(int news, int training, String newsFile) throws Exception {
        if(news == 1){
            prepareData(newsFile);
        } else {
            prepareData();
        }

        if(training == 1){
            this.weka.loadAttributes(this.trainingFile);
        } else {
            this.weka.loadAttributes(this.trainingFile);
        }
    }

    /**
     * Method to start running the program. Prepares training and test data
     * from the given files, then builds the model using weka, and classifys
     * the data.
     *
     * @throws Exception
     */
    public void run() throws Exception {
        this.displayMenu();

        String prediction = this.weka.classifyData();

        int predictionId = ServerAPI.sendMarketPrediction(prediction, this.Comodity);

        ServerAPI.sendNewsHeadlines(predictionId, this.NewsHeadlines);

    }

}
