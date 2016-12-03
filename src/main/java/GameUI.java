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
import javax.swing.border.Border;

public class GameUI extends JPanel{

    private static final long serialVersionUID = 1L;
    private final int WIDTH = 1220;
    private final int HEIGHT = 810;

    private JPanel leftPane = new JPanel();
    private JPanel rightPane = new JPanel();
    private JPanel boardPane = new BoardPanel();
    private JPanel resourcePane = new ResourcePanel();
    private JPanel controlPanel = new ControlPanel();

    private JLabel resourceBoxLabel = new JLabel("Resource Panel:");
    private JLabel controlPanelLabel = new JLabel("Control Panel:");

    public GameUI() {

        Border blackline = BorderFactory.createLineBorder(Color.BLACK);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));

        boardPane.setBorder(blackline);

        resourcePane.setBorder(blackline);
        resourcePane.add(resourceBoxLabel);

        controlPanel.setBorder(blackline);
        controlPanel.add(controlPanelLabel);

        leftPane.setPreferredSize(new Dimension(800,800));
        leftPane.setLayout(new BoxLayout(leftPane, BoxLayout.PAGE_AXIS));
        leftPane.setBorder(blackline);
        leftPane.add(boardPane);
        leftPane.add(resourcePane);

        rightPane.setPreferredSize(new Dimension(400, 800));
        rightPane.setLayout(new BoxLayout(rightPane, BoxLayout.PAGE_AXIS));
        rightPane.setBorder(blackline);
        rightPane.add(controlPanel);

        this.add(leftPane);
        this.add(rightPane);
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
