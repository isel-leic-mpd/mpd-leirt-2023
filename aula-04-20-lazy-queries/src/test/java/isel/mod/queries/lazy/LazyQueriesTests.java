package isel.mod.queries.lazy;

import org.junit.jupiter.api.Test;

import static isel.mpd.queries.lazy.Queries1.multiplesOf5;

public class LazyQueriesTests {
    @Test
    public void checkMultiplesOf5List() {
        for(int i : multiplesOf5()) {
            System.out.println(i);
        }
    }
}
