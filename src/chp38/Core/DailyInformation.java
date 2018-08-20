package chp38.Core;

import java.util.ArrayList;

public class DailyInformation {
    private ArrayList<String> headlines;

    private Double dailyLow;

    private Double dailyHigh;

    private Double open;

    private Double close;

    private ArrayList<String> priceList;

    private ArrayList<String> headlineSentiments;

    /**
     * Get the list of headlines
     *
     * @return List of headlines
     */
    public ArrayList<String> getHeadlines() {
        return headlines;
    }

    /**
     * Set the list of headlines
     *
     * @param headlines
     */
    public void setHeadlines(ArrayList<String> headlines) {
        this.headlines = headlines;
    }

    /**
     * Get the previous daily low of the market
     *
     * @return The daily low of market being analysed
     */
    public Double getDailyLow() {
        return dailyLow;
    }

    /**
     * Set the previous daily low for the market
     *
     * @param dailyLow
     */
    public void setDailyLow(Double dailyLow) {
        this.dailyLow = dailyLow;
    }

    /**
     * Get the previous daily high for the market
     *
     * @return The daily high of market being analysed
     */
    public Double getDailyHigh() {
        return dailyHigh;
    }

    /**
     * Set the previous daily high of the market
     *
     * @param dailyHigh
     */
    public void setDailyHigh(Double dailyHigh) {
        this.dailyHigh = dailyHigh;
    }

    /**
     * Get the previous dat open of the market
     *
     * @return The previous day open of market being analysed
     */
    public Double getOpen() {
        return open;
    }

    /**
     * Set the previous day open of the market
     *
     * @param open
     */
    public void setOpen(Double open) {
        this.open = open;
    }

    /**
     * Get the previous day close of the market
     *
     * @return The previous day close of market being analysed
     */
    public Double getClose() {
        return close;
    }

    /**
     * Set the previous day close of the market
     *
     * @param close
     */
    public void setClose(Double close) {
        this.close = close;
    }

    /**
     * Get the list of prices for the previous day
     *
     * @return list of the market prices
     */
    public ArrayList<String> getPriceList() {
        return priceList;
    }

    /**
     * Set the list of prices for the previous day
     *
     * @param prices
     */
    public void setPriceList(ArrayList<String> prices) {
        this.priceList = prices;

        this.open = Double.parseDouble(prices.get(0));
        this.dailyHigh = Double.parseDouble(prices.get(1));
        this.dailyLow = Double.parseDouble(prices.get(2));
        this.close = Double.parseDouble(prices.get(3));
    }

    /**
     * Return a list of sentiments associated with the news headlines
     *
     * @return list of the sentiments
     */
    public ArrayList<String> getHeadlineSentiments() {
        return headlineSentiments;
    }

    /**
     * Set the list of headlines sentiments
     *
     * @param headlineSentiments
     */
    public void setHeadlineSentiments(ArrayList<String> headlineSentiments) {
        this.headlineSentiments = headlineSentiments;
    }
}
