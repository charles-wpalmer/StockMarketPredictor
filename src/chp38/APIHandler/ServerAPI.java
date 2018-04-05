package chp38.APIHandler;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * Class to communicate with the Server API
 *
 * @author charlespalmer
 */
public class ServerAPI {

    /**
     * String to hold the API URL
     */
    private final String requestURL = "http://markets.development/api/markets";

    /**
     * Variable for the connection
     */
    private static HttpURLConnection con;

    /**
     * Method to handle sending the prediction information off to the server
     * 
     * @throws IOException
     */
    public void sendMarketPrediction() throws IOException {

        URL url = new URL(this.requestURL);
        URLConnection conn = url.openConnection();
        conn.setDoOutput(true);
        OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());

        writer.write("market_name=Coca&date=2018-04-04&prediction=Increase&prev_day_high=100.4&prev_day_low=98.4");
        writer.flush();

        writer.close();
    }
}
