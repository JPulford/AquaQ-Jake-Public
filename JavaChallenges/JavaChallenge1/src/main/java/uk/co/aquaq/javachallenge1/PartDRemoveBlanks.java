package uk.co.aquaq.javachallenge1;

import java.io.*;
import java.util.List;

public class PartDRemoveBlanks {

    private WriteNewFile fileWriter;

    public PartDRemoveBlanks(WriteNewFile fileWriter) {
        this.fileWriter = fileWriter;
    }

    /**
     * Removes blanks from file, where new file is output minus the blank lines
     * @param stringsFromFile
     */
    public void removeBlanks(final List<String> stringsFromFile) {

        for (int lineNumber = 0; lineNumber < stringsFromFile.size(); lineNumber++) {
            String line = stringsFromFile.get(lineNumber);

            if (!line.isEmpty()) {
                fileWriter.writeToFile(line);
            } else {
                System.out.println("Original line " + (lineNumber + 1) + " removed from file");
            }
        }
        fileWriter.closeFileWriter();
    }
}



