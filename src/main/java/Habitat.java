
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
        for (int i = 0; i < 10000; i++) {
            //System.out.println("pokolenie " + i + " watku " + id);
            synchronized (monitor) {
                population.resolveAdaptation();
                population.selectionByTournament();
                population.doCrossing();
                alpha = population.getAlpha();
                if (population.getOF(alpha) < population.getOF(pioneer)) {
                    pioneer = alpha;
                    System.out.println(i + " " + population.getOF(pioneer) + " " + PRD(population.getOF(pioneer)) + "%");
                }
                population.mutatePopulation(0);
            }
            //population.printTheBest();
            //population.printPopulation(0);

            //System.out.println();

        }
        population.resolveAdaptation();
        population.selectionByTournament();
        population.doCrossing();
        //dataCollector.collectData(population.getAlpha(), population.getOF(population.getAlpha()));
        population.getAlpha().printIndividual();
    }
    public Individual getAlphaFromHabitat() {
        return population.getAlpha();
    }
    private double PRD(int of) {
        return (double) (of - bestKnown)/of;
    }
}
