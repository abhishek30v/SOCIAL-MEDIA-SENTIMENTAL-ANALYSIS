package org.example.modelService;

import weka.classifiers.Classifier;
import weka.classifiers.evaluation.Evaluation;
import weka.classifiers.functions.SMO;
import weka.core.Instances;
import weka.core.SerializationHelper;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

public class ModelTrainer {
    private String classifierPath;

    public Classifier trainModel(Instances trainData) throws Exception {
        Classifier classifier = new SMO();

        System.out.println("==========================================================================");
        System.out.println("Training using SMO classifier");

        classifier.buildClassifier(trainData);
        return classifier;
    }

    public void evaluateModel(String modelPath,Instances testData) throws Exception {
        Classifier classifier = (Classifier) SerializationHelper.read(modelPath);
        System.out.println("==========================================================================");
        System.out.println("Evaluating the trained model using test data");

        Evaluation eval = new Evaluation(testData);
        eval.evaluateModel(classifier, testData);
        System.out.println(eval.toSummaryString());
        System.out.println(eval.toClassDetailsString());
    }

    public void saveModel(String modelFilePath, Classifier classifier) throws Exception {
        SerializationHelper.write(modelFilePath, classifier);
        System.out.println("===== Model saved to " + modelFilePath + " =====");
    }
}
