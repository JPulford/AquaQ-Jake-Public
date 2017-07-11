package uk.co.aquaq.javachallenge1;

import java.util.List;

public class PartASumValues {

    /**
     * Sums values of integers contained in text file passed in
     * @param stringsFromFile - strings read from each line of file
     * @return sum total of numerical values in file
     */
    public int sumTotal(final List<String> stringsFromFile)  {

        int valueHeldInCurrentLine = 0;
        int totalValue = 0;

        for (int lineNumber = 0; lineNumber < stringsFromFile.size(); lineNumber++) {
            String line = stringsFromFile.get(lineNumber);
            // match only lines containing numbers, excluding empty lines
            if (line.matches("[0-9]+")) {
                System.out.println("Line " + (lineNumber + 1) + " contains value of " + line);
                valueHeldInCurrentLine = Integer.parseInt(line);
            } else {
                System.out.println("Line " + (lineNumber + 1) + " does not contain a numerical value");
            }

            totalValue += valueHeldInCurrentLine;
            valueHeldInCurrentLine = 0;
        }
        return totalValue;
    }
}

