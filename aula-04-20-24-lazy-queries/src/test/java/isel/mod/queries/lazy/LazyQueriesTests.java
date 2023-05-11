package isel.mod.queries.lazy;

import isel.mpd.queries.lazy.utils.IntPair;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static isel.mpd.queries.lazy.Queries1.*;
import static org.junit.jupiter.api.Assertions.*;

public class LazyQueriesTests {
    @Test
    public void checkMultiplesOf5List() {
        for(int i : multiplesOf5()) {
            System.out.println(i);
        }
    }

    @Test
    public void checkFirst3MultiplesOf15FromMultiplesOf5() {
        var expected = List.of(15, 30, 45);

        var list =  toList(
                        limit(
                            filter(
                                multiplesOf5(),
                                n -> n % 15 == 0
                            ),
                            3
                        ));

        assertEquals(expected, list);

    }

    private Iterable<IntPair> combs2(int li, int ls) {

        List<IntPair> res = new ArrayList<>();

        for(int i= li; i <= ls; i++) {
            for(int j = i + 1; j <= ls; j++) {
                res.add(new IntPair(i, j));
            }
        }
        return res;
    }

    private List<Character> chars(String s) {
        var l = new ArrayList<Character>();
        for(int i=0; i< s.length(); ++i)
            l.add(s.charAt(i));
        return l;
    }

    private int countOccurrences(
        List<List<String>> words,
        char letter
    ) {
     return
            count(
                filter(
                    flatMap(
                        flatMap(words, l -> l),
                        s -> chars(s)
                    ),
                    c -> c == letter
                )
            );
    }


    @Test
    public void getAllCombinationsOf2IntsBetween1And10() {
        var res = flatMap(
            range(1, 10),
            i1 ->
                map(
                    range(i1+1, 10),
                    i2 -> new IntPair(i1, i2)
                )
        );

        for(var c : res)
             System.out.println(c);
    }

    @Test
    public void countOcurrencesTest() {
        List<List<String>> data = List.of(
            List.of( "abc" , "bd"),
            List.of(),
            List.of("123a", "bda")
        );

        int expected = 3;

        int res = countOccurrences(data, 'b');

        assertEquals(expected, res);
    }

}
