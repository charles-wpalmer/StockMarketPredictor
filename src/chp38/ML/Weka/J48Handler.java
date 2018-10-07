package chp38.ML.Weka;

import weka.classifiers.trees.J48;
import weka.core.Instances;

public class J48Handler extends AbstractWekaHandler {

    @Override
    public void buildModel(Instances data) throws Exception {
        data.setClassIndex(data.numAttributes() - 1);

        String[] options = new String[1];
        options[0] = "";
        this.model = new J48();
        model.setOptions(options);
        model.buildClassifier(data);
    }

}
