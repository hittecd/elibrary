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

public class ResourcePanel extends JPanel{

    private final int WIDTH = 790;
    private final int HEIGHT = 190;

    public interface ResourcePanelInterface {};

    public ResourcePanel() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
    }
}
