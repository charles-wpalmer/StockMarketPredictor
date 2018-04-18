package chp38.SentimentAnalysis;

public class NewsHeadline {

    /**
     * VString to hold the name of the market.
     */
    private String market;

    public NewsHeadline(String market){
        this.market = market;
    }

    /**
     * Get the market being dealt with
     * @return market
     */
    public String getMarket() {
        return market;
    }

    /**
     * Set the market being dealt with
     * @param market
     */
    public void setMarket(String market) {
        this.market = market;
    }
}
