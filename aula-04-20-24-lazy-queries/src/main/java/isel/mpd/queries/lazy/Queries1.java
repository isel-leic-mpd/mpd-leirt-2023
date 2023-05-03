package isel.mpd.queries.lazy;

import isel.mpd.queries.lazy.iterators.FilterIterator;
import isel.mpd.queries.lazy.iterators.LimitIterator;
import isel.mpd.queries.lazy.iterators.MapIterator;
import isel.mpd.queries.lazy.iterators.Generator;
import isel.mpd.queries.lazy.iterators.Multiple5Iterator;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public class Queries1 {

    // operações Factory ou construtores de sequências
    public static Iterable<Integer> multiplesOf5() {
        //return () -> new Multiple5Iterator();
        return iterate(5, n -> n + 5);
    }

    public static <T> Iterable<T> iterate(T seed, Function<T,T> producer) {
        return () -> new Generator(seed, producer);
    }

    // operações intermédias

    public static <T> Iterable<T> filter(
        Iterable<T> src, Predicate<T> pred) {
        return () -> new FilterIterator<>(src, pred);
    }

    public static <T,U> Iterable<U> map(
        Iterable<T> src, Function<T,U> mapper) {

        return () -> new MapIterator<>(src,mapper);
    }

    public static <T> Iterable<T> limit(
        Iterable<T> src, int lim) {
        return () -> new LimitIterator<>(src,lim);
    }


    // operações terminais

    public static <T> List<T> toList(Iterable<T> src) {
        List<T> res =  new ArrayList<>();
        for(T e : src) res.add(e);
        return res;
    }
}
