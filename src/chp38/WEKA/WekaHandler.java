package chp38.WEKA;

import weka.classifiers.bayes.NaiveBayes;
import weka.core.Instances;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class WekaHandler {

    /**
     * Variable to hold the J48 tree
     */
    private NaiveBayes tree;

    /**
     * Array to hold the two classifications.
     */
    private static String[] arrayClasses = {"decrease", "increase"};

    /**
     * Method to load the attributes from the generated file.
     *
     * @throws Exception
     */
    public void loadAttributes() throws Exception {
        Instances data = this.getInstances("labelled.arff");

        this.buildModel(data);
    }

    /**
     * Method to load the attributes from the user given file.
     *
     * @throws Exception
     */
    public void loadAttributes(String file) throws Exception {

        Instances data = this.getInstances(file);

        this.buildModel(data);
    }

    /**
     * Method to build the model with a given training dataset
     *
     * @param data
     * @throws Exception
     */
    private void buildModel(Instances data) throws Exception {
        data.setClassIndex(data.numAttributes() - 1);

        String[] options = new String[1];
        options[0] = "";
        this.tree = new NaiveBayes();
        tree.setOptions(options);
        tree.buildClassifier(data);
    }

    /**
     * Methos to classify new data, based on the built tree from the
     * training data
     *
     * @throws Exception
     */
    public String classifyData() throws Exception {
        Instances test = this.getInstances("unlabelled.arff");
        Double clsLabel = 0.0;

        test.setClassIndex(test.numAttributes() - 1);

        for (int i = 0; i < test.numInstances(); i++) {
            clsLabel = this.tree.classifyInstance(test.instance(i));
            test.instance(i).setClassValue(clsLabel);
        }

        return this.arrayClasses[(int)Math.round(clsLabel)];
    }

    /**
     * Method to return the instances from the .arff files
     *
     * @param file
     * @return Instances the instances from the arff file
     * @throws IOException
     */
    public Instances getInstances(String file) throws IOException {
        BufferedReader reader = new BufferedReader(
                new FileReader(file));

        Instances data = new Instances(reader);

        reader.close();

        return data;
    }
}
