package org.example.dataService;

import au.com.bytecode.opencsv.CSVReader;

import java.io.*;

public class DataFormatter {

    public void cleanCSVData(String inputFilePath, String outputFilePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFilePath));
             BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath))) {

            String line;
            if ((line = reader.readLine()) != null) {
                writer.write(line + "\n");
            }

            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] columns = line.split(",(?=(?:[^\"]\"[^\"]\")[^\"]$)", -1);

                for (int i = 0; i < columns.length; i++) {
                    columns[i] = columns[i].replaceAll("^\"|\"$", "").trim();
                }


                StringBuilder csvLine = new StringBuilder();
                for (int i = 0; i < columns.length; i++) {
                    if (i == columns.length - 1) { // Last column
                        csvLine.append(escapeSpecialCharacters(columns[i]));
                    }else if(i==0){
                        csvLine.append(escapeSpecialCharacters(columns[i])).append(",");
                    }else {
                        csvLine.append('"').append(escapeSpecialCharacters(columns[i])).append("\",");
                    }
                }

                writer.write(csvLine.toString() + "\n");


            }

            System.out.println("Data formatting to CSV completed successfully.");

        } catch (IOException e) {
            System.err.println("Error formatting data to CSV: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private String escapeSpecialCharacters(String column) {
        return column.replaceAll("\"", "\"\"");
    }
    public static void convertCSVToARFF(String csvFilePath, String arffFilePath) throws IOException {
        try (CSVReader csvReader = new CSVReader(new FileReader(csvFilePath));
             BufferedWriter bw = new BufferedWriter(new FileWriter(arffFilePath))) {

            String[] values;
            boolean isHeader = true;

            bw.write("@relation social_media_sentiment\n\n");
            bw.write("@attribute text string\n");
            bw.write("@attribute sentiment {positive, negative, neutral}\n\n");
            bw.write("@data\n");

            while ((values = csvReader.readNext()) != null) {
                if (isHeader) {
                    isHeader = false;
                    continue;
                }

                String text = values[1].trim();
                String sentiment = null;
                try{ sentiment = values[3].trim();}catch (Exception e){
                    System.out.println(text);
                }

                text = "\"" + text.replace("\"", "\"\"") + "\"";

                bw.write(text + ", " + sentiment + "\n");
            }
        }
    }
}