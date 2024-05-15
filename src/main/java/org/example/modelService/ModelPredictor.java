package org.example.modelService;

import org.example.utils.Location;
import weka.classifiers.Classifier;
import weka.core.Instances;
import weka.core.SerializationHelper;

public class ModelPredictor {
    private final String classifierPath;

    public ModelPredictor(String classifierPath) {
        this.classifierPath = classifierPath;
    }

    public void predictSentiment(Instances unlabeledData) throws Exception {
        Classifier classifier = (Classifier) SerializationHelper.read(classifierPath);
        // Label the instances in the testing data
        Instances labeled = new Instances(unlabeledData);
        for (int i = 0; i < unlabeledData.numInstances(); i++) {
            double clsLabel = classifier.classifyInstance(unlabeledData.instance(i));
            labeled.instance(i).setClassValue(clsLabel);
            System.out.println(clsLabel + " -> " + unlabeledData.classAttribute().value((int) clsLabel));
        }
    }
}
