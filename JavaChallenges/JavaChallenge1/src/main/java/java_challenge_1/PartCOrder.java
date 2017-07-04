package java_challenge_1;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

public class PartCOrder {

    /**
     * Orders values contained within file by outputting a new ordered file
     * @param fileNameIn
     * @param fileNameOut
     */
    public void orderNumbersInFile(final String fileNameIn, final String fileNameOut) {
        BufferedReader br;
        FileReader fr;
        FileWriter fw;
        BufferedWriter bw;
        ArrayList<String> sortedArray;
        ArrayList<String> textFileArray;
        String textToWrite;

        try {
            fr = new FileReader(fileNameIn);
            br = new BufferedReader(fr);
            fw = new FileWriter(fileNameOut);
            bw = new BufferedWriter(fw);
            sortedArray = new ArrayList<String>();
            textFileArray = new ArrayList<String>();
            textToWrite = null;

            String currentLine;

            while ((currentLine = br.readLine()) != null) {
                // only process non-empty lines
                try {
                    if (!currentLine.isEmpty()) {
                        // add to arraylist all of the lines read in from the file
                        textFileArray.add(currentLine);
                    }
                } catch (NumberFormatException numberFormatException) {
                    // not an integer
                    System.out.println("Non-numerical character ignored");
                }
            }

            // create a new arraylist that will be sorted
            sortedArray = new ArrayList<String>(textFileArray);

            Collections.sort(sortedArray);

            // in loop, retrieve sorted values and combine with its original index, then write to file
            for (int i = 0; i < sortedArray.size(); i++) {

                String lineValue = textFileArray.get(i);
                textToWrite = lineValue + "   " + sortedArray.indexOf(lineValue);
                bw.write(textToWrite);
                bw.newLine();

            }

            br.close();
            fr.close();
            bw.close();
            fw.close();

        } catch (IOException ioException) {
            System.out.println(ioException.getMessage());
        }
    }
}
