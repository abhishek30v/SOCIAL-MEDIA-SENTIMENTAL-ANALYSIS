package org.example.dataService;

import java.io.*;

public class DataFormatter {

    private String escapeSpecialCharacters(String column) {
        return column.replaceAll("\"", "\"\"");
    }

    private static String escapeQuotes(String value) {
        if (value == null) {
            return "";
        }
        return value.replace("'", "\\'");
    }

    /*---------------------- reformat csv ----------------------------------*/

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

                String[] columns = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);

                for (int i = 0; i < columns.length; i++) {
                    columns[i] = columns[i].replaceAll("^\"|\"$", "").trim();
                }


                StringBuilder csvLine = new StringBuilder();
                for (int i = 0; i < columns.length; i++) {
                    if (i == columns.length - 1) { // Last column
                        csvLine.append(escapeSpecialCharacters(columns[i]));
                    } else if (i == 0) {
                        csvLine.append(escapeSpecialCharacters(columns[i])).append(",");
                    } else {
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

    /*---------------------- csv to arff ----------------------------------*/
    public void convertCSVToARFF(String csvFilePath, String arffFilePath) {

        try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath));
             FileWriter fw = new FileWriter(arffFilePath)) {

            String line;
            boolean isFirstLine = true;

            fw.write("@relation sentiment_analysis\n\n");
            fw.write("@attribute text string\n");
            fw.write("@attribute sentiment {neutral, positive, negative}\n\n");
            fw.write("@data\n");

            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }

                String[] values = parseCSVLine(line);

                fw.write(String.format("'%s',%s\n",
                        escapeQuotes(values[1]),
                        values[3]));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String[] parseCSVLine(String line) {
        String[] values = new String[4];
        boolean inQuotes = false;
        int valueIndex = 0;
        StringBuilder currentValue = new StringBuilder();

        for (char ch : line.toCharArray()) {
            if (ch == '"') {
                inQuotes = !inQuotes;
            } else if (ch == ',' && !inQuotes) {
                values[valueIndex++] = currentValue.toString();
                currentValue.setLength(0);
            } else {
                currentValue.append(ch);
            }
        }

        values[valueIndex] = currentValue.toString();
        return values;
    }
}
