package chp38.Core.DailyInformation;

import java.util.ArrayList;

public class FundamentalInformation extends DailyInformation {

    /**
     * ArrayList<String>
     */
    private ArrayList<String> headlines;

    /**
     * ArrayList<String>
     */
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
     * @param headlines ArrayList
     */
    public void setHeadlines(ArrayList<String> headlines) {
        this.headlines = headlines;
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
     * @param headlineSentiments ArrayList
     */
    public void setHeadlineSentiments(ArrayList<String> headlineSentiments) {
        this.headlineSentiments = headlineSentiments;
    }
}