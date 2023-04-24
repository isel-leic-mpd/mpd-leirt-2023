package isel.mpd.queries.lazy;

import isel.mpd.queries.lazy.iterators.FilterIterator;
import isel.mpd.queries.lazy.iterators.MapIterator;
import isel.mpd.queries.lazy.iterators.Multiple5Iterator;

import java.util.function.Function;
import java.util.function.Predicate;

public class Queries1 {
    public static <T> Iterable<T> filter(
        Iterable<T> src, Predicate<T> pred) {
        return () -> new FilterIterator<>(src, pred);
    }

    public static <T,U> Iterable<U> map(
        Iterable<T> src, Function<T,U> mapper) {

        return () -> new MapIterator<>(src,mapper);
    }

    public static Iterable<Integer> multiplesOf5() {
        return () -> new Multiple5Iterator();
    }
}
