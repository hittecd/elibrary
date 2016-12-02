import javafx.scene.shape.Circle;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.*;

public class GameUI extends JPanel implements MouseListener{

    private static final long serialVersionUID = 1L;
    private final int WIDTH = 1200;
    private final int HEIGHT = 800;
    // This array holds all 54 X values for the corners.
    private final int[] XPoints = { -25, 25,
                                    -100, -50, 50, 100,
                                    -175, -125, -25, 25, 125, 175,
                                    -200, -100, -50, 50, 100, 200,
                                    -175, -125, -25, 25, 125, 175,
                                    -200, -100, -50, 50, 100, 200,
                                    -175, -125, -25, 25, 125, 175,
                                    -200, -100, -50, 50, 100, 200,
                                    -175, -125, -25, 25, 125, 175,
                                    -100, -50, 50, 100,
                                    -25, 25};
    // This array holds all 54 Y values for the corners.
    private final int[] YPoints = { -220, -220,
                                    -176, -176, -176, -176,
                                    -132, -132, -132, -132, -132, -132,
                                    -88, -88, -88, -88, -88, -88,
                                    -44, -44, -44, -44, -44, -44,
                                    0, 0, 0, 0, 0, 0,
                                    44, 44, 44, 44, 44, 44,
                                    88, 88, 88, 88, 88, 88,
                                    132, 132, 132, 132, 132, 132,
                                    176, 176, 176, 176,
                                    220, 220};
    public Polygon[] Hexes2D = new Polygon[19];
    public Ellipse2D.Double[] Corner2D = new Ellipse2D.Double[54];
    private final int[][] HexPointsMap = {{0,1,4,9,8,3},
            {2,3,8,14,13,7}, {4,5,10,16,15,9},
            {6,7,13,19,18,12}, {8,9,15,21,20,14}, {10,11,17,23,22,16},
            {13,14,20,26,25,19}, {15,16,22,28,27,21},
            {18,19,25,31,30,24}, {20,21,27,33,32,26}, {22,23,29,35,34,28},
            {25,26,32,38,37,31}, {27,28,34,40,39,33},
            {30,31,37,43,42,36}, {32,33,39,45,44,38}, {34,35,41,47,46,40},
            {37,38,44,49,48,43}, {39,40,46,51,50,45},
            {44,45,50,53,52,49}};


    private Font font = new Font("Arial", Font.BOLD, 18);
    FontMetrics metrics;

    public GameUI() {
        addMouseListener(this);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
    }

    //@Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        Point origin = new Point(WIDTH / 2, HEIGHT / 2);

        g2d.setStroke(new BasicStroke(4.0f, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER));
        g2d.setFont(font);
        metrics = g.getFontMetrics();

        drawCircle(g2d, origin, 250, true, true, 0x4488FF, 0);
        // Setup Hexagon Array
        for(int i = 0; i < 19; i++){
            //System.out.println("\nPolygon #" + i);
            int[] tempXPts = new int[6];
            int[] tempYPts = new int[6];
            for(int j = 0; j < 6; j++){
                //System.out.print("Point #" + (j+1) + " ");
                tempXPts[j] = XPoints[HexPointsMap[i][j]] + origin.x;
                tempYPts[j] = YPoints[HexPointsMap[i][j]] + origin.y;
            }
            Hexes2D[i] = new Polygon(tempXPts, tempYPts, 6);
        }
        // Setup the Corner Array
        for(int i = 0; i < 54; i++){
            Corner2D[i] = new Ellipse2D.Double((XPoints[i]-7+origin.x), (YPoints[i]-7+origin.y), 14, 14);
        }

        for(int i = 0; i < 19; i++){
            g2d.setColor(new Color(0x008844));
            g2d.fill(Hexes2D[i]);
            g2d.setColor(new Color(0xFFDD88));
            g2d.draw(Hexes2D[i]);
        }
        for(int i = 0; i < 54; i++){
            g2d.setColor(new Color(0xFFFFFF));
            g2d.fill(Corner2D[i]);
        }
    }

    private void drawHex(Graphics g, int posX, int posY, int x, int y, int r) {
        Graphics2D g2d = (Graphics2D) g;

        Hexagon hex = new Hexagon(x, y, r);
        String text = String.format("%s : %s", coord(posX), coord(posY));
        int w = metrics.stringWidth(text);
        int h = metrics.getHeight();

        hex.draw(g2d, x, y, 0, 0x008844, true);
        hex.draw(g2d, x, y, 4, 0xFFDD88, false);

        g.setColor(new Color(0xFFFFFF));
        g.drawString(text, x - w/2, y + h/2);
    }

    private String coord(int value) {
        return (value > 0 ? "+" : "") + Integer.toString(value);
    }

    public void drawCircle(Graphics2D g, Point origin, int radius,
                           boolean centered, boolean filled, int colorValue, int lineThickness) {
        // Store before changing.
        Stroke tmpS = g.getStroke();
        Color tmpC = g.getColor();

        g.setColor(new Color(colorValue));
        g.setStroke(new BasicStroke(lineThickness, BasicStroke.CAP_ROUND,
                BasicStroke.JOIN_ROUND));

        int diameter = radius * 2;
        int x2 = centered ? origin.x - radius : origin.x;
        int y2 = centered ? origin.y - radius : origin.y;

        if (filled)
            g.fillOval(x2, y2, diameter, diameter);
        else
            g.drawOval(x2, y2, diameter, diameter);

        // Set values to previous when done.
        g.setColor(tmpC);
        g.setStroke(tmpS);
    }

    public void mouseClicked(MouseEvent e) {
        for(int i = 0; i < 19; i++){
            if(Hexes2D[i].contains(e.getX(), e.getY())) {
                System.out.println("Mouse Clicked on Hex #" + i);
                break;
            }
        }
        for(int i = 0; i < 54; i++){
            if(Corner2D[i].contains(e.getX(), e.getY())){
                System.out.println("Mouse Clicked on Corner #" + i);
                break;
            }
        }
        //System.out.println("Mouse Not Clicked on a Hex or ERROR");
    }

    public void mousePressed(MouseEvent e) {

    }

    public void mouseReleased(MouseEvent e) {

    }

    public void mouseEntered(MouseEvent e) {

    }

    public void mouseExited(MouseEvent e) {

    }

    /*
    public static void main(String[] args) {
        JFrame f = new JFrame();
        Main p = new Main();

        f.setContentPane(p);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.pack();
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }
    */
}
