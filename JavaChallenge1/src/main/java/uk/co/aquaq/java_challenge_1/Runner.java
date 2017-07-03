package uk.co.aquaq.java_challenge_1;

/**
 * Created by jpulford on 20/06/17.
 * Run to execute JavaChallenge1 classes
 */
public class Runner {

    public static void main(String[] args) {

        // txt file to read in
        String fileNameIn = "num.txt";

        PartASumValues partA = new PartASumValues();
        PartBAsciiAdder partB = new PartBAsciiAdder();
        PartCOrder partC = new PartCOrder();
        PartDRemoveBlanks partD = new PartDRemoveBlanks();

        partA.sumTotal(fileNameIn);
        partB.getAsciiValues(fileNameIn, "num2.txt" );
        partC.orderNumbersInFile(fileNameIn, "num3.txt");
        partD.removeBlanks(fileNameIn, "num4.txt");



    }
}
