package org.example.dataService;

import au.com.bytecode.opencsv.CSVReader;

import java.io.*;

public class DataFormatter {

    public void formatToCSV(String inputFilePath, String outputFilePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFilePath));
             BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath))) {

            String line;
            // Read the first line and write it to the output file as is
            if ((line = reader.readLine()) != null) {
                writer.write(line + "\n");
            }

            // Read each subsequent line from the input CSV file
            while ((line = reader.readLine()) != null) {
                // Skip empty lines
                if (line.trim().isEmpty()) {
                    continue;
                }

                // Split the line by commas to get individual columns
                String[] columns = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);

                // Remove quotes and leading/trailing whitespaces from each column
                for (int i = 0; i < columns.length; i++) {
                    columns[i] = columns[i].replaceAll("^\"|\"$", "").trim();
                }


// Reconstruct the CSV line with proper formatting
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

// Write the line to the file
                writer.write(csvLine.toString() + "\n");


                // Remove the trailing comma and write the line to the file
//                writer.write(csvLine.substring(0, csvLine.length() - 1) + "\n");
            }

            System.out.println("Data formatting to CSV completed successfully.");

        } catch (IOException e) {
            System.err.println("Error formatting data to CSV: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Helper method to escape special characters
    private String escapeSpecialCharacters(String column) {
        return column.replaceAll("\"", "\"\"");
    }
    public void convertCSVToARFF(String csvFilePath, String arffFilePath) throws IOException {
        try (CSVReader csvReader = new CSVReader(new FileReader(csvFilePath));
             BufferedWriter bw = new BufferedWriter(new FileWriter(arffFilePath))) {

            String[] values;
            boolean isHeader = true;

            // Writing ARFF header
            bw.write("@relation social_media_sentiment\n\n");
            bw.write("@attribute text string\n");
            bw.write("@attribute sentiment {positive, negative, neutral}\n\n");
            bw.write("@data\n");

            // Reading CSV file
            while ((values = csvReader.readNext()) != null) {
                if (isHeader) {
                    // Skip header line
                    isHeader = false;
                    continue;
                }

                // Extracting the relevant data
                String text = values[1].trim();
                String sentiment = values[3].trim();

                // Formatting the text to escape quotes
                text = "\"" + text.replace("\"", "\"\"") + "\"";

                // Writing to ARFF
                bw.write(text + ", " + sentiment + "\n");
            }
        }
    }
}
