package java_challenge_1;

import java.io.*;

public class PartDRemoveBlanks {

    /**
     * Removes blanks from file, where new file is output minus the blank lines
     * @param fileNameIn
     * @param fileNameOut
     */
    public void removeBlanks(final String fileNameIn, final String fileNameOut) {

        BufferedReader br;
        FileReader fr;
        FileWriter fw;
        BufferedWriter bw;

        try {
            fr = new FileReader(fileNameIn);
            br = new BufferedReader(fr);

            fw = new FileWriter(fileNameOut);
            bw = new BufferedWriter(fw);

            String currentLine;
            int lineCount = 0;

            while ((currentLine = br.readLine()) != null) {
                lineCount++;
                // if string is not empty, write to new file
                if (!currentLine.isEmpty()) {
                    bw.write(currentLine);
                    bw.newLine();
                } else {
                    // a blank line has been read in, which has not been written to file
                    System.out.println("Original line " + lineCount + " removed from file");
                }
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



