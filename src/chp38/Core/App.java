package chp38.Core;

import chp38.Core.Handler.IHandler;
import chp38.Core.Factory.AbstractFactory;
import chp38.Core.Factory.FactoryProducer;

import java.io.File;
import java.util.Scanner;

public class App {

    /**
     * AbstractFactory AppFactory;
     */
    private AbstractFactory AppFactory;

    /**
     * App Constructor
     */
    public App(){
        this.AppFactory = FactoryProducer.getAppHandlerFactory();
    }

    /**
     * Get and run the handler
     * @param comodity String
     * @param filesFolder String
     * @throws Exception e
     */
    private void run(String comodity, String filesFolder)throws Exception {
        IHandler han = this.AppFactory.getAppHandler("fundamental", comodity, filesFolder);
        han.run();
    }

    /**
     * Display the Menu to the user
     * @throws Exception e
     */
    private void menu() throws Exception {
        Scanner reader = new Scanner(System.in);

        String filesFolder = "./files";
        File f = new File(filesFolder);

        if (!f.exists() && !f.isDirectory()) {
            System.out.println("Files folder not found, please enter its location... ");

            filesFolder = reader.nextLine();
        }

        System.out.println("Please enter the short name of the Commodity/market you wish to predict...");
        String comodity = reader.nextLine();

        this.run(comodity, filesFolder);
    }

    /**
     * Initialize the application
     * @throws Exception e
     */
    public  void initialize() throws Exception {
        this.menu();
    }
}
