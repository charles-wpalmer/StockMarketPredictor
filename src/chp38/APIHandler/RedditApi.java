package chp38.APIHandler;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Class to handle the Reddit API, to be abel to pull news headlines from Reddit
 *
 * @author Charles Palmer
 */
public class RedditApi {

    /**
     * User agent for the request
     */
    private final String USER_AGENT = "Mozilla/5.0";

    /**
     * Request URL - where to get the news headlines from
     */
    private final String requestURL = "https://www.reddit.com/r/worldnews/.json";

    /**
     * Method to generate a list of the headlines from Reddit.
     *
     * @return ArrayList
     * @throws Exception
     */
    public ArrayList<String> getHeadlines() throws Exception {

        JSONParser parser = new JSONParser();

        Object object = parser.parse(this.sendRequest());

        JSONObject jsonObject = (JSONObject) object;

        object = parser.parse(jsonObject.get("data").toString());

        jsonObject = (JSONObject) object;

        JSONArray list = (JSONArray) jsonObject.get("children");

        ArrayList<String> headlineList = new ArrayList<>();
        for(int c = 0; c < list.size(); c++){
            object = parser.parse(list.get(c).toString());

            jsonObject = (JSONObject) object;

            object = parser.parse(jsonObject.get("data").toString());

            jsonObject = (JSONObject) object;

            headlineList.add(jsonObject.get("title").toString());
        }

        return headlineList;

    }

    /**
     * Method to actually send the request to Reddit world news, top 25 news headlines of the past
     * 24 hours.
     *
     * @return String
     * @throws Exception
     */
    private String sendRequest() throws Exception {
        URL obj = new URL(this.requestURL);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        con.setRequestMethod("GET");

        con.setRequestProperty("User-Agent", USER_AGENT);

        int responseCode = con.getResponseCode();

        if (responseCode == 200) {

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine, response = "";

            while ((inputLine = in.readLine()) != null) {
                response = inputLine;
            }

            in.close();

            return response;
        } else {
            throw new RuntimeException();
        }

    }


}
