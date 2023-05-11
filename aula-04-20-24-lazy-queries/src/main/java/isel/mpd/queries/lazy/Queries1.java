package isel.mpd.queries.lazy;

import isel.mpd.queries.lazy.iterators.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
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


    public static Iterable<Integer> range(int li, int ls) {
        return () -> new Iterator<Integer>() {
            int next = li;

            @Override
            public boolean hasNext() {
                return next <= ls;
            }

            @Override
            public Integer next() {
                if (!hasNext())
                    throw new NoSuchElementException();
                return next++;
            }
        };
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

    public static <T,U> Iterable<U> flatMap(
        Iterable<T> src,
        Function<T,Iterable<U>> mapper) {

        return () -> new FlatMapIterator<>(src,mapper);
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

    public static <T> int count(Iterable<T> src) {
        int c = 0;
        for(T e : src) c++;
        return c;
    }
}
