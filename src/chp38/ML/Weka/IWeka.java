package chp38.ML.Weka;

import weka.core.Instances;
import java.io.IOException;

public interface IWeka {

    /**
     * Methos to classify new data, based on the built tree from the
     * training data
     *
     * @param file the file containing object to classify
     * @throws Exception e
     */
    void loadAttributes(String file) throws Exception;

    /**
     * Methods to classify new data, based on the built tree from the
     * training data
     *
     * @param file the file containing object to classify
     * @throws Exception e
     * @return String classification (increase|decrease)
     */
     String classifyData(String file) throws Exception;

    /**
     * Method to build the model with a given training dataset
     *
     * @param data Instances
     * @throws Exception e
     */
    void buildModel(Instances data) throws Exception;

    /**
     * Method to return the instances from the .arff files
     *
     * @param file file to get the instanes from
     * @return Instances the instances from the arff file
     * @throws IOException e
     */
    Instances getInstances(String file) throws IOException;

}
