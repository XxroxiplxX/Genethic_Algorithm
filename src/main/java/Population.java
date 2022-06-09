import java.util.*;

public class Population {
    final private Random rand;
    final private int[][] distances;
    public Individual[] population;
    private final int sizeOfPopulation;
    public ArrayList<Individual> parents;
    //Individual p1,p2;
    public Population(Individual individual, int[][] distances, int sizeOfPopulation) {
        this.sizeOfPopulation = sizeOfPopulation;
        this.distances = distances;
        this.population = new Individual[sizeOfPopulation];
        this.parents = new ArrayList<>();
        this.rand = new Random();
        this.init(individual);
    }
    public void init(Individual individual) {
        //System.out.println("inicjuje populacje: ");
        for (int i = 0; i < sizeOfPopulation; i++) {
            Individual individual1 = new Individual(individual.size, this.permutation(individual.getGenotype(), individual.size));
            this.population[i] = individual1;
        }
    }
    public void mutatePopulation(double probability) {
        for (Individual individual : population) {
            double r = rand.nextDouble();
            if (r < probability) {
                individual.mutationInvert(rand.nextInt(individual.size/2), 2* rand.nextInt(individual.size/2));
                //System.out.println();
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
        while (i < 0.8*sizeOfPopulation) {
            for (int j = 0; j < sizeOfPopulation - 1; j++) {

                if (parents.get(j).compareTo(parents.get(j + 1)) != 0 && i < 0.8*sizeOfPopulation) {
                    Individual[] children = orderCrossover(parents.get(j), parents.get(j + 1));
                    population[i] = children[0];
                    population[i + 1] = children[1];
                    i+=2;
                }
            }
        }
        Collections.sort(parents);
        for (int j = sizeOfPopulation - 1; j > 0.8*sizeOfPopulation - 1; j-- ) {
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
    public void selectionByRoulette() {   //losujac liczbe od 0 do 1 decyduje ktore osobniki (w zaleznosci od adaptacji) przezyja
        int i = 0;
        while (i < sizeOfPopulation) {
            for (Individual individual : population) {
                if (i == sizeOfPopulation) {
                    return;
                }
                if (rand.nextDouble() + 0.4 < individual.getAdaptation()) {
                    parents.add(individual);
                    i++;
                }
            }
        }
        //System.out.println("wyselekcjonowano najsilniejsze osobniki");
    }
    public void selectionByTournament() {
        int i = 0;
        int group = sizeOfPopulation/10;
        int[] indexes = new int[group];
        int best;
        int k = 0;
        while (i < sizeOfPopulation) {
            for (int j = 0; j < group; j++) {
                indexes[j] = rand.nextInt(sizeOfPopulation);
            }
            best = OF(population[indexes[0]]);
            for (int j = 1; j < group; j++) {
                if (OF(population[indexes[j]]) < best) {
                    best = OF(population[indexes[j]]);
                    k = j;  //na k-tej pozycji w tablicy indeksow jesty indeks z tablicy population zwyciezcy turnieju
                }
            }
            parents.add(population[indexes[k]]);
            i++;
        }
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

    public Individual[] halfCrossover(Individual parent1, Individual parent2) {
        Individual[] children = new Individual[2];
        int[] ospring1 = new int[parent1.size];
        int[] ospring2 = new int[parent2.size];
        int xPoint = rand.nextInt(parent2.size/2) + parent2.size/4;

        for (int i = 0; i < xPoint; i++) {
            ospring1[i] = parent1.getGen(i);
            ospring2[i] = parent2.getGen(i);
        }
        int c1 = xPoint;
        int c2 = xPoint;
        for (int i = 0; i < parent1.size; i++) {
            if (doesNotContain(ospring1, parent2.getGen(i), 0, parent1.size - 1)) {
                ospring1[c1] = parent2.getGen(i);
                c1++;
            }
            if (doesNotContain(ospring2, parent1.getGen(i), 0, parent1.size - 1)) {
                ospring2[c2] = parent1.getGen(i);
                c2++;
            }
        }
        children[0] = new Individual(parent1.size, ospring1);
        children[1] = new Individual(parent1.size, ospring2);

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
