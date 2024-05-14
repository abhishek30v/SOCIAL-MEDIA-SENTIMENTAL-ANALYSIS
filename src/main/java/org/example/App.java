package org.example;

import org.example.modelService.*;
import org.example.dataService.*;

import java.io.File;

public class App {
    public static void main(String[] args) {
        try {
            String rawCSVData = "src/main/resources/tweets.csv";
            String formattedCSVData = "src/main/resources/formattedCSVData.csv";
            String trainingFilePath = "src/main/resources/t.arff";
            String testingFilePath = "src/main/resources/newData.arff";
            String modelFilePath = "src/main/resources/myModel/SentimentModel.dat";

            DataFormatter dataFormatter = new DataFormatter();
//            dataFormatter.formatToCSV(rawCSVData, formattedCSVData);
//            dataFormatter.convertCSVToARFF(formattedCSVData, trainingFilePath);
            DataProcessor dataProcessor = new DataProcessor(trainingFilePath, testingFilePath);
            ModelTrainer modelTrainer = new ModelTrainer();
            ModelPredictor modelPredictor = new ModelPredictor();

            dataProcessor.loadAndPreprocessData();
            modelTrainer.trainModel(dataProcessor.getFilteredTrainData());
            modelTrainer.saveModel(modelFilePath); // Save the model after training

            modelPredictor.classifyAndSaveModel(modelTrainer.getClassifier(), dataProcessor.getFilteredTestData(), dataProcessor.getUnlabeledData());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
