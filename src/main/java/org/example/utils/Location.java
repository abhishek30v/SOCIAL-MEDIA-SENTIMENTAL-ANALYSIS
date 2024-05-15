package org.example.utils;

public class Location {
    private static final String RESOURCES = "src/main/resources";
    private static final String DATASETS = RESOURCES + "/datasets";
    public static String TRAINING_DATA_PATH = DATASETS + "/trainingData1.arff";
    public static String TESTING_DATA_PATH = DATASETS + "/testingData.arff";
    public static String TARGET_DATA_PATH = DATASETS + "/newData.arff";
    private static final String MODELS = RESOURCES + "/myModel";
    public static String CLASSIFIER_PATH = MODELS + "/SentimentModel1.dat";
    public static String FILTER_PATH = MODELS + "/filter.model";
}
