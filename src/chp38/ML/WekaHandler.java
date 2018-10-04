package chp38.ML;

import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.trees.J48;
import weka.core.Instances;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

public class WekaHandler implements IWeka{

    /**
     * Variable to hold the J48 tree
     */
    private J48 tree;

    /**
     * Variable to hold the Naive Bayes Model
     */
    private NaiveBayes model;

    /**
     * Array to hold the two classifications.
     */
    private static String[] arrayClasses = {"decrease", "increase"};

    @Override
    public void loadAttributes(String file) throws Exception {

        Instances data = this.getInstances(file);

        this.buildModel(data);
    }

    @Override
    public String classifyData(String file) throws Exception {
        Instances test = this.getInstances(file);
        Double clsLabel = 0.0;

        test.setClassIndex(test.numAttributes() - 1);

        for (int i = 0; i < test.numInstances(); i++) {
            clsLabel = this.tree.classifyInstance(test.instance(i));
            test.instance(i).setClassValue(clsLabel);
        }

        return test.classAttribute().value(clsLabel.intValue());
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
        this.tree = new J48();
        tree.setOptions(options);
        tree.buildClassifier(data);
    }

    /**
     * Method to return the instances from the .arff files
     *
     * @param file file to get the instanes from
     * @return Instances the instances from the arff file
     * @throws IOException
     */
    private Instances getInstances(String file) throws IOException {
        BufferedReader reader = new BufferedReader(
                new FileReader(file));

        Instances data = new Instances(reader);

        reader.close();

        return data;
    }

    /**
     * Function to test the accuracy (CV) for J48
     *
     * @throws Exception
     */
    public void testJ48Accuracy() throws Exception {
        Instances data = this.getInstances("files/labelled.arff");
        data.setClassIndex(data.numAttributes() - 1);
        String[] options = new String[1];
        options[0] = "";
        this.tree = new J48();
        this.tree.setOptions(options);

        Evaluation eval = new Evaluation(data);
        eval.crossValidateModel(this.tree, data, 5, new Random(1));
        System.out.println(eval.toSummaryString("\nResults\n======\n", false));
    }

    /**
     * Function to test the accuracy (CV) for Naive Bayes
     *
     * @throws Exception
     */
    public void testNBAccuracy() throws Exception {
        Instances data = this.getInstances("files/labelled.arff");
        data.setClassIndex(data.numAttributes() - 1);
        String[] options = new String[1];
        options[0] = "";
        this.model = new NaiveBayes();
        this.model.setOptions(options);

        Evaluation eval = new Evaluation(data);
        eval.crossValidateModel(this.model, data, 5, new Random(1));
        System.out.println(eval.toSummaryString("\nResults\n======\n", false));
    }
}
