import java.util.*;

public class Population {
    final private Random rand;
    final private int[][] distances;
    public Individual[] population;
    public ArrayList<Individual> parents;
    //Individual p1,p2;
    public Population(Individual individual, int[][] distances) {
        this.distances = distances;
        this.population = new Individual[100];
        this.parents = new ArrayList<>();
        this.rand = new Random();
        this.init(individual);
    }
    public void init(Individual individual) {
        //System.out.println("inicjuje populacje: ");
        for (int i = 0; i < 100; i++) {
            Individual individual1 = new Individual(individual.size, this.permutation(individual.getGenotype(), individual.size));
            this.population[i] = individual1;
        }
    }
    public void mutatePopulation(double probability) {
        for (Individual individual : population) {
            if (rand.nextDouble() < probability) {
                individual.mutationSwap(rand.nextInt(individual.size/2), 2* rand.nextInt(individual.size/2));
            }
        }
    }
    private int[] permutation(int[] arg, int size) {
        int[] pi = new int[size];

        for (int i = 0; i < size; i++) {
            pi[i] = arg[i];

        }

            for (int i = 0; i < size; i++) {
            int tmp1 = pi[i];
            int r = rand.nextInt(size);
            pi[i] = pi[r];
            pi[r] = tmp1;

        }
        //System.out.println("utworzono nowy genotyp");
        return pi;
    }

    public void doCrossing(){   //wyznacza nowa populacje, 80% to nowe dzieci, 20% to najsilniejsze osobniki z poprzedniego pokolenia
        //population.clear();
        int i = 0;
        while (i < 80) {
            for (int j = 0; j < 99; j++) {

                if (parents.get(j).compareTo(parents.get(j + 1)) != 0 && i < 80) {
                    Individual[] children = orderCrossover(parents.get(j), parents.get(j + 1));
                    population[i] = children[0];
                    population[i + 1] = children[1];
                    i+=2;
                }
            }
        }
        Collections.sort(parents);
        for (int j = 99; j > 79; j-- ) {
            population[j] = (parents.get(j));
        }
        parents.clear();
        //System.out.println("populacja sie rozmnozyla");
    }

    public void printPopulation(int x) {
        if (x == 1) {
            for (Individual individual : this.parents) {
                for (int i = 0; i < individual.size; i++) {
                    System.out.print(individual.getGen(i) + " " + individual.getGen(i + 1) + "   ");
                }
                System.out.println();
            }
        } else if (x == 0) {
            for (Individual individual : this.population) {
                for (int i = 0; i < individual.size - 1; i++) {
                    System.out.print(individual.getGen(i) + " " + individual.getGen(i + 1) + "   ");
                }
                System.out.println();
            }
        }
    }
    public void selection() {   //losujac liczbe od 0 do 1 decyduje ktore osobniki (w zaleznosci od adaptacji) przezyja
        int i = 0;
        while (i < 100) {
            for (Individual individual : population) {
                if (i == 100) {
                    return;
                }
                if (rand.nextDouble() < individual.getAdaptation()) {
                    parents.add(individual);
                    i++;
                }
            }
        }
        //System.out.println("wyselekcjonowano najsilniejsze osobniki");
    }
    public void resolveAdaptation() {   //przydziela osobnikom prawdopodobienstwo przezycia wyznaczone w stosunku do najsilniejszego osobnika
        double bestAdapted = OF(population[0]);
        for (Individual individual : population) {
            if (OF(individual) < bestAdapted) {
                bestAdapted = OF(individual);
            }
        }
        for (Individual individual : population) {
            individual.changeAdaptation(bestAdapted/OF(individual));
        }
        //System.out.println("przydzielono zywotnosc");
    }

    public Individual[] cycleCrossover(Individual parent1, Individual parent2) {
        int size = parent1.size;
        Individual[] children = new Individual[2];
        int[] osrping1 = new int[parent1.size];
        int[] osrping2 = new int[parent2.size];

        for (int i = 0, j = size - 1; i <= size/2; i++, j--) {
            osrping1[i] = parent2.getGen(i);
            osrping2[i] = parent1.getGen(i);
            osrping1[j] = parent1.getGen(j);
            osrping2[j] = parent2.getGen(j);
        }
        children[0] = new Individual(size, osrping1);
        children[1] = new Individual(size, osrping2);


        return children;
    }
    public Individual[] orderCrossover(Individual parent1, Individual parent2) {
        Individual[] children = new Individual[2];
        int[] osrping1 = new int[parent1.size];
        int[] osrping2 = new int[parent2.size];


        int beg = rand.nextInt(parent1.size/2); //losowanie liczby od 0 do 4
        int end = beg + rand.nextInt(parent1.size/2);  //losowanie liczby z przedizalu
        //System.out.println("beg: " + beg + " end: " + end);
        for (int i = beg; i <= end; i++) {
            osrping1[i] = parent1.getGen(i);


            osrping2[i] = parent2.getGen(i);

        }


        //uzupelniam tyl genomu pierwszego potomka

        int c = end + 1;
        int i = end + 1;
        while (i < parent1.size){
            if (doesNotContain(osrping1, parent2.getGen(c), beg, end)) {  //potomek nie zawiera genu o indeksie c, dodaje gen i wyrzucam go z puli
                osrping1[i] = parent2.getGen(c);

                c = (c + 1)% parent1.size;
                i++;
            } else {    //potomek zawiera juz gen c wiec wyrzucam go z puli
                c = (c + 1)% parent1.size;
            }
        }
        //uzupelniam przod genomu pierwszego potomka
        i = 0;
        while (i < beg) {
            if (doesNotContain(osrping1, parent2.getGen(c), beg, end)) {  //potomek nie zawiera genu o indeksie c, dodaje gen i wyrzucam go z puli
                osrping1[i] = parent2.getGen(c);
                osrping1[i] = parent2.getGen(c);
                c = (c + 1)% parent1.size;
                i++;
            } else {    //potomek zawiera juz gen c wiec wyrzucam go z puli
                c = (c + 1)% parent1.size;
            }
        }




        //uzupelniam tyl genomu drugiego potomka
        i = end + 1;
        c = end + 1;
        while (i < parent1.size){
            if (doesNotContain(osrping2, parent1.getGen(c), beg, end)) {  //potomek nie zawiera genu o indeksie c, dodaje gen i wyrzucam go z puli
                osrping2[i] = parent1.getGen(c);

                c = (c + 1)% parent1.size;
                i++;
            } else {    //potomek zawiera juz gen c wiec wyrzucam go z puli
                c = (c + 1)% parent1.size;
            }
        }



        //uzupelniam prodz genomu drugiego potomka
        i = 0;
        while (i < beg){
            if (doesNotContain(osrping2, parent1.getGen(c), beg, end)) {  //potomek nie zawiera genu o indeksie c, dodaje gen i wyrzucam go z puli
                osrping2[i] = parent1.getGen(c);
                c = (c + 1)% parent1.size;
                i++;
            } else {    //potomek zawiera juz gen c wiec wyrzucam go z puli
                c = (c + 1)% parent1.size;
            }
        }
        //System.out.println();

        children[0] = new Individual(parent1.size, osrping1);
        children[1] = new Individual(parent2.size, osrping2);
        //System.out.println("skrzyzonwano 2 osobniki i uzyskano potomstwo");
        return children;
    }
    private static boolean doesNotContain(int[] tabu, int x, int p, int q) {   //true jesli tablica nie zawiera punktu (x,y) miedzy pozycjami p i q
        for (int i = p; i <= q; i++) {
            if (tabu[i] == x) {
                return false;
            }
        }
        return true;
    }
    private int OF(Individual individual) {
        int f = distances[individual.getGen(0)][individual.getGen(individual.size - 1)];
        //System.out.println(f);
        for (int i = 0; i < individual.size - 1; i++) {
            f += distances[individual.getGen(i)][individual.getGen(i + 1)];
            //System.out.println(distances[individual.getGen(i)][individual.getGen(i + 1)]);
        }
        return f;
    }
    public int getOF(Individual individual) {
        return OF(individual);
    }
    private Individual findaAlpha() {
        Individual bestInvidual = population[0];
        double bestOF = OF(population[0]);
        for (Individual individual : population) {
            if (OF(individual) < bestOF) {
                bestInvidual = individual;
            }
        }
        return bestInvidual;
    }
    public Individual getAlpha() {
        return this.findaAlpha();
    }
    public void printTheBest() {
        System.out.println("Najlepszy osobnik ma OF wilkisci: " + OF(this.findaAlpha()));
    }


}
