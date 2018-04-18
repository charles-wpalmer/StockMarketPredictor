package chp38;

import chp38.APIHandler.ServerAPI;
import chp38.Core.*;
import chp38.Files.CSVFileReader;
import chp38.SentimentAnalysis.SentimentAnalysis;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner reader = new Scanner(System.in);

        System.out.println("Please enter the short name of the Commodity/market you wish to predict...");
        String comodity = reader.nextLine();

        AppHandler han = new AppHandler(comodity);

        han.run();
    }
}
