package org.example.analysis;

import weka.classifiers.Evaluation;
import weka.classifiers.trees.J48;
import weka.core.DenseInstance;
import weka.core.Instances;
import weka.core.converters.CSVLoader;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Normalize;

import java.io.File;
import java.util.Random;

public class Analyzer {

    public void analyze(File file) throws Exception {
        // Load CSV file
        CSVLoader loader = new CSVLoader();
        loader.setSource(file);
        Instances data = loader.getDataSet();
        // Normalize data
        Normalize normalize = new Normalize();
        normalize.setInputFormat(data);
        Instances normalizedData = Filter.useFilter(data, normalize);
        normalizedData.setClassIndex(data.numAttributes() - 1);
        // Train decision tree
        J48 tree = new J48();
        tree.buildClassifier(normalizedData);
        // Evaluate model using cross-validation
        Evaluation eval = new Evaluation(normalizedData);
        eval.crossValidateModel(tree, normalizedData, 4, new Random(1));
        System.out.println(eval.toSummaryString());
        // Create new instance
        double[] values = {1.0, 2.0, 3.0, 4.0};
        DenseInstance instance = new DenseInstance(1.0, values);
        instance.setDataset(normalizedData);
        // Make prediction
        double prediction = tree.classifyInstance(instance);
        System.out.println(prediction);
    }
}
