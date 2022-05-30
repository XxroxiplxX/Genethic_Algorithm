public class Individual implements Solution {
    public int[] genotype;
    public int size;
    private double adaptation;
    public int result;
    public Individual(int size, int[] genotype) {
        this.size = size;
        this.genotype = genotype;

    }


    public double getAdaptation() {
        return adaptation;
    }

    public int getGen(int i) {
        return this.genotype[i];
    }
    public int[] getGenotype() {
        return this.genotype;
    }

    /*public double objectiveFunction(int[][] distances) {
        double f = 0;
        for (int i = 0 ; i < this.size - 1; i++) {
            f += distances[getGen(i][getGen(i,1)];
        }

        return f;
    }*/


    public void changeAdaptation(double adaptation) {
        this.adaptation = adaptation;
    }
    /*
    public double getObjectiveFunction() {
        return this.objectiveFunction(this.genotype);
    }
    public double getFenotype() {
        return objectiveFunction(this.genotype);
    }*/

    @Override
    public int compareTo(Individual o) {
        if (this.getAdaptation() < o.getAdaptation()) {
            return -1;
        } else  if (this.getAdaptation() > o.getAdaptation()){
            return 1;
        } else  {
            return  0;
        }
    }
    public void mutationInsert(int p, int q) {
        assert p < q;  //q = r2
        for (int i = q - 1; i > p; i--) {
            int tmp = genotype[i + 1];
            genotype[i + 1] = genotype[i];
            genotype[i] = tmp;
        }
    }
    public void mutationSwap(int p, int q) {
        int tmp = genotype[p];
        genotype[p] = genotype[q];
        genotype[q] = tmp;
    }
    public void printIndividual() {
        for (int i = 0; i < size; i++) {
            System.out.print(this.getGen(i) + " ");
        }
        System.out.println();
    }
}
