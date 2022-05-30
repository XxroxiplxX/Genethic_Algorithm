import org.junit.jupiter.api.Test;

public class Test2 {
    @Test
    public void readFromFile() {
        Parser parser = new Parser();
        int data[][] = parser.generateMatrixFromLowerDiagonalMatrixData(parser.getMatrixData("gr17.tsp"), "gr17.tsp");
        System.out.println();
    }

}
