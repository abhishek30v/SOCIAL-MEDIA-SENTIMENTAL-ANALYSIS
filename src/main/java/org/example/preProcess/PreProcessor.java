package org.example.preProcess;

import java.io.*;

public class PreProcessor {

    public void reFormatData(File givenFile, File newFile) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(givenFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(newFile))) {
            writer.write("textID,text,selected_text,sentiment");
            writer.newLine();
            reader.readLine(); // Skip the header line
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
                // Above regex splits the line by comma only if it's not within double quotes

                // Removing double quotes from each part if present
                for (int i = 0; i < parts.length; i++) {
                    parts[i] = parts[i].replaceAll("^\"|\"$", ""); // Remove leading and trailing quotes
                }

                if (parts.length >= 4) {
                    writer.write(String.format("\"%s\",\"%s\",\"%s\",%s",
                            parts[0], parts[1], parts[2], parts[3]));
                    writer.newLine();
                }
            }
        }
    }
}
