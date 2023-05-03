package isel.mod.queries.lazy;

import org.junit.jupiter.api.Test;

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
}
