import java.util.ArrayList;

public class Commander {
    private ArrayList<Habitat> isles;
    private final Monitor monitor;


    public Commander(int[][] data, int sizeOfPopulation, int dimension, int bestKnown) {
        isles = new ArrayList<>();
        monitor = new Monitor();
        for (int i = 0; i < 1; i++) {
            isles.add(new Habitat(data, i, monitor, 50, dimension, bestKnown));
        }
    }

}
