package isel.mpd.painter1.view;

import java.awt.*;

// defines the contract for config drawers
// that is, to draw on a specified graphics the shape in construction
public interface ConfigDrawer {
    void draw(Graphics2D g);
    void process(PaintPanel canvas);
}
