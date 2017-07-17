package uk.co.aquaq.javachallenge1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ReadFileToArrayList {

    /**
     * Convert input stream to arraylist containing strings from the text file
     * @param filePath
     * @return arraylist of strings
     */
    public List<String> convertFileToStringArray(InputStream filePath) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(filePath));
        String currentLine = null;
        List<String> stringListFromInput = new ArrayList<String>();

        try {
            while ((currentLine = bufferedReader.readLine()) != null) {
                // adds all strings, including non-numerical and empty lines
                stringListFromInput.add(currentLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return stringListFromInput;
    }

}
