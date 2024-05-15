package org.example;

import org.example.modelService.*;
import org.example.dataService.*;
import weka.classifiers.Classifier;
import weka.core.Instances;

import static org.example.utils.Location.*;

import java.io.IOException;

public class App {
    public static void main(String[] args) {
//      step1: clean and reformat the data
//        reformatTrainData();

//      step2: data processing and training
        trainAndSave();

//      step3: evaluation
        DataProcessor dataProcessor = new DataProcessor(FILTER_PATH);
        evaluateModel(dataProcessor);

//      step4: Prediction
        ModelPredictor predictor = new ModelPredictor(CLASSIFIER_PATH);
        predictNewData(dataProcessor, predictor);
    }

    private static void predictNewData(DataProcessor dataProcessor, ModelPredictor predictor) {
        Instances targetData = null;
        try {
            targetData = dataProcessor.loadAndPreprocessData(TARGET_DATA_PATH);
            Instances finalTargetData = dataProcessor.applyFilter(targetData);
            predictor.predictSentiment(finalTargetData);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private static void evaluateModel(DataProcessor dataProcessor) {
        ModelTrainer modelTrainer = new ModelTrainer();
        Instances testData = null;
        try {
            testData = dataProcessor.loadAndPreprocessData(TESTING_DATA_PATH);
            Instances finalTestData = dataProcessor.applyFilter(testData);
            modelTrainer.evaluateModel(CLASSIFIER_PATH, finalTestData);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private static void trainAndSave() {

        DataProcessor dataProcessor = new DataProcessor(FILTER_PATH);
        ModelTrainer modelTrainer = new ModelTrainer();
        try {
            //  2.i      processData, apply and save filter
            Instances trainData = dataProcessor.loadAndPreprocessData(TRAINING_DATA_PATH);
            Instances finalTrainData = dataProcessor.createAndApplyFilter(trainData);

            // 2.ii     trainModel, saveModel
            Classifier classifier = modelTrainer.trainModel(finalTrainData);
            modelTrainer.saveModel(CLASSIFIER_PATH, classifier);

        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    private static void reformatTrainData() {
        String rawCSVData = "src/main/resources/tweets.csv";
        String formattedCSVData = "src/main/resources/formattedCSVData.csv";
        DataFormatter dataFormatter = new DataFormatter();
        dataFormatter.cleanCSVData(rawCSVData,formattedCSVData);
        dataFormatter.convertCSVToARFF(formattedCSVData, TRAINING_DATA_PATH);
    }

}
