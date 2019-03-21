package chp38.ML.Weka;

import weka.classifiers.bayes.NaiveBayes;
import weka.core.Instances;

public class NBHandler extends AbstractWekaHandler {

    @Override
    public void buildModel(Instances data) throws Exception {
        data.setClassIndex(data.numAttributes() - 1);

        String[] options = new String[1];
        options[0] = "";
        this.model = new NaiveBayes();
        model.setOptions(options);
        model.buildClassifier(data);
    }


}
