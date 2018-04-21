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
    private String Commodity;

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
     * Variable to hold the DailyInformation class
     */
    private DailyInformation dailyInfo;

    public AppHandler(String comodity){
        this.Commodity = comodity;

        this.weka = new WekaHandler();
        this.SA = new SentimentAnalysis();
        this.AV = new AlphaVantage(this.Commodity);
        this.dailyInfo = new DailyInformation();
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

        this.dailyInfo.setHeadlines(Reddit.getHeadlines());

        this.buildArffFile(this.dailyInfo.getHeadlines());
    }

    /**
     * Method to prepare the training data. Parses the file provided by the user
     *
     * @param file
     * @throws Exception
     */
    private void prepareData(String file) throws Exception {

        this.dailyInfo.setHeadlines(CSVFileReader.readUserNewsFile(file));

        this.buildArffFile(this.dailyInfo.getHeadlines());

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
        ArrayList<String> headlineSentiments = new ArrayList();

        for(int c = 0; c < 25; c++){
            headlineSentiments.add(String.valueOf(this.SA.detectSentiment(headlines.get(c))));
        }

        this.dailyInfo.setHeadlineSentiments(headlineSentiments);
        this.dailyInfo.setPriceList(this.AV.getDailyPrices());

        ArrayList<String> prices = this.dailyInfo.getPriceList();

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

        this.handleInputs(news, newsSource);
    }

    /**
     * Handle the user inputs from the menu, and run the appropriate methods.
     *
     * @param news
     * @param newsFile
     * @throws Exception
     */
    private void handleInputs(int news, String newsFile) throws Exception {
        if(news == 1){
            prepareData(newsFile);
        } else {
            prepareData();
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

        String prediction = this.runWeka();

        this.handleOutput(prediction);
    }

    /**
     *
     * @param prediction
     * @throws IOException
     */
    private void handleOutput(String prediction) throws IOException {
        int predictionId = ServerAPI.sendMarketPrediction(prediction, this.Commodity,
                this.dailyInfo.getDailyHigh(), this.dailyInfo.getDailyLow());

        ServerAPI.sendNewsHeadlines(predictionId, this.dailyInfo.getHeadlines(), this.dailyInfo.getHeadlineSentiments());

        System.out.println("Prediction for " + this.Commodity + " is: " + prediction);
    }

    /**
     * Method to load the training data into Weka and classify the new data.
     *
     * @return String Prediction
     * @throws Exception
     */
    private String runWeka() throws Exception {
        this.weka.loadAttributes(this.trainingFile);

        String prediction = this.weka.classifyData(this.testFile);

        return prediction;
    }
}
