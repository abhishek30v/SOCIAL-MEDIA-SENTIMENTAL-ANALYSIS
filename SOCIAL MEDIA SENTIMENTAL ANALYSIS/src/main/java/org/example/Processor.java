package org.example;

import org.example.analysis.Analyzer;
import org.example.preProcess.PreProcessor;
import org.jetbrains.annotations.NotNull;

import java.io.*;

public class Processor {
    private PreProcessor preProcessor;
    private Analyzer analyzer;

    private @NotNull String processLine(String line) {
        String[] lineArr = line.split(",");
        if (lineArr.length != 4) {
            return line;
        }

        for (int i = 1; i < lineArr.length - 1; i++) {
            String text = lineArr[i];

            if (!lineArr[i].startsWith("\"") || !lineArr[i].endsWith("\"")) {
                lineArr[i] = "\"" + text + "\"";
            }
        }
        StringBuilder processedLine = new StringBuilder();
        for (int i = 0; i < lineArr.length; i++) {
            processedLine.append(lineArr[i]);
            if (i < lineArr.length - 1) {
                processedLine.append(",");
            }
        }
        return processedLine.toString();
    }

    public PreProcessor getPreProcessor() throws IOException {
        File dataset = new File("src/main/resources/Tweets.csv");
        try (BufferedReader reader = new BufferedReader(new FileReader(dataset));
             BufferedWriter writer = new BufferedWriter(new FileWriter(dataset))) {
            String line;
            StringBuilder newProcessedData = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                String processedLine = processLine(line);
                newProcessedData.append(processedLine).append("\n");
            }
            writer.write(newProcessedData.toString());
        }

        return preProcessor;
    }

    public void setPreProcessor(PreProcessor preProcessor) {
        this.preProcessor = preProcessor;
    }

    public Analyzer getAnalyzer() {
        return analyzer;
    }

    public void setAnalyzer(Analyzer analyzer) {
        this.analyzer = analyzer;
    }
}
