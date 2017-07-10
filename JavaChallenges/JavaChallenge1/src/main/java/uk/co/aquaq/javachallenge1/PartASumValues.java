package uk.co.aquaq.javachallenge1;

import java.util.List;

public class PartASumValues {

    private WriteNewFile fileWriter;

    /**
     * Sums values of integers contained in text file passed in
     * @param stringsFromFile
     * @return sum total of numerical values in file
     */
    public int sumTotal(final List<String> stringsFromFile)  {

        int lineValue = 0;
        int totalValue = 0;

        for (int lineNumber = 0; lineNumber < stringsFromFile.size(); lineNumber++) {
            String line = stringsFromFile.get(lineNumber);
            if (line.matches("[0-9]+") && line.length() >= 1) {
                System.out.println("Line " + (lineNumber + 1) + " contains value of " + line);
                lineValue = Integer.parseInt(line);
            } else {
                System.out.println("Line " + (lineNumber + 1) + " does not contain a numerical value");
            }

            totalValue += lineValue;
            lineValue = 0;
        }

        return totalValue;
    }
}

