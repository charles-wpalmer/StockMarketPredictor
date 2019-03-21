package chp38.ML.Weka;

import weka.classifiers.AbstractClassifier;
import weka.core.Instances;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public abstract class AbstractWekaHandler implements IWeka{

    /**
     * Variable to hold ML model J48, NB ...
     */
    AbstractClassifier model;

    @Override
    public Instances getInstances(String file) throws IOException {
        BufferedReader reader = new BufferedReader(
                new FileReader(file));

        Instances data = new Instances(reader);

        reader.close();

        return data;
    }

    @Override
    public String classifyData(String file) throws Exception {
        Instances test = this.getInstances(file);
        Double clsLabel = 0.0;

        test.setClassIndex(test.numAttributes() - 1);

        for (int i = 0; i < test.numInstances(); i++) {
            clsLabel = this.model.classifyInstance(test.instance(i));
            test.instance(i).setClassValue(clsLabel);
        }

        return test.classAttribute().value(clsLabel.intValue());
    }

    @Override
    public void loadAttributes(String file) throws Exception {

        Instances data = this.getInstances(file);

        this.buildModel(data);
    }
}
