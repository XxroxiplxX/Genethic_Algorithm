import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class MutationsTests {
    @Test
    public void invertTest() {
        int[] genotype = {1,2,3,4,5,6,7,8};
        Individual testIndividual = new Individual(8, genotype);
        testIndividual.mutationInvert(1,6);
        assertEquals(testIndividual.getGen(1), 7);
        testIndividual.printIndividual();
        testIndividual.mutationInvert(1,6);
        testIndividual.printIndividual();
        testIndividual.mutationSwap(1,6);
        assertEquals(testIndividual.getGen(1), 7);
        testIndividual.printIndividual();
        testIndividual.mutationSwap(1,6);
        testIndividual.mutationInsert(1,6);
        testIndividual.printIndividual();
    }
    @Test
    public void mutateAll() {
        int[] genotype = {1,2,3,4,5,6,7,8};
        Individual testIndividual = new Individual(8, genotype);
        Population population = new Population(testIndividual, null, 10);
        population.printPopulation(0);
        System.out.println("po mutacji");
        population.mutatePopulation(0.4);
        population.printPopulation(0);
    }
}
