package chp38.APIHandler;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
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
    private static final String requestURL = "http://markets.development/api/markets";

    /**
     * Variable for the connection
     */
    private static HttpURLConnection con;

    /**
     * Method to handle sending the prediction information off to the server
     *
     * @throws IOException
     */
    public static void sendMarketPrediction(String prediction, String commodity) throws IOException {
        URL serverUrl =
                new URL(ServerAPI.requestURL);
        HttpURLConnection urlConnection = (HttpURLConnection)serverUrl.openConnection();

        urlConnection.setDoOutput(true);
        urlConnection.setRequestMethod("POST");

        BufferedWriter httpRequestBodyWriter =
                new BufferedWriter(new OutputStreamWriter(urlConnection.getOutputStream()));
        httpRequestBodyWriter.write("market_name="+commodity+"&date=2018-04-04&prediction="+prediction+"&prev_day_high=100.4&prev_day_low=98.4");
        httpRequestBodyWriter.close();

        Scanner httpResponseScanner = new Scanner(urlConnection.getInputStream());
        httpResponseScanner.close();
    }
}