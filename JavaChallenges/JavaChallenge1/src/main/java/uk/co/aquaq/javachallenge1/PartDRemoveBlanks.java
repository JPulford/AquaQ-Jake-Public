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
     * @param stringsFromFile - strings read from each line of file
     */
    public void removeBlanks(final List<String> stringsFromFile) {

        for (int lineNumber = 0; lineNumber < stringsFromFile.size(); lineNumber++) {
            String valueFromLineOfFile = stringsFromFile.get(lineNumber);

            if (!valueFromLineOfFile.isEmpty()) {
                fileWriter.writeToFile(valueFromLineOfFile);
            } else {
                System.out.println("Original line " + (lineNumber + 1) + " removed from file");
            }
        }
        fileWriter.closeFileWriter();
    }
}



