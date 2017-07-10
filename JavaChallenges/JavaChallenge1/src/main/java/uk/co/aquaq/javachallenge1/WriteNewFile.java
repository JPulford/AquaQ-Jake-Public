package uk.co.aquaq.javachallenge1;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Class for writing files using filewriter and bufferedwriter
 * Constructor is used to set initial file output path
 * For subsequent use of the same object, call the resetFileWriter() method and provide a new output path
 */
public class WriteNewFile {

    private FileWriter fileWriter;
    private BufferedWriter bufferedWriter;
    private String fileOutputName;

    public WriteNewFile(final String fileOutputName) {

        this.fileOutputName = fileOutputName;

        try {
            fileWriter = new FileWriter(fileOutputName);
            bufferedWriter = new BufferedWriter(fileWriter);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeToFile(final String lineValue) {

        try {
            bufferedWriter.write(lineValue);
            bufferedWriter.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeFileWriter() {

        try {
            bufferedWriter.close();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void resetFileWriter(final String fileOutputName) {

        this.fileOutputName = fileOutputName;

        try {
            fileWriter = new FileWriter(fileOutputName);
            bufferedWriter = new BufferedWriter(fileWriter);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
