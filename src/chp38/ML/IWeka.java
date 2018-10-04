package chp38.ML;

public interface IWeka {

    /**
     * Method to load the attributes from the user given file.
     *
     * @param file the file to load attributes from
     * @throws Exception e
     */
    void loadAttributes(String file) throws Exception;

    /**
     * Methos to classify new data, based on the built tree from the
     * training data
     *
     * @param file the file containing object to classify
     * @throws Exception e
     * @return String classification (increase|decrease)
     */
     String classifyData(String file) throws Exception;
}
