package org.example;

import org.example.modelService.*;
import org.example.dataService.*;

import java.io.IOException;

public class App {
    public static void main(String[] args) {
        try {

            String trainingFilePath = "src/main/resources/trainingData.arff";
            String testingFilePath = "src/main/resources/newData.arff";
            String modelFilePath = "src/main/resources/myModel/SentimentModel.dat";

            DataProcessor dataProcessor = new DataProcessor(trainingFilePath, testingFilePath);

            ModelPredictor modelPredictor = new ModelPredictor();

            dataProcessor.loadAndPreprocessData();

            modelPredictor.predictSentiment(modelPredictor.getClassifier(modelFilePath), dataProcessor.getFilteredTestData(), dataProcessor.getUnlabeledData());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void trainAndSave( DataProcessor dataProcessor, String modelFilePath) throws Exception {
        ModelTrainer modelTrainer = new ModelTrainer();
        modelTrainer.trainModel(dataProcessor.getFilteredTrainData());
        modelTrainer.saveModel(modelFilePath); // Save the model after training
    }

    private static void reformatTrainData() throws IOException {
        String rawCSVData = "src/main/resources/tweets.csv";
        String formattedCSVData = "src/main/resources/formattedCSVData.csv";
        String trainingFilePath = "src/main/resources/trainingData.arff";

        DataFormatter dataFormatter = new DataFormatter();
        dataFormatter.formatToCSV(rawCSVData,formattedCSVData);
        dataFormatter.convertCSVToARFF(formattedCSVData,trainingFilePath);
    }

}
