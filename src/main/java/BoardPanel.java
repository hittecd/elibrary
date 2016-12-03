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

public class BoardPanel extends JPanel implements MouseListener{

    public interface BoardPanelListener {
        void onEdgeClick();
        void onCornerClick();
        void onHexClick();
    };

    private BoardPanelListener boardPanelListener;

    public void setBoardPanelListener(BoardPanelListener listener) {
        this.boardPanelListener= listener;
    }

    private GameState currentState;

    private static final long serialVersionUID = 1L;
    private final int WIDTH = 790;
    private final int HEIGHT = 590;
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
    public Polygon[] Edges2D = new Polygon[72];
    private final int[][] HexPointsMap = {{0,1,4,9,8,3},
            {2,3,8,14,13,7}, {4,5,10,16,15,9},
            {6,7,13,19,18,12}, {8,9,15,21,20,14}, {10,11,17,23,22,16},
            {13,14,20,26,25,19}, {15,16,22,28,27,21},
            {18,19,25,31,30,24}, {20,21,27,33,32,26}, {22,23,29,35,34,28},
            {25,26,32,38,37,31}, {27,28,34,40,39,33},
            {30,31,37,43,42,36}, {32,33,39,45,44,38}, {34,35,41,47,46,40},
            {37,38,44,49,48,43}, {39,40,46,51,50,45},
            {44,45,50,53,52,49}};
    private final int[][] EdgePointMap = {{0,0,1},
            {1,3,0}, {2,1,4},
            {0,2,3}, {0,4,5},
            {1,7,2}, {2,3,8}, {1,9,4}, {2,5,10},
            {0,6,7}, {0,8,9}, {0,10,11},
            {1,12,6}, {2,7,13}, {1,14,8}, {2,9,15}, {1,16,10}, {2,11,17},
            {0,13,14}, {0,15,16},
            {2,12,18}, {1,19,13}, {2,14,20}, {1,21,15}, {2,16,22}, {1,23,17},
            {0,18,19}, {0,20,21}, {0,22,23},
            {1,24,18}, {2,19,25}, {1,26,20}, {2,21,27}, {1,28,22}, {2,23,29},
            {0,25,26}, {0,27,28},
            {2,24,30}, {1,31,25}, {2,26,32}, {1,33,27}, {2,28,34}, {1,35,29},
            {0,30,31}, {0,32,33}, {0,34,35},
            {1,36,30}, {2,31,37}, {1,38,32}, {2,33,39}, {1,40,34}, {2,35,41},
            {0,37,38}, {0,39,40},
            {2,36,42}, {1,43,37}, {2,38,44}, {1,45,39}, {2,40,46}, {1,47,41},
            {0,42,43}, {0,44,45}, {0,46,47},
            {2,43,48}, {1,49,44}, {2,45,50}, {1,51,46},
            {0,48,49}, {0,50,51},
            {2,49,52}, {1,53,50},
            {0,52,53}};


    private Font font = new Font("Arial", Font.BOLD, 18);
    FontMetrics metrics;

    public BoardPanel() {
        addMouseListener(this);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
    }

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
        // Setup the Edge Array
        for(int i = 0; i < 72; i++){
            //System.out.println("\nEdge Number " + i);
            int[] tempXpts = new int[4];
            int[] tempYpts = new int[4];
            if(EdgePointMap[i][0] == 0){
                //System.out.print("Point 1  ");
                tempXpts[0] = XPoints[EdgePointMap[i][1]] + origin.x;
                tempYpts[0] = YPoints[EdgePointMap[i][1]] + origin.y+6;
                //System.out.print("Point 2  ");
                tempXpts[1] = XPoints[EdgePointMap[i][1]] + origin.x;
                tempYpts[1] = YPoints[EdgePointMap[i][1]] + origin.y-6;
                //System.out.print("Point 3  ");
                tempXpts[2] = XPoints[EdgePointMap[i][2]] + origin.x;
                tempYpts[2] = YPoints[EdgePointMap[i][2]] + origin.y-6;
                //System.out.print("Point 4  ");
                tempXpts[3] = XPoints[EdgePointMap[i][2]] + origin.x;
                tempYpts[3] = YPoints[EdgePointMap[i][2]] + origin.y+6;
            }
            else if(EdgePointMap[i][0] == 1){
                //System.out.print("Point 1  ");
                tempXpts[0] = XPoints[EdgePointMap[i][1]] + origin.x - 3;
                tempYpts[0] = YPoints[EdgePointMap[i][1]] + origin.y - 5;
                //System.out.print("Point 2  ");
                tempXpts[1] = XPoints[EdgePointMap[i][1]] + origin.x + 3;
                tempYpts[1] = YPoints[EdgePointMap[i][1]] + origin.y + 5;
                //System.out.print("Point 3  ");
                tempXpts[2] = XPoints[EdgePointMap[i][2]] + origin.x + 3;
                tempYpts[2] = YPoints[EdgePointMap[i][2]] + origin.y + 5;
                //System.out.print("Point 4  ");
                tempXpts[3] = XPoints[EdgePointMap[i][2]] + origin.x - 3;
                tempYpts[3] = YPoints[EdgePointMap[i][2]] + origin.y - 5;
            }
            else {
                //System.out.print("Point 1  ");
                tempXpts[0] = XPoints[EdgePointMap[i][1]] + origin.x + 3;
                tempYpts[0] = YPoints[EdgePointMap[i][1]] + origin.y - 5;
                //System.out.print("Point 2  ");
                tempXpts[1] = XPoints[EdgePointMap[i][1]] + origin.x - 3;
                tempYpts[1] = YPoints[EdgePointMap[i][1]] + origin.y + 5;
                //System.out.print("Point 3  ");
                tempXpts[2] = XPoints[EdgePointMap[i][2]] + origin.x - 3;
                tempYpts[2] = YPoints[EdgePointMap[i][2]] + origin.y + 5;
                //System.out.print("Point 4  ");
                tempXpts[3] = XPoints[EdgePointMap[i][2]] + origin.x + 3;
                tempYpts[3] = YPoints[EdgePointMap[i][2]] + origin.y - 5;
            }
            Edges2D[i] = new Polygon(tempXpts,tempYpts,4);
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
        for(int i = 0; i < 72; i++){
            g2d.setColor(new Color(0xFF0000));
            g2d.fill(Edges2D[i]);
        }
        for(int i = 0; i < 54; i++){
            g2d.setColor(new Color(0xFFFFFF));
            g2d.fill(Corner2D[i]);
        }
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
        for(int i = 0; i < 72; i++){
            if(Edges2D[i].contains(e.getX(),e.getY())){
                System.out.println("Mouse Clicked on Edge #" + i);
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
