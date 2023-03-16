package isel.mpd.painter1.view;

import java.awt.*;

public class RectDrawer implements ConfigDrawer{
    private PainterFrame frame;
    public RectDrawer(PainterFrame frame) {
       this.frame = frame;
    }


    @Override
    public void draw(Graphics2D g) {
        int w = frame.getCurr().x - frame.getStart().x + 1;
        int h = frame.getCurr().y - frame.getStart().y + 1;
        g.drawRect(frame.getStart().x, frame.getStart().y, w, h);
    }

    @Override
    public void process(PaintPanel canvas) {
        int width = frame.getCurr().x - frame.getStart().x+ 1;
        int height = frame.getCurr().y - frame.getStart().y + 1;
        canvas.addRect(frame.getStart(), width, height, frame.getColor());
    }
}
