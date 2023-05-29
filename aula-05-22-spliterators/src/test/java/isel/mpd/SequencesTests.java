package isel.mpd;

import isel.mpd.sequences.Sequence;
import org.junit.jupiter.api.Test;

import java.util.function.Supplier;

public class SequencesTests {

    @Test
    public void checkCountAndElementsOfASequenceProducedByFilter() {
        Supplier<Sequence<Integer>> seq =
            () -> Sequence.of(1,2,3,4,5,6);

        var seq2 = seq.get().filter(i -> i %2 == 0);
        var seq3 = seq.get().filter(i -> i %2 == 0);

        seq2.forEach(System.out::println);
        int countElems = seq3.count();
        System.out.println(countElems);
    }
}
