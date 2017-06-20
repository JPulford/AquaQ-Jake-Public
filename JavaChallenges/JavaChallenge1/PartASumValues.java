package org.aquaq.challenge1;

import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by Jake.Pulford on 13/06/2017.
 */
public class PartASumValues {

    /**
     * Sums values of integers contained in text file passed in
     * @param fileName
     * @return sum total of numerical values in file
     */
    public int sumTotal(String fileName)  {

        BufferedReader br;
        FileReader fr;

        int lineValue = 0;
        int totalValue = 0;

        try {
            fr = new FileReader(fileName);
            br = new BufferedReader(fr);

            // holds value of current line read in
            String currentLine;
            int lineCount = 0;

            while ((currentLine = br.readLine()) != null) {
                lineCount++;
                try {
                    lineValue = Integer.parseInt(currentLine);
                    System.out.println("Line value is " + lineValue);
                } catch (NumberFormatException numberFormatException) {
                    System.out.println("Non-numerical character on line " + lineCount);
                }
                // sum cumulative value
                totalValue += lineValue;
                // reset line value back to 0
                lineValue = 0;
            }

        } catch (IOException ioException) {
            System.out.println("Error - I/O exception");
        }

        return totalValue;
    }
}

