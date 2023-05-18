package isel.leirt.mpd.weather3;

import org.junit.jupiter.api.Test;

import java.security.cert.CollectionCertStoreParameters;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class StreamTests {

    @Test
    public void check_lazyness_of_streams() {
        int[] p1 = {0};
        int[] p2 = {0};

        var seq = IntStream.rangeClosed(1, 10)
                           .peek(__ -> p1[0]++)
                           .filter(n -> n % 3 == 0)
                           .peek(__ -> p2[0]++);
        assertEquals(0, p1[0]);
        assertEquals(0, p2[0]);

        long sum = seq.sum();

        assertEquals(18,sum);
        assertEquals(10, p1[0]);
        assertEquals(3, p2[0]);
    }

    @Test
    public void multiple_stream_traverse() {
        var seq = Stream.of("a", "b", "c");

        assertThrows(IllegalStateException.class, () -> {
            assertEquals(3, seq.count());
            assertEquals(3, seq.count());
        });

    }


    @Test
    public void using_collect_to_group_stream_elements() {
        var words = Stream.of(
            "AA", "BC", "BA", "CC", "CB", "AC", "AB"
        );
        var map = words
            .collect(Collectors.groupingBy(s -> s.charAt(0)));

        System.out.println(map);
       // TODO
    }

    @Test
    public void using_reduce_to_get_sum_of_stream_of_ints() {
        var intSeq = IntStream.rangeClosed(1,100);
        // TODO
    }

    private boolean isPrime(long n) {
        if (n == 2) return true;
        if (n <= 1 || (n % 2 == 0)) return false;
        for(long d= 3; d < Math.sqrt(n); d += 2)
            if (n % d == 0) return false;
        return true;
    }


    @Test
    public void filter_primes_from_range_in_serial_and_parallel() {
        final long  PRIMES_IN_RANGE = 665024;
        var startTime = System.currentTimeMillis();
        var primesP = LongStream.range(1, 10_000_000)
                               .filter(l -> isPrime(l))
                               .mapToObj(l -> l)
                               .parallel()
                               .collect(Collectors.toList());
        var endTime = System.currentTimeMillis();
        System.out.println("Primes in parallel done in " + (endTime-startTime) + "ms");

        startTime = System.currentTimeMillis();
        var primesS = LongStream.range(1, 10_000_000)
                                .filter(l -> isPrime(l))
                                .mapToObj(l -> l)
                                .collect(Collectors.toList());
        endTime = System.currentTimeMillis();
        System.out.println("Primes in serial done in " + (endTime-startTime) + "ms");

        assertEquals(primesP, primesS);
        assertEquals(PRIMES_IN_RANGE,primesS.size() );
    }
}
