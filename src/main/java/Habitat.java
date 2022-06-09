
public class Habitat implements Runnable{

    private final Population population;
    int[][] data;
    private final Monitor monitor;
    private DataCollector dataCollector;
    private Individual pioneer;
    private final int bestKnown;
    final int id;
    public Habitat(int[][] data,  int id, Monitor monitor, int sizeOfPopulation, int lengthOfGenotype, int bestKnown) {
        this.bestKnown = bestKnown;
        this.monitor = monitor;
        Thread thread = new Thread(this);
        this.id = id;
        this.data = data;
        int[] p1 = new int[lengthOfGenotype];
        for (int i = 0; i < lengthOfGenotype; i++) {
            p1[i] = i;
        }
        pioneer = new Individual(lengthOfGenotype,p1);
        population = new Population(pioneer, data, sizeOfPopulation);

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
                population.selectionByTournament();
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
                    population.mutatePopulation(0.8);
                    crit += 0.1*crit;
                    iterationsWithoutImprovement = 0;
                    System.out.println("Fallout procedure " + i);
                } else {
                    population.mutatePopulation(0.06);
                }

            }
            //population.printTheBest();
            //population.printPopulation(0);

            //System.out.println();

        }
        population.resolveAdaptation();
        population.selectionByTournament();
        population.doCrossing();
        //dataCollector.collectData(population.getAlpha(), population.getOF(population.getAlpha()));
        if (id == 0) {
            System.out.println(crit - 1 + " " + population.getOF(pioneer) + " " + PRD(population.getOF(pioneer)) *100+ "%");
            population.getAlpha().printIndividual();
        } else {
            System.out.println("Island " + id + " found cycle with OF: " + population.getOF(population.getAlpha()));
        }
    }
    public Individual getAlphaFromHabitat() {
        return population.getAlpha();
    }
    private double PRD(int of) {
        return (double) (of - bestKnown)/of;
    }
}
