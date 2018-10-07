package chp38.Core.Handler;

import chp38.Core.Factory.AbstractFactory;
import chp38.Core.Factory.FactoryProducer;
import chp38.ML.Weka.IWeka;

import java.io.IOException;
import java.util.Scanner;

public class StatisticalAppHandler implements IHandler {

    private IWeka weka;
    /**
     * String commodity
     */
    private String commodity;

    /**
     * String filesFolder
     */
    private String filesFolder;

    /**
     * String the training file
     */
    private String trainingFile;

    /**
     * Constructor
     *
     * @param commodity   String
     * @param filesFolder String
     */
    public StatisticalAppHandler(String commodity, String filesFolder) {
        this.commodity = commodity;
        this.filesFolder = filesFolder;
    }

    @Override
    public String run() throws Exception {
        this.displayMenu();

        String prediction = this.runWeka();

        this.handleOutput(prediction);

        return prediction;
    }

    @Override
    public void displayMenu() throws Exception {
        Scanner reader = new Scanner(System.in);

        System.out.println("Would you like to provide your own Training Data? (0 for no 1 for yes)");
        int training = reader.nextInt();

        if (training == 1) {
            reader.nextLine();
            System.out.println("Please provide the path to the file");
            this.trainingFile = reader.nextLine();
        }

        this.prepareData();
    }

    @Override
    public void prepareData() throws Exception {

    }

    @Override
    public void setWekaHandler() {
        AbstractFactory WekaFactory = FactoryProducer.getWekaHandler();

        this.weka = WekaFactory.getWekaHandler("NB");
    }

    @Override
    public String runWeka() throws Exception {
        return null;
    }

    @Override
    public void handleOutput(String prediction) throws IOException {

    }

}
