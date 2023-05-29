package isel.mpd.sequences;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public interface Sequence<T> {
    boolean tryAdvance(Consumer<T> action);

    // constuctor operations

    static <T> Sequence<T> empty() {
        return action -> false;
    }

    static <T> Sequence<T> of(T ... args) {
        int[] index = {0};
        return action -> {
            if (index[0] >= args.length) return false;
            action.accept(args[index[0]++]);
            return true;
        };
    }

    // intermediate operations

    default <U> Sequence<U> map(Function<T,U> mapper) {
        return action -> tryAdvance(
                    t -> action.accept(mapper.apply(t)));
    }

    default Sequence<T> concat(Sequence<T> other) {
        return action -> {
            if (tryAdvance(action)) return true;
            return (other.tryAdvance(action));
        };
    }

    default Sequence<T> filter(Predicate<T> pred) {
       return action -> {
            boolean[] done = {false};
            while (!done[0] && tryAdvance(t -> {
                if (pred.test(t)) {
                    action.accept(t);
                    done[0] = true;
                }

            }));
            return done[0];
        };
    }

    default Sequence<T> concat2(Sequence<T> other) {
        return action ->
            tryAdvance(action) || other.tryAdvance(action);
    }

    // terminal operation

    default void forEach(Consumer<T> action) {
        while (tryAdvance(action)) {}
    }

    default int count() {
        int c = 0;
        while (tryAdvance(__ -> {})) c++;
        return c;
    }
}
