package uk.co.aquaq.javachallenge1;

import java.util.*;

public class PartCOrder {

    private WriteNewFile fileWriter;

    public PartCOrder(WriteNewFile fileWriter) {
        this.fileWriter = fileWriter;
    }

    /**
     * Orders values contained within file by outputting a new ordered file
     * @param stringsFromFile - strings read from each line of file
     */
    public void orderNumbersInFile(final List<String> stringsFromFile) {

        String textToWrite;
        ArrayList<String> sortedArray;
        ArrayList<String> textFileArray;
        LinkedHashMap<Integer, String> mapOfValues = new LinkedHashMap<>();

        sortedArray = new ArrayList<String>();
        textFileArray = new ArrayList<String>();
        textToWrite = null;

        for (int lineNumber = 0; lineNumber < stringsFromFile.size(); lineNumber++) {
            String line = stringsFromFile.get(lineNumber);

            textFileArray.add(line);

        }

        // to remove duplicates
        Set sortedSet = new HashSet<String>(textFileArray);
        // to remove duplicates
        sortedArray = new ArrayList<String>(sortedSet);
        // sorted in order
        Collections.sort(sortedArray);

        // in loop, retrieve sorted values and combine with its original index, then write to file
        for (int i = 0; i < textFileArray.size(); i++) {
            int intLineValue;
            String lineValue = textFileArray.get(i);

            if (!lineValue.isEmpty()) {
                textToWrite = lineValue + " from line " + (i+1) + " order is " + (sortedArray.indexOf(lineValue));

            } else {
                // for empty lines, to show original position
                textToWrite = "Empty line";
            }

            fileWriter.writeToFile(textToWrite);
        }
        fileWriter.closeFileWriter();
    }
}
