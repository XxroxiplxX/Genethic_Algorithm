import java.util.ArrayList;

public class Habitat implements Runnable{

    private final Population population;
    int[][] data;
    private final Monitor monitor;
    private final ArrayList<String> results;
    private DataCollector dataCollector;
    private Individual pioneer;
    private final int bestKnown;
    private final double ppbOfMutation;
    final int id;
    public Habitat(int[][] data, int id, Monitor monitor, int sizeOfPopulation, int lengthOfGenotype, int bestKnown, ArrayList<String> results, double ppbOfMutation, double rouletteCrit) {
        this.bestKnown = bestKnown;
        this.ppbOfMutation = ppbOfMutation;
        this.results = results;
        this.monitor = monitor;
        Thread thread = new Thread(this);
        this.id = id;
        this.data = data;
        int[] p1 = new int[lengthOfGenotype];
        for (int i = 0; i < lengthOfGenotype; i++) {
            p1[i] = i;
        }
        pioneer = new Individual(lengthOfGenotype,p1);
        population = new Population(pioneer, data, sizeOfPopulation, rouletteCrit);

        thread.start();

    }



    @Override
    public void run() {
        Individual alpha;
        int iterationsWithoutImprovement = 0;
        int crit = 10000;
        for (int i = 0; i < crit; i++) {
            //System.out.println("pokolenie " + i + " watku " + id);
            synchronized (monitor) {
                iterationsWithoutImprovement++;
                population.resolveAdaptation();
                population.selectionByRoulette();
                population.doCrossing();
                alpha = population.getAlpha();
                if (population.getOF(alpha) < population.getOF(pioneer)) {
                    pioneer = alpha;

                    if (id == 0) {
                        System.out.println(i + " " + population.getOF(pioneer) + " " + PRD(population.getOF(pioneer)) *100+ "%");
                    }
                    //System.out.println(iterationsWithoutImprovement);

                    iterationsWithoutImprovement = 0;
                }
                if (iterationsWithoutImprovement > 0.4*crit) {
                    population.mutatePopulation(0.9);
                    crit += 0.1*crit;
                    iterationsWithoutImprovement = 0;
                    if (id == 0) {
                        System.out.println("Fallout procedure " + i);
                    }
                } else {
                    population.mutatePopulation(ppbOfMutation);
                }

            }
            //population.printTheBest();
            //population.printPopulation(0);

            //System.out.println();

        }
        population.resolveAdaptation();
        population.selectionByRoulette();
        population.doCrossing();
        //dataCollector.collectData(population.getAlpha(), population.getOF(population.getAlpha()));
        if (id == 0) {
            System.out.println(crit - 1 + " " + population.getOF(pioneer) + " " + PRD(population.getOF(pioneer)) *100+ "%");
            population.getAlpha().printIndividual();
        } else {

            //String result = "Island " + id + " found a cycle with OF: " + population.getOF(population.getAlpha()) + " and PRD = " + PRD(population.getOF(pioneer)) *100+ "%";
            String result = population.getRouletteCrit() + ";" + PRD(population.getOF(pioneer)) *100;
            synchronized (monitor) {
                results.add(result);
            }

            //System.out.println("Island " + id + " found cycle with OF: " + population.getOF(population.getAlpha()));
        }
        monitor.incrementThreadCounter();
    }
    public Individual getAlphaFromHabitat() {
        return population.getAlpha();
    }
    private double PRD(int of) {
        return (double) (of - bestKnown)/of;
    }
}
