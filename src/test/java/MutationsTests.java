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
}
