package isel.mpd.queries.lazy.utils;

public class IntPair {
    public final int i1, i2;

    public IntPair(int i1, int i2) {
        this.i1 = i1; this.i2 = i2;
    }

    public String toString() {
        return String.format("(%d,%d)", i1, i2);
    }
}