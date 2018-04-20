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

    public ArrayList<String> getHeadlines() {
        return headlines;
    }

    public void setHeadlines(ArrayList<String> headlines) {
        this.headlines = headlines;
    }

    public Double getDailyLow() {
        return dailyLow;
    }

    public void setDailyLow(Double dailyLow) {
        this.dailyLow = dailyLow;
    }

    public Double getDailyHigh() {
        return dailyHigh;
    }

    public void setDailyHigh(Double dailyHigh) {
        this.dailyHigh = dailyHigh;
    }

    public Double getOpen() {
        return open;
    }

    public void setOpen(Double open) {
        this.open = open;
    }

    public Double getClose() {
        return close;
    }

    public void setClose(Double close) {
        this.close = close;
    }

    public ArrayList<String> getPriceList() {
        return priceList;
    }

    public void setPriceList(ArrayList<String> prices) {
        this.priceList = prices;

        this.open = Double.parseDouble(prices.get(0));
        this.dailyHigh = Double.parseDouble(prices.get(1));
        this.dailyLow = Double.parseDouble(prices.get(2));
        this.close = Double.parseDouble(prices.get(3));
    }

    public ArrayList<String> getHeadlineSentiments() {
        return headlineSentiments;
    }

    public void setHeadlineSentiments(ArrayList<String> headlineSentiments) {
        this.headlineSentiments = headlineSentiments;
    }
}
