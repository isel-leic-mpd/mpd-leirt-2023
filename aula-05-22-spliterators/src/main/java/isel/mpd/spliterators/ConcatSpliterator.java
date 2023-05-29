package isel.mpd.spliterators;

import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class ConcatSpliterator<T> extends Spliterators.AbstractSpliterator<T> {
    private final Spliterator<T> split1;
    private final Spliterator<T> split2;

    public ConcatSpliterator(Spliterator<T> s1, Spliterator<T> s2) {
        super(s1.estimateSize()+s2.estimateSize(),
            0);
        this.split1 = s1;
        this.split2 = s2;
    }

    public ConcatSpliterator(Stream<T> s1, Stream<T> s2) {
       this(s1.spliterator(), s2.spliterator());
    }

    @Override
    public boolean tryAdvance(Consumer<? super T> action) {
        return split1.tryAdvance(action) || split2.tryAdvance(action);
    }
}
