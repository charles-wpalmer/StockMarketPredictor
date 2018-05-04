package chp38;

import chp38.Core.AppHandler;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {

        Scanner reader = new Scanner(System.in);

        String filesFolder = "./files";
        File f = new File(filesFolder);

        if (!f.exists() && !f.isDirectory()) {
            System.out.println("Files folder not found, please enter its location... ");

            filesFolder = reader.nextLine();
        }

        System.out.println("Please enter the short name of the Commodity/market you wish to predict...");
        String comodity = reader.nextLine();

        AppHandler han = new AppHandler(comodity, filesFolder);

        han.run();

    }
}
