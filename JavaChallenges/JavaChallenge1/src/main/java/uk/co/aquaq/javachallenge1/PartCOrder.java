package uk.co.aquaq.javachallenge1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PartCOrder {

    private WriteNewFile fileWriter;

    public PartCOrder(WriteNewFile fileWriter) {
        this.fileWriter = fileWriter;
    }

    /**
     * Orders values contained within file by outputting a new ordered file
     * @param stringsFromFile
     */
    public void orderNumbersInFile(final List<String> stringsFromFile) {

        String textToWrite;
        ArrayList<String> sortedArray;
        ArrayList<String> textFileArray;

        sortedArray = new ArrayList<String>();
        textFileArray = new ArrayList<String>();
        textToWrite = null;

        for (int lineNumber = 0; lineNumber < stringsFromFile.size(); lineNumber++) {
            String line = stringsFromFile.get(lineNumber);

            if (!line.isEmpty()) {
                textFileArray.add(line);
            }
        }
        // create a new arraylist that will be sorted
        sortedArray = new ArrayList<String>(textFileArray);

        Collections.sort(sortedArray);

        // in loop, retrieve sorted values and combine with its original index, then write to file
        for (int i = 0; i < sortedArray.size(); i++) {
            String lineValue = textFileArray.get(i);
            textToWrite = lineValue + "   " + sortedArray.indexOf(lineValue);
            fileWriter.writeToFile(textToWrite);
        }
        fileWriter.closeFileWriter();
    }
}
