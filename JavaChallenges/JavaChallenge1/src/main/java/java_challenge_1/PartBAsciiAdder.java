package java_challenge_1;

import java.io.*;


/**
 * Created by Jake.Pulford on 13/06/2017.
 */
public class PartBAsciiAdder {

    /**
     * Create new text file that contains ASCII values for characters within file
     * @param fileNameIn
     * @param fileNameOut
     */
    public void getAsciiValues(String fileNameIn, String fileNameOut) {

        BufferedReader br;
        FileReader fr;
        String currentLine;
        char lineValue;
        int ascii;
        String toWriteToFile;
        FileWriter fw;
        BufferedWriter bw;

        try {
            fr = new FileReader(fileNameIn);
            br = new BufferedReader(fr);
            fw = new FileWriter(fileNameOut);
            bw = new BufferedWriter(fw);

            while ((currentLine = br.readLine()) != null) {
                // empty lines are not processed
                if (!currentLine.isEmpty()) {
                    lineValue = currentLine.charAt(0);
                    ascii = (int) lineValue;
                    toWriteToFile = currentLine + "    " + ascii;
                    System.out.println(toWriteToFile);
                    bw.write(toWriteToFile);
                    bw.newLine();
                }
            }

            br.close();
            fr.close();
            bw.close();
            fw.close();

        } catch (IOException ioException) {
            System.out.println("Error");

        }






    }
}
