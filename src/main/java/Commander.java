import java.util.ArrayList;

public class Commander {
    private final ArrayList<Habitat> isles;
    private ArrayList<String> results;


    public Commander(int[][] data, int sizeOfPopulation, int dimension, int bestKnown) {
        isles = new ArrayList<>();
        results = new ArrayList<>();
        Monitor monitor = new Monitor();
        double p = 0.165;
        double c = 0.675;
        int pop = 50;
        for (int i = 0; i < 20; i++) {
            isles.add(new Habitat(data, i, monitor, pop, dimension, bestKnown, results, p, c));
            //pop += 2;
            //p += 0.005;
            //c += 0.005;
        }
        while (!(monitor.getThreadCounter() == 20)) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println();
        for (String result : results) {
            System.out.println(result);
        }
    }

}
