package org.example.modelService;

import weka.classifiers.Classifier;
import weka.classifiers.evaluation.Evaluation;
import weka.classifiers.functions.SMO;
import weka.core.Instances;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.Random;

public class ModelTrainer {
    private Classifier classifier;

    public void trainModel(Instances trainData) throws Exception {
        classifier = new SMO();

        System.out.println("==========================================================================");
        System.out.println("Training using SMO classifier");

        // Train the classifier on the full training data
        classifier.buildClassifier(trainData);
    }

    public void evaluateModel(Instances testData) throws Exception {
        System.out.println("==========================================================================");
        System.out.println("Evaluating the trained model using test data");

        // Evaluate the classifier using test data
        Evaluation eval = new Evaluation(testData);
        eval.evaluateModel(classifier, testData);
        System.out.println(eval.toSummaryString());
        System.out.println(eval.toClassDetailsString());
    }

    public Classifier getClassifier() {
        return classifier;
    }

    public void saveModel(String modelFilePath) throws Exception {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(modelFilePath))) {
            out.writeObject(classifier);
            System.out.println("===== Model saved to " + modelFilePath + " =====");
        }
    }
}
