package isel.mpd.functional;

import java.util.function.Function;

public interface MyFunction<T,R> {
    R apply(T t);

    default <V> MyFunction<V,R> compose(Function<V,T> before) {
        return v -> this.apply(before.apply(v));

    }

    default <V> MyFunction<T,V> andThen(Function<R,V> after) {
        return (T t) -> after.apply(apply(t));
    }
}
