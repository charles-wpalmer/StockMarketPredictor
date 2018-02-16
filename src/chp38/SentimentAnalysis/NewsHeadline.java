package chp38.SentimentAnalysis;

public class NewsHeadline {

    String headline;
    String classification;

    public NewsHeadline(String text, String classification){
        this.headline = text;
        this.classification = classification;
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }
}
