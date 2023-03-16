package isel.mpd.painter1.view;

import isel.mpd.painter1.app.App;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JMenuBar;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JColorChooser;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map;

public class PainterFrame extends JFrame {
    public static int CANVAS_SIZE_X = 1024;
    public static int CANVAS_SIZE_Y = 600;

    // command names
    public static final String SHAPE_CMD_RECT = "Rect";
    public static final String SHAPE_CMD_LINE = "Line";
    public static final String SHAPE_CMD_TRIANGLE = "Triangle";
    public static final String SHAPE_CMD_OVAL = "Oval";
    public static final String SHAPE_CMD_CIRCLE = "Circle";

    // auxiliary state to shape interactive construction
    private Point start, curr;
    private String currShapeName;

    private ConfigDrawer currDrawer;

    // state variables
    private Color currColor = Color.BLACK;

    // components
    private JTextArea mouseHistory;
    private PaintPanel canvas;

    // interactive shape build state acessors
    public Point getStart() { return start; }
    public Point getCurr() { return curr; }
    public Color getColor() { return currColor; }

    // not used anymore
    public String getBuildShapeName() {
        return currShapeName;
    }

    private Map<String, ConfigDrawer> drawersMap =
        Map.ofEntries(
            Map.entry(SHAPE_CMD_RECT, new RectDrawer(this)),
            Map.entry(SHAPE_CMD_TRIANGLE, new TriangleDrawer(this))
        );

    public  ConfigDrawer createConfigDrawer(String name) {
        //if (name.equals(SHAPE_CMD_RECT)) return new RectDrawer(this);
        return drawersMap.get(name);
    }

    public  PainterFrame(App app){
        initComponents();
        buildMenu();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setResizable(false);
    }

    private  class PaintMouselistener extends  MouseAdapter {
        public void mousePressed(MouseEvent me) {
            start = me.getPoint();
            curr = start;

            // passes the canvas the drawer to use
            canvas.setBuildMode(currDrawer);
        }

        public void mouseDragged(MouseEvent me) {
            // TODO
            curr = me.getPoint();
            canvas.repaint();
        }

        public void mouseReleased(MouseEvent me) {
            // TODO
            currDrawer.process(canvas);
            canvas.resetBuildMode();
        }
    }

    private void initComponents() {
        BorderLayout layout = new BorderLayout();
        Container container = getContentPane();
        container.setLayout(layout);
        canvas = new PaintPanel(CANVAS_SIZE_X, CANVAS_SIZE_Y, Color.WHITE);
        PaintMouselistener mlistener = new PaintMouselistener();
        canvas.addMouseMotionListener(mlistener);
        canvas.addMouseListener(mlistener);

        mouseHistory = new JTextArea(6, 80);
        mouseHistory.setBackground(new Color(240,240,240));
        mouseHistory.setEditable(false); // set textArea non-editable
        var scroll = new JScrollPane(mouseHistory);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        container.add(canvas, BorderLayout.CENTER);
        container.add(scroll, BorderLayout.SOUTH);
    }

    private JMenuItem buildItem(String name, ActionListener action) {
        var item = new JMenuItem(name);
        item.addActionListener(action);
        return item;
    }

    private void addItem(String name, JMenu menu) {
        var item = new JMenuItem(name);
        item.addActionListener(evt -> {
            currDrawer = createConfigDrawer(name);
        });
        menu.add(item);
    }

    private void buildMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu creationSel = new JMenu("Add Shape");

        addItem(SHAPE_CMD_RECT, creationSel);

        /*
        creationSel.add(buildItem(SHAPE_CMD_RECT,
            evt -> {
                mouseHistory.append("add rectangle\n");
                currDrawer = new RectDrawer(this);

            }));
         */
        creationSel.add(buildItem(SHAPE_CMD_TRIANGLE,
            evt -> {
                mouseHistory.append("add triangle\n");
                // obsolete, change to config drawer
                currShapeName = SHAPE_CMD_TRIANGLE;

            }));
        creationSel.add(buildItem(SHAPE_CMD_OVAL,
            evt -> {
                mouseHistory.append("add oval\n");
                // obsolete, change to config drawer
                currShapeName = SHAPE_CMD_OVAL;

            }));
        creationSel.add(buildItem(SHAPE_CMD_LINE,
            evt -> {
                mouseHistory.append("add line\n");
                // obsolete, change to config drawer
                currShapeName = SHAPE_CMD_LINE;

            }));
        creationSel.add(buildItem(SHAPE_CMD_CIRCLE,
            evt -> {
                mouseHistory.append("add oval\n");
                // obsolete, change to config drawer
                currShapeName = SHAPE_CMD_CIRCLE;
            }));

        JMenu configSel = new JMenu("Config");
        JMenuItem color = new JMenuItem("Color");
        color.addActionListener(evt -> {
            currColor = JColorChooser.showDialog(null,"Choose Color", currColor);
        });
        configSel.add(color);
        menuBar.add(creationSel);
        menuBar.add(configSel);
        setJMenuBar(menuBar);
    }

}
