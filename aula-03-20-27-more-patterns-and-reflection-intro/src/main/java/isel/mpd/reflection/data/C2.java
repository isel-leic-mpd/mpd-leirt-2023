package isel.mpd.reflection.data;

public class C2 extends C1 implements I2{
    String s;

    public C2(String s, int val) {
        super(new D(3.5), val);
        this.s = s;
    }

    public C2() {

    }
}
