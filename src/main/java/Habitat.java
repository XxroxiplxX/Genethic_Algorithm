import java.util.ArrayList;
import java.util.Random;

public class Habitat implements Runnable{
    public ArrayList<Individual> test;
    private Population population;
    int[][] data;
    final int id;
    public Habitat(int[][] data,  int id) {
        Thread thread = new Thread(this);
        this.id = id;
        this.data = data;
        int[] p1 = {
                0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16
        };
        Individual pioneer = new Individual(17,p1);
        population = new Population(pioneer, data);

        thread.start();




    }
    public void evolution() {
        Random random = new Random();
        int[][] distances = new int[10][10];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (i == j) {
                    distances[i][j] = 0;
                } else {
                    distances[i][j] = random.nextInt(100);;
                }
            }
        }
        int[] p1 = {
                0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16
        };


        /*
        test.add(new Individual(11, p1));
        test.add(new Individual(11, p2));
        for (Individual individual : this.test) {
            for (int i = 0; i < 11; i++) {
                System.out.print(individual.getGenotype(i, 0) + " " + individual.getGenotype(i, 1) + "   ");
            }
            System.out.println();
        }*/

        Individual pioneer = new Individual(17,p1);
        Population population = new Population(pioneer, data);
        //population.init(pioneer);
        //population.printPopulation(0);
        //population.resolveAdaptation();
        //population.printPopulation(0);
        //population.selection();
        //population.printPopulation(1);
        //population.doCrossing();
        //population.printPopulation(0);
        for (int i = 0; i < 10000; i++) {
            System.out.println("pokolenie " + i + " watku " + id);
            population.resolveAdaptation();
            population.selection();
            population.doCrossing();
            population.mutatePopulation(0);
            population.printTheBest();
            //population.printPopulation(0);

            System.out.println();

        }
    }

    @Override
    public void run() {
        for (int i = 0; i < 10000; i++) {
            //System.out.println("pokolenie " + i + " watku " + id);
            population.resolveAdaptation();
            population.selection();
            population.doCrossing();
            population.mutatePopulation(0);
            //population.printTheBest();
            //population.printPopulation(0);

            //System.out.println();

        }
        System.out.println("Wyspa o id " + id + " odnalazla sciezke dlugosci: " + population.getOF(population.getAlpha()));
    }
    public Individual getAlphaFromHabitat() {
        return population.getAlpha();
    }
}
