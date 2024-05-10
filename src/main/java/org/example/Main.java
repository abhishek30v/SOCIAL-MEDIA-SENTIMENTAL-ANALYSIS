package org.example;
import java.io.File;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        File dataset = new File("src/main/resources/Tweets.csv");
        Processor processor = new Processor();
        try {
            File formattedDataset = processor.getPreProcessor().reFormatData(dataset);
            processor.getAnalyzer().analyze(formattedDataset);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


 