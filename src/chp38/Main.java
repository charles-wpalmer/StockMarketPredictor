package chp38;

import chp38.Factory.AbstractFactory;
import chp38.Factory.FactoryProducer;
import chp38.Handler.IHandler;

import java.io.File;
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


        AbstractFactory AppFactory = FactoryProducer.getAppHandlerFactory();
        IHandler han = AppFactory.getAppHandler("fundamental", comodity, filesFolder);
        han.run();

    }
}
