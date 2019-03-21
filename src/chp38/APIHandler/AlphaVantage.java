package chp38.APIHandler;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Set;

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
     * @return ArrayList of the prices
     * @throws IOException IOe
     * @throws ParseException e
     */
    public ArrayList<String> getDailyPrices() throws IOException, ParseException {
        String data = this.sendRequest();
        JSONParser parser = new JSONParser();

        JSONObject jsonObject = (JSONObject) parser.parse(data);

        if(jsonObject.toString().contains("Error Message")) {
            System.out.println("Incorrect Commodity Entered");
            System.exit(0);
        }

        JSONObject days = (JSONObject) jsonObject.get("Time Series (Daily)");

        Set<String> dates = days.keySet();
        JSONObject lastDay = (JSONObject) days.get(dates.iterator().next());

        Set<String> stats = lastDay.keySet();

        ArrayList<String> dailyPrices = new ArrayList<>();
        for(String stat : stats){
            dailyPrices.add(lastDay.get(stat).toString());
        }

        return dailyPrices;
    }

    /**
     * Function to actually send a request to the AlphaVantage API
     *
     * @return String the response
     * @throws IOException
     */
    private String sendRequest() throws IOException {
        String requestURL = "https://www.alphavantage.co/query?function=TIME_SERIES_DAILY&symbol="+this.commodity+"&apikey=" + this.APIKey;

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
