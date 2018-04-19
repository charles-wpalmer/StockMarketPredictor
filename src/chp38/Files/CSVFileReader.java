package chp38.Files;

import chp38.SentimentAnalysis.SentimentAnalysis;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class CSVFileReader {

    /**
     * Read a text file provided by the user with the news headlines to use.
     *
     * @param file
     * @return headlines
     */
    public static ArrayList<String> readNewsFile(String file, SentimentAnalysis SA){

        String line;
        String date = "", classification = "";
        ArrayList<Object> dailyInformation = new ArrayList<>();
        ArrayList<String> headlines = new ArrayList<>();
        ArrayList<String> prices = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new java.io.FileReader(file))) {

            while ((line = br.readLine()) != null) {

                String[] data = CSVFileReader.splitLine(line);

                if (data[0].equals("Date")) {
                    continue;
                }

                if(date.equals("")){
                    date = data[0];

                    prices = CSVFileReader.getPriceInformation(date);

                    if(prices.size() == 0){
                        continue;
                    }

                    classification = prices.remove(5);
                    dailyInformation.addAll(prices);
                    dailyInformation.add(SA.detectSentiment(data[1]));

                } else if(data[0].equals(date)){
                    if(prices.size() != 0) {
                        dailyInformation.add(SA.detectSentiment(data[1]));
                    }
                } else {
                    date = data[0];
                    prices = CSVFileReader.getPriceInformation(date);
                    if(prices.size() == 0){
                        continue;
                    }

                    dailyInformation.add(classification);

                    if(dailyInformation.size() == 31) {
                        headlines.add(WekaFileWriter.generateObject(dailyInformation));
                    }

                    dailyInformation.clear();

                    classification = prices.remove(5);
                    dailyInformation.addAll(prices);
                    dailyInformation.add(SA.detectSentiment(data[1]));
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return headlines;
    }

    /**
     * Read a text file provided by the user with the news headlines to use.
     *
     * @param file
     * @return headlines
     */
    public static ArrayList<String> readUserNewsFile(String file){
        String line = "";
        ArrayList<String> headlines = new ArrayList<String>();

        try (BufferedReader br = new BufferedReader(new java.io.FileReader(file))) {

            while ((line = br.readLine()) != null) {
                headlines.add(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return headlines;
    }

    /**
     * Method to split the line from RedditNews.csv
     *
     * @param line
     * @return data
     */
    public static String[] splitLine(String line){
        String[] data = line.split(",\"");

        if(data.length != 2){
            data = line.split(",");
        }
        if(data.length != 2){
            data = line.split(",'");
        }
        if(data.length != 2){
            data = line.split(",[a-z]");
        }
        if(data.length != 2){
            data = line.split(",\"[a-z]");
        }

        return data;
    }

    /**
     * Get the price information for a given date.
     *
     * @param date
     * @return ArrayList
     */
    private static ArrayList<String> getPriceInformation(String date){

        String line;
        String[] data;
        ArrayList<String> prices = new ArrayList<String>();

        try (BufferedReader br = new BufferedReader(new java.io.FileReader("./DJIA_table.csv"))) {

            while ((line = br.readLine()) != null) {
                data = line.split(",");

                if(date.equals(data[0])) {

                    prices.add(data[1]);
                    prices.add(data[2]);
                    prices.add(data[3]);
                    prices.add(data[4]);
                    prices.add(data[5]);
                    prices.add(calcClassification(Double.parseDouble(data[1]), Double.parseDouble(data[4])));

                    return prices;
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return prices;
    }

    /**
     * Calculate if the trend for a given day was increase of decrease.
     *
     * @param open
     * @param close
     * @return String
     */
    private static String calcClassification(Double open, Double close){
        Double diff = close - open;

        if(diff > 0){
            return "increase";
        } else {
            return "decrease";
        }
    }

}
