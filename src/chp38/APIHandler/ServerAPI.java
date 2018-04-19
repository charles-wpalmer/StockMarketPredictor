package chp38.APIHandler;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Class to communicate with the Server API
 *
 * @author charlespalmer
 */
public class ServerAPI {

    /**
     * String to hold the API URL
     */
    private static final String predictionRequestURL = "http://markets.development/api/markets";

    private static final String newsRequestURL = "http://markets.development/api/headlines";

    /**
     * Variable for the connection
     */
    private static HttpURLConnection con;

    /**
     * Method to handle sending the prediction information off to the server
     *
     * @throws IOException
     */
    public static int sendMarketPrediction(String prediction, String commodity) throws IOException {
        URL serverUrl =
                new URL(ServerAPI.predictionRequestURL);
        HttpURLConnection urlConnection = (HttpURLConnection)serverUrl.openConnection();

        urlConnection.setDoOutput(true);
        urlConnection.setRequestMethod("POST");

        BufferedWriter httpRequestBodyWriter =
                new BufferedWriter(new OutputStreamWriter(urlConnection.getOutputStream()));
        httpRequestBodyWriter.write("market_name="+commodity+"&date=" + ServerAPI.getDate() +
                "&prediction="+prediction+"&prev_day_high=100.4&prev_day_low=98.4");

        httpRequestBodyWriter.close();

        Scanner httpResponseScanner = new Scanner(urlConnection.getInputStream());

        String response = "";
        while(httpResponseScanner.hasNextLine()) {
            response = httpResponseScanner.nextLine();
        }

        httpResponseScanner.close();

        return Integer.valueOf(response);
    }

    /**
     * Method to handle sending the news headlines off to the server
     *
     * @throws IOException
     */
    public static void sendNewsHeadlines(int predictionId, ArrayList<String> headlines) throws IOException {
        URL serverUrl =
                new URL(ServerAPI.newsRequestURL);
        HttpURLConnection urlConnection = (HttpURLConnection)serverUrl.openConnection();

        urlConnection.setDoOutput(true);
        urlConnection.setRequestMethod("POST");

        BufferedWriter httpRequestBodyWriter =
                new BufferedWriter(new OutputStreamWriter(urlConnection.getOutputStream()));


        String headlineString = ServerAPI.generateHeadlineString(headlines);
        String str = "date=" + ServerAPI.getDate() + "&prediction_id="+predictionId + headlineString;

        httpRequestBodyWriter.write(str);
        httpRequestBodyWriter.close();

        Scanner httpResponseScanner = new Scanner(urlConnection.getInputStream());
        httpResponseScanner.close();
    }

    /**
     * Method to generate the current date
     *
     * @return String Date
     */
    public static String getDate(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime now = LocalDateTime.now();

        return dtf.format(now);
    }

    /**
     * Method to generate the headline string for the request
     */
    public static String generateHeadlineString(ArrayList<String> headlines){
        StringBuilder string = new StringBuilder();
        int count = 0;

        for(String headline : headlines){
            string.append("&news_headlines["+count+"]=" + headline);

            count++;
        }

        string.deleteCharAt(string.length()-1);

        return string.toString();
    }
}