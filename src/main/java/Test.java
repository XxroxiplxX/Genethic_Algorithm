import java.util.ArrayList;

public class Test {
    public static void main(String[] args) {    //przekazuje po prostu nazwe instancji
        /*while (true) {
            System.out.println("(==(     )==)");
            System.out.println(" `-.`. ,',-'");
            System.out.println("    _,-'\"");
            System.out.println(" ,-',' `.`-.");
        }*/
        //habitat.evolution();
        Parser parser = new Parser();
        String format = parser.getFormat(args[0]);
        int bestKnown = 1;
        try {
            bestKnown = Integer.parseInt(args[1]);
        } catch (NumberFormatException e ) {
            e.printStackTrace();
        }
        System.out.println(format);
        int dimension = parser.getDimension(args[0]);
        int[][] data;
        switch (format) {
            case "LOWER_DIAG_ROW":
                data = parser.generateMatrixFromLowerDiagonalMatrixData(parser.getMatrixData(args[0]), args[0]);
                break;
            case "":
                data = parser.generateEuc2DMatrix(parser.getEuc2DNodesCoordinate(args[0]), args[0]);
                break;
            case "FULL_MATRIX":
                data = parser.generateMatrixFromFullMatrixData(parser.getMatrixData(args[0]), args[0]);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + format);
        }

        Commander commander = new Commander(data, 200, dimension, bestKnown);
        //Habitat habitat = new Habitat(data, 0, monitor, 50);

        System.out.println();
    }
        /*
        int[][] p1 = {
                {3,0},
                {4,0},
                {8,0},
                {2,0},
                {7,0},
                {1,0},
                {6,0},
                {5,0}
        };
        int[][] p2 = {
                {4,0},
                {2,0},
                {5,0},
                {1,0},
                {6,0},
                {8,0},
                {3,0},
                {7,0}
        };
        Individual parent1 = new Individual(8, p1);
        Individual parent2 = new Individual(8, p2);
        Population population = new Population(parent1, parent2);
        Individual[] children = population.orderCrossover(parent1, parent2);
        for (Individual ind : children) {
            for (int i = 0; i < 8; i++) {
                System.out.print(ind.genotype[i][0] + " ");
            }
            System.out.println();
        }
    }*/


}
