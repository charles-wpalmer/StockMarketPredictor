package chp38.Files;

import chp38.ML.SentimentAnalysis;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class FilesTest {

    @Test
    void readUserNewsFile() {
        ArrayList headlines = FileReader.readUserNewsFile("/Users/charlespalmer/Desktop/headlines.txt");

        assertTrue(headlines.size() > 0);
    }

    @Test
    void splitLine() {

        String line;
        String[] data;
        boolean success = true;

        try (BufferedReader br = new BufferedReader(new java.io.FileReader("./RedditNews.csv"))) {

            while ((line = br.readLine()) != null) {

                data = FileReader.splitLine(line);

                if(data.length != 2){
                    success = false;
                }

            }
            } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        assertTrue(success);

    }
}