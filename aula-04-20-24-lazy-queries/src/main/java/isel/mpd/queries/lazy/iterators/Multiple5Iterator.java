package isel.mpd.queries.lazy.iterators;

import java.util.Iterator;

public class Multiple5Iterator implements Iterator<Integer> {
    private int nextMultiple = 5;

    @Override
    public boolean hasNext() {
        return true;
    }

    @Override
    public Integer next() {
        int result = nextMultiple;
        nextMultiple += 5;
        return result;
    }
}
