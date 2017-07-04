package java_challenge_1;

/**
 * Created by jpulford on 20/06/17.
 * Run to execute JavaChallenge1 classes
 */
public class Runner {

    public static void main(String[] args) {

        // txt file to read in
        String fileNameIn = "src/main/resources/num.txt";

        PartASumValues partA = new PartASumValues();
        PartBAsciiAdder partB = new PartBAsciiAdder();
        PartCOrder partC = new PartCOrder();
        PartDRemoveBlanks partD = new PartDRemoveBlanks();

        partA.sumTotal(fileNameIn);
        partB.getAsciiValues(fileNameIn, "src/main/resources/num2.txt" );
        partC.orderNumbersInFile(fileNameIn, "src/main/resources/num3.txt");
        partD.removeBlanks(fileNameIn, "src/main/resources/num4.txt");

    }
}
