package org.example.modelService;

import weka.classifiers.Classifier;
import weka.core.Instances;

public class ModelPredictor {
    public void classifyAndSaveModel(Classifier classifier, Instances testData, Instances unlabeledData) throws Exception {
        // Label the instances in the testing data
        Instances labeled = new Instances(testData);
        for (int i = 0; i < testData.numInstances(); i++) {
            double clsLabel = classifier.classifyInstance(testData.instance(i));
            labeled.instance(i).setClassValue(clsLabel);
            System.out.println(clsLabel + " -> " + unlabeledData.classAttribute().value((int) clsLabel));
        }
    }
}
