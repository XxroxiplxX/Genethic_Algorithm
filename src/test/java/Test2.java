import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class Test2 {
    @Test
    public void readFromFile() {
        Parser parser = new Parser();
        int data[][] = parser.generateMatrixFromLowerDiagonalMatrixData(parser.getMatrixData("gr17.tsp"), "gr17.tsp");
        int data2[][] = parser.generateMatrixFromFullMatrixData(parser.getMatrixData("ftv33.atsp"), "ftv33.atsp");
        int data3[][] = parser.generateEuc2DMatrix(parser.getEuc2DNodesCoordinate("berlin52.tsp"), "berlin52.tsp");
        assertNotNull(data);
        assertNotNull(data2);
        assertNotNull(data3);
        System.out.println(parser.getFormat("berlin52.tsp"));
    }

}
