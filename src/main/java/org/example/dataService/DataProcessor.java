package org.example.dataService;

import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.StringToWordVector;

public class DataProcessor {
    private String trainingFilePath;
    private String testingFilePath;
    private Instances filteredTrain;
    private Instances filteredTest;
    private Instances unlabeled;

    public DataProcessor(String trainingFilePath, String testingFilePath) {
        this.trainingFilePath = trainingFilePath;
        this.testingFilePath = testingFilePath;
    }

    public void loadAndPreprocessData() throws Exception {
        // Load training data
        DataSource trainSource = new DataSource(trainingFilePath);
        Instances train = trainSource.getDataSet();
        train.setClassIndex(train.numAttributes() - 1);

        // Load testing data
        DataSource testSource = new DataSource(testingFilePath);
        unlabeled = testSource.getDataSet();
        unlabeled.setClassIndex(unlabeled.numAttributes() - 1);

        // Apply StringToWordVector filter
        StringToWordVector filter = new StringToWordVector();
        filter.setAttributeIndices("first-last");
        filter.setInputFormat(train);

        filteredTrain = Filter.useFilter(train, filter);
        filteredTest = Filter.useFilter(unlabeled, filter);
    }

    public Instances getFilteredTrainData() {
        return filteredTrain;
    }

    public Instances getFilteredTestData() {
        return filteredTest;
    }

    public Instances getUnlabeledData() {
        return unlabeled;
    }
}
