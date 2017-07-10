package uk.co.aquaq.javachallenge1;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Run to execute JavaChallenge1 classes
 */
public class Runner {

    public static void main(String[] args) {

        // array to hold strings retrieved from file
        List<String> stringFromFile;

        // set input stream as num.txt, should work locally and when run as JAR
        final InputStream filePath = Thread.currentThread().getContextClassLoader().getResourceAsStream("num.txt");

        final ReadFileToArrayList inputReader = new ReadFileToArrayList();

        // read in lines from input stream and store strings in an arraylist
        // pass the contents to each challenge class
        stringFromFile = inputReader.convertFileToStringArray(filePath);

        PartASumValues partA = new PartASumValues();
        System.out.println(partA.sumTotal(stringFromFile));

        // instantiate fileWriter with output location
        WriteNewFile fileWriter = new WriteNewFile("target/num2.txt");
        PartBAsciiAdder partB = new PartBAsciiAdder(fileWriter);
        partB.getAsciiValues(stringFromFile);

        // reset fileWriter to point to new location
        fileWriter.resetFileWriter("target/num3.txt");
        PartCOrder partC = new PartCOrder(fileWriter);
        partC.orderNumbersInFile(stringFromFile);

        // reset fileWriter to point to new location
        fileWriter.resetFileWriter("target/num4.txt");
        PartDRemoveBlanks partD = new PartDRemoveBlanks(fileWriter);
        partD.removeBlanks(stringFromFile);

    }
}
