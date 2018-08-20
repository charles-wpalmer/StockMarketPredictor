package chp38.APIHandler;

import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.rmi.ServerError;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class APITest {

    @Test
    void testDailyPricesSize() throws IOException, ParseException {
        AlphaVantage AV = new AlphaVantage("DJIA");

        ArrayList prices = AV.getDailyPrices();

        assertEquals(prices.size(), 5);
    }

    @Test
    void testHeadlinesSize() throws Exception {
        RedditApi Reddit = new RedditApi();

        ArrayList headlines = Reddit.getHeadlines();

        assertEquals(headlines.size(), 25);
    }

    @Test
    void testSendMarketData() throws IOException {
        int id = ServerAPI.sendMarketPrediction("decrease", "DJIA", 5.0, 4.0);

        assertTrue(id > 0);
    }
}