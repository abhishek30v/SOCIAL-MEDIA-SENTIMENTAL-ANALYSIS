package org.example.modelService;

import weka.classifiers.Classifier;
import weka.core.Instances;
import weka.core.SerializationHelper;

public class ModelPredictor {
    private Classifier classifier;

    public ModelPredictor(String classifierPath) throws Exception {
        this.classifier = (Classifier) SerializationHelper.read(classifierPath);
    }

    public void predictSentiment(Instances unlabeledData) throws Exception {
        // Label the instances in the testing data
        Instances labeled = new Instances(unlabeledData);
        for (int i = 0; i < unlabeledData.numInstances(); i++) {
            double clsLabel = classifier.classifyInstance(unlabeledData.instance(i));
            labeled.instance(i).setClassValue(clsLabel);

            System.out.println(clsLabel + " -> " + unlabeledData.classAttribute().value((int) clsLabel));
        }
    }
}
