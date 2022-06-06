public class DataCollector {
    private Individual result;
    private int bestOF;
    private final int bestknown;
    public DataCollector(int bestknown) {
        this.bestknown = bestknown;
    }

    public void collectData(Individual best, int of) {
        result = best;
        bestOF = of;
    }
    private double PRD() {
        return (double)(bestOF - bestknown)/bestknown;
    }
    public void printData() {

    }
}
