package isel.mpd.reflection.data;

import java.io.Serializable;

public class MyPoint implements Serializable  {
    private int x, y;
    private String name;
    private transient Double originDist;
    /*
    public MyPoint() {

    }
     */

    public MyPoint(int x, int y, String name) {
        this.x = x;
        this.y = y;
        this.name = name;
    }

    public MyPoint(int x, int y ) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }

    public String toString() {
        return String.format("(%d,%d)", x, y);
    }

    public boolean equals(Object other) {
        if (other == null || other.getClass() != MyPoint.class)
            return false;
        MyPoint op = (MyPoint) other;
        return x == op.x && y == op.y;
    }

    Double distanceFromOrigin() {
        if (originDist == null)
          originDist = Math.sqrt(x*x + y*y);
        return originDist;
    }
}
