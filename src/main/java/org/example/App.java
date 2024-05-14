package org.example;

import org.example.modelService.*;
import org.example.dataService.*;
import org.jetbrains.annotations.NotNull;
import weka.core.Instances;

import java.io.IOException;

public class App {
    public static void main(String[] args) {
        try {

            String targetDataPath = "src/main/resources/datasets/newData.arff";
            String modelFilePath = "src/main/resources/myModel/SentimentModel.dat";

            DataProcessor dataProcessor = new DataProcessor();
            evaluateModel(dataProcessor,modelFilePath);

            Instances targetData = dataProcessor.loadAndPreprocessData(targetDataPath);
            ModelPredictor modelPredictor = new ModelPredictor(modelFilePath);

            modelPredictor.predictSentiment(targetData);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void evaluateModel(DataProcessor dataProcessor,String modelFilePath) throws Exception {
        String testingFilePath = "src/main/resources/datasets/t.arff";

        Instances testData = dataProcessor.loadAndPreprocessData(testingFilePath);
        ModelTrainer modelTrainer = new ModelTrainer();
        modelTrainer.evaluateModel(modelFilePath, testData);
    }

    private static void trainAndSave( DataProcessor dataProcessor, String modelFilePath) throws Exception {
        String trainingFilePath = "src/main/resources/datasets/trainingData.arff";
        Instances trainData = dataProcessor.loadAndPreprocessData(trainingFilePath);
        ModelTrainer modelTrainer = new ModelTrainer();
        modelTrainer.trainModel(trainData);
        modelTrainer.saveModel(modelFilePath); // Save the model after training
    }

    private static void reformatTrainData() throws IOException {
        String rawCSVData = "src/main/resources/tweets.csv";
        String formattedCSVData = "src/main/resources/formattedCSVData.csv";
        String trainingFilePath = "src/main/resources/datasets/trainingData.arff";

        DataFormatter dataFormatter = new DataFormatter();
        dataFormatter.cleanCSVData(rawCSVData,formattedCSVData);
        dataFormatter.convertCSVToARFF(formattedCSVData,trainingFilePath);
    }

}
