package org.example;
import java.io.File;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
//        Scanner scanner = new Scanner(System.in);
//        System.out.println("Raw data location with filename (Enter path): ");
//        String rawDataPath = scanner.nextLine();
//        System.out.println("FileName with location where clean data would be generated (Enter path) : ");
//        String cleanDataPath = scanner.nextLine();
//        File dataset = new File(rawDataPath);
//        File formattedDataSet = new File(cleanDataPath);
        File dataset = new File("src/main/resources/Tweets.csv");
        File formattedDataset = new File("src/main/resources/formattedDataSet.csv");
        Processor processor = new Processor();
        try {
            processor.getPreProcessor().reFormatData(dataset, formattedDataset);
            processor.getAnalyzer().analyze(formattedDataset);
        } catch (Exception e) {
           e.printStackTrace();
        }
    }
}


 