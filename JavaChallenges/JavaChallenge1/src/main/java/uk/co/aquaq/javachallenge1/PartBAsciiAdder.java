package uk.co.aquaq.javachallenge1;

import java.util.List;

public class PartBAsciiAdder {

    private WriteNewFile fileWriter;


    public PartBAsciiAdder(WriteNewFile fileWriter) {
        this.fileWriter = fileWriter;
    }

    /**
     * Create new text file that contains ASCII values for characters within file
     * Currently only reads the first character of a line to determine its ASCII value
     * @param stringsFromFile - strings read from each line of file
     */
    public void getAsciiValues(final List<String> stringsFromFile) {

        char valueHeldInCurrentLine;
        int ascii;
        String toWriteToFile;

        System.out.println("ASCII output");
        for (int lineNumber = 0; lineNumber < stringsFromFile.size(); lineNumber++) {
            String line = stringsFromFile.get(lineNumber);

            if (!line.isEmpty()) {
                valueHeldInCurrentLine = line.charAt(0);
                // convert value read in to char, then cast to int to return ascii value
                ascii = (int) valueHeldInCurrentLine;
                toWriteToFile = line + "    " + ascii;
                System.out.println(toWriteToFile);

                fileWriter.writeToFile(toWriteToFile);
            }
        }
        fileWriter.closeFileWriter();
    }

    public void setFileWriter(WriteNewFile fileWriter) {
        this.fileWriter = fileWriter;
    }
}
