import java.util.ArrayList;

public class Test {
    public static void main(String[] args) {
        /*while (true) {
            System.out.println("(==(     )==)");
            System.out.println(" `-.`. ,',-'");
            System.out.println("    _,-'\"");
            System.out.println(" ,-',' `.`-.");
        }*/
        //habitat.evolution();
        Parser parser = new Parser();
        int[][] data = parser.generateMatrixFromFullMatrixData(parser.getMatrixData("ftv33.atsp"), "ftv33.atsp");
        Individual[] results = new Individual[2];

        ArrayList<Habitat> isles = new ArrayList<>();
        Monitor monitor = new Monitor();
        for (int i = 0; i < 16; i++) {
            isles.add(new Habitat(data, i, monitor, 50, 33));
        }
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
