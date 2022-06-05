
public class Habitat implements Runnable{

    private final Population population;
    int[][] data;
    private final Monitor monitor;
    final int id;
    public Habitat(int[][] data,  int id, Monitor monitor, int sizeOfPopulation) {
        this.monitor = monitor;
        Thread thread = new Thread(this);
        this.id = id;
        this.data = data;
        int[] p1 = {
                0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16
        };
        Individual pioneer = new Individual(17,p1);
        population = new Population(pioneer, data, sizeOfPopulation);

        thread.start();




    }


    @Override
    public void run() {
        for (int i = 0; i < 10000; i++) {
            //System.out.println("pokolenie " + i + " watku " + id);
            synchronized (monitor) {
                if (i == 2000) {
                    //System.out.println("czekam");
                    //monitor.goWaiting();

                } else if (i == 5000) {
                    //System.out.println("wracam");
                    //monitor.ressurect();
                }
                population.resolveAdaptation();
                population.selection();
                population.doCrossing();
                population.mutatePopulation(0);
            }
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
