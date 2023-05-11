import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static isel.mpd.queries.lazy2.StreamIterable.from;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class StreamIterablesTests {

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
        return from(words)
            .flatMap(l -> l)
            .flatMap(s -> chars(s))
            .filter(    c -> c == letter)
            .count();

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
