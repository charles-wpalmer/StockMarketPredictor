package chp38.Files;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class FileReader {

    /**
     * Read a text file provided by the user with the news headlines to use.
     *
     * @param file
     * @return headlines
     */
    public static ArrayList<String> readNewsFile(String file){

        String line;
        ArrayList<String> headlines = new ArrayList<String>();
        
        try (BufferedReader br = new BufferedReader(new java.io.FileReader("./RedditNews.csv"))) {

            while ((line = br.readLine()) != null) {
                String[] data = FileReader.splitLine(line);

            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return headlines;
    }

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

}
