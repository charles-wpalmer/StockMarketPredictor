package chp38.Core.Handler;

import java.io.IOException;

public interface IHandler {

    /**
     * Method to start running the program. Prepares training and test data
     * from the given files, then builds the model using weka, and classifys
     * the data.
     *
     * @return String
     * @throws Exception e
     */
    String run() throws Exception;

    /**
     * Display the menu, to gain user inputs.
     *
     * @throws Exception e
     */
    void displayMenu() throws Exception;

    /**
     * Method to prepare the training data.
     *
     * @throws Exception e
     */
    void prepareData() throws Exception;

    /**
     * Set the Weka Handler to use
     */
    void setWekaHandler();

    /**
     * Method to load the training data into Weka and classify the new data.
     *
     * @return String Prediction
     * @throws Exception e
     */
    String runWeka() throws Exception;

    /**
     *
     * @param prediction String
     * @throws IOException e
     */
    void handleOutput(String prediction) throws IOException;

}
