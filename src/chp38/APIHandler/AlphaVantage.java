package chp38.APIHandler;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *  A class to handle the API calls to Alpha vantage to gain
 *  Stock market prices.
 *
 * @author Charles Palmer
 * @since 2018-13-02
 */
public class AlphaVantage {

    /**
     * The key to be used for the Alpha Vantage API
     */
    private final String APIKey = "YC8VVZQTBQZPJG27";

    /**
     * The commodity being analysed, i.e DJIA
     */
    private String commodity;

    /**
     * Constructor
     * @param commodity what commodity to get data for
     */
    public AlphaVantage(String commodity){
        this.commodity = commodity;
    }

    /**
     * Get the past daily prices for a given comodity
     *
     */
    public String[] getDailyPrices() throws IOException, ParseException {
        JSONParser parser = new JSONParser();

        Object object = parser.parse(this.sendRequest());

        JSONObject jsonObject = (JSONObject) object;

        object = parser.parse(jsonObject.get("Time Series (Daily)").toString());
        jsonObject = (JSONObject) object;

        object = parser.parse(jsonObject.get("2018-03-15").toString());
        jsonObject = (JSONObject) object;

        String[] dailyPrices = new String[5];
        dailyPrices[0] = jsonObject.get("1. open").toString();
        dailyPrices[1] = jsonObject.get("2. high").toString();
        dailyPrices[2] = jsonObject.get("3. low").toString();
        dailyPrices[3] = jsonObject.get("4. close").toString();
        dailyPrices[4] = jsonObject.get("5. volume").toString();

        return dailyPrices;
    }

    /**
     * Function to actually send a request to the AlphaVantage API
     *
     * @return String the response
     * @throws IOException
     */
    private String sendRequest() throws IOException {
        String requestURL = "https://www.alphavantage.co/query?function=TIME_SERIES_DAILY&symbol="+this.commodity+"&apikey=YC8VVZQTBQZPJG27";

        URL obj = new URL(requestURL);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        con.setRequestMethod("GET");

        int responseCode = con.getResponseCode();

        if (responseCode == 200) {

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine = "";
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }

            in.close();

            return response.toString();
        } else {
            throw new RuntimeException();
        }
    }

    /**
     * Get the past simple moving average for a given
     * comodity
     *
     */
    public void getSMA(){

    }
}
