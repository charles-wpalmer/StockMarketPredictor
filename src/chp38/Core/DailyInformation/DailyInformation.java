package chp38.Core.DailyInformation;

import java.util.ArrayList;

public class DailyInformation {

    /**
     * Double dailyLow
     */
    private Double dailyLow;

    /**
     * Double dailyHigh
     */
    private Double dailyHigh;

    /**
     * Double open
     */
    private Double open;

    /**
     * Double close
     */
    private Double close;

    /**
     * ArrayList priceList
     */
    private ArrayList<String> priceList;

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
     * @param dailyLow Double
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
     * @param dailyHigh Souble
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
     * @param open Double
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
     * @param close Double
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
     * @param prices ArrayList
     */
    public void setPriceList(ArrayList<String> prices) {
        this.priceList = prices;

        this.open = Double.parseDouble(prices.get(0));
        this.dailyHigh = Double.parseDouble(prices.get(1));
        this.dailyLow = Double.parseDouble(prices.get(2));
        this.close = Double.parseDouble(prices.get(3));
    }

}
