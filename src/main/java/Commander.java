import java.util.ArrayList;

public class Commander {
    private final ArrayList<Habitat> isles;
    private ArrayList<String> results;


    public Commander(int[][] data, int sizeOfPopulation, int dimension, int bestKnown) {
        isles = new ArrayList<>();
        results = new ArrayList<>();
        Monitor monitor = new Monitor();
        for (int i = 0; i < 10; i++) {
            isles.add(new Habitat(data, i, monitor, sizeOfPopulation, dimension, bestKnown, results));
        }
        while (!(monitor.getThreadCounter() == 10)) {
            try {
                Thread.sleep(1000);
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
