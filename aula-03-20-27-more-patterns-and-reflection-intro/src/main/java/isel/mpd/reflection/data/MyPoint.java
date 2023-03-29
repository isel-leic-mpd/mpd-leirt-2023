package isel.mpd.reflection.data;

public class MyPoint {
    public  final int x, y;

    public  MyPoint() {
        x = 0;
        y = 0;
    }

    public MyPoint(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public String toString() {
        return String.format("(%d,%d)", x, y);
    }
}
