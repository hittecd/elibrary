import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Ellipse2D;
import java.util.*;
import java.util.List;
import javax.swing.*;

public class GameUI extends JPanel {

    private final int WIDTH = 1220;
    private final int HEIGHT = 810;

    private final JPanel leftPane = new JPanel();
    private final JPanel rightPane = new JPanel();


    private final ResourcePanel resourcePanel = new ResourcePanel();
    private final DevCardPanel devCardPanel = new DevCardPanel();
    private final ControlPanel controlPanel = new ControlPanel();
    private final BoardPanel boardPanel = new BoardPanel();

    private final JOptionPane notificationPane = new JOptionPane();

    public void setControlPanel(Integer vp){
        controlPanel.victoryPointLabel.setText("Victory Points: " + vp);
    }

    /*
    private final Game.UpdateStateListener updateStateListener = new Game.UpdateStateListener() {
        public void updateState(GameState newState) {
            gameState = newState;
        }
    };
    */

    private BoardPanelListener boardPanelListener;
    private ControlPanelListener controlPanelListener;
    private ResourcePanelListener resourcePanelListener;
    private DevCardPanelListener devCardPanelListener;

    //private GameState gameState;

    public GameUI(Game game) {
        //game.registerUpdateStateListener(updateStateListener);

        setPreferredSize(new Dimension(WIDTH, HEIGHT));

        game.registerUpdateStateListener(boardPanel.getUpdateStateListener());
        game.registerUpdateStateListener(resourcePanel.getUpdateStateListener());
        game.registerUpdateStateListener(controlPanel.getUpdateStateListener());
        game.registerUpdateStateListener(devCardPanel.getUpdateStateListener());

        leftPane.setLayout(new BoxLayout(leftPane, BoxLayout.PAGE_AXIS));
        leftPane.add(boardPanel);

        rightPane.setLayout(new BoxLayout(rightPane, BoxLayout.PAGE_AXIS));
        rightPane.add(controlPanel);
        rightPane.add(resourcePanel);
        rightPane.add(devCardPanel);

        this.add(leftPane);
        this.add(rightPane);
    }

    public void setBoardPanelListener(BoardPanelListener listener) {
        boardPanelListener = listener;
    }

    public void setControlPanelListener(ControlPanelListener listener) {
        controlPanelListener = listener;
    }

    public void setResourcePanelListener(ResourcePanelListener listener) {
        resourcePanelListener = listener;
    }

    public void setDevCardPanelListener(DevCardPanelListener listener){ devCardPanelListener = listener;}

    private class BoardPanel extends JPanel implements MouseListener {
        private final int WIDTH = 990;
        private final int HEIGHT = 790;

        private final Map<ResourceType, Color> resourceTypeColorMap = new HashMap<ResourceType, Color>();
        private final Map<Integer, Color> playerColorMap = new HashMap<Integer, Color>();

        private List<Hex> hexList = null;
        private List<Edge> edgeList = null;
        private List<Corner> cornerList = null;

        // This array holds all 54 X values for the corners.
        private final int[] unscaledXPoints = { -25, 25,
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
        private final int[] unscaledYPoints = { -220, -220,
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

        private final int[]unscaledXCenters = { 0,
                -75, 75,
                -150, 0, 150,
                -75, 75,
                -150, 0, 150,
                -75, 75,
                -150, 0, 150,
                -75, 75,
                0};
        private final int[]unscaledYCenters = { -176,
                -132, -132,
                -88, -88, -88,
                -44, -44,
                0, 0, 0,
                44, 44,
                88, 88, 88,
                132, 132,
                176};


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

        private int[] XPoints;
        private int[] YPoints;
        private int[] XCenters;
        private int[] YCenters;

        private Font font1 = new Font("Arial", Font.BOLD, 25);
        private Font font2 = new Font("Arial", Font.BOLD, 18);
        FontMetrics metrics;

        public final Game.UpdateStateListener updateStateListener = new Game.UpdateStateListener() {
            public void updateState(GameState newState) {
                hexList = boardPanelListener.onUpdateHexList();
                edgeList = boardPanelListener.onUpdateEdgeList();
                cornerList = boardPanelListener.onUpdateCornerList();


            }
        };

        private int[] Scale(int[] XY, double newRadius){
            double[] scaler = new double[XY.length];
            for(int i = 0; i < XY.length; i++){
                scaler[i] = XY[i];
            }
            double scaleFactor = newRadius/50;
            int[] scaledXY = new int[XY.length];
            for(int i = 0; i < scaler.length; i++){
                scaledXY[i] = (int)(scaler[i] * scaleFactor);
            }
            return scaledXY;
        }

        public BoardPanel() {
            addMouseListener(this);
            setPreferredSize(new Dimension(WIDTH, HEIGHT));
            setBorder(BorderFactory.createLineBorder(Color.BLACK));;

            initColorMap();
            initPlayerColorMap();
            XPoints = Scale(unscaledXPoints, 85);
            YPoints = Scale(unscaledYPoints, 85);
            XCenters = Scale(unscaledXCenters, 85);
            YCenters = Scale(unscaledYCenters, 85);
        }

        private void initColorMap() {
            resourceTypeColorMap.put(ResourceType.WHEAT, new Color(0xC48821));
            resourceTypeColorMap.put(ResourceType.SHEEP, new Color(0xFFFFFF));
            resourceTypeColorMap.put(ResourceType.LUMBER, new Color(0x0A6805));
            resourceTypeColorMap.put(ResourceType.BRICK, new Color(0xED4917));
            resourceTypeColorMap.put(ResourceType.ORE, new Color(0x606060));
            resourceTypeColorMap.put(ResourceType.DESERT, new Color(0xcFFDD88));
        }

        private void initPlayerColorMap() {
            playerColorMap.put(0, new Color(0xFF0000));
            playerColorMap.put(1, new Color(0x00FF00));
            playerColorMap.put(2, new Color(0x0000FF));
            playerColorMap.put(3, new Color(0xFFFF00));
        }

        public Game.UpdateStateListener getUpdateStateListener() {
            return updateStateListener;
        }

        public void paintComponent(Graphics g) {
            if(hexList == null || edgeList == null || cornerList == null)
                return;

            Graphics2D g2d = (Graphics2D) g;
            Point origin = new Point(WIDTH / 2, HEIGHT / 2);

            g2d.setStroke(new BasicStroke(4.0f, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER));
            g2d.setFont(font1);
            metrics = g.getFontMetrics();

            drawCircle(g2d, origin, 390, true, true, 0x4488FF, 0);

            // Setup Hexagon Array
            for(int i = 0; i < 19; i++){
                int[] tempXPts = new int[6];
                int[] tempYPts = new int[6];
                for(int j = 0; j < 6; j++){
                    tempXPts[j] = XPoints[HexPointsMap[i][j]] + origin.x;
                    tempYPts[j] = YPoints[HexPointsMap[i][j]] + origin.y;
                }
                Hexes2D[i] = new Polygon(tempXPts, tempYPts, 6);
            }

            // Setup the Edge Array
            for(int i = 0; i < 72; i++){
                int[] tempXpts = new int[4];
                int[] tempYpts = new int[4];
                if(EdgePointMap[i][0] == 0){
                    tempXpts[0] = XPoints[EdgePointMap[i][1]] + origin.x;
                    tempYpts[0] = YPoints[EdgePointMap[i][1]] + origin.y+6;
                    tempXpts[1] = XPoints[EdgePointMap[i][1]] + origin.x;
                    tempYpts[1] = YPoints[EdgePointMap[i][1]] + origin.y-6;
                    tempXpts[2] = XPoints[EdgePointMap[i][2]] + origin.x;
                    tempYpts[2] = YPoints[EdgePointMap[i][2]] + origin.y-6;
                    tempXpts[3] = XPoints[EdgePointMap[i][2]] + origin.x;
                    tempYpts[3] = YPoints[EdgePointMap[i][2]] + origin.y+6;
                }
                else if(EdgePointMap[i][0] == 1){
                    tempXpts[0] = XPoints[EdgePointMap[i][1]] + origin.x - 3;
                    tempYpts[0] = YPoints[EdgePointMap[i][1]] + origin.y - 5;
                    tempXpts[1] = XPoints[EdgePointMap[i][1]] + origin.x + 3;
                    tempYpts[1] = YPoints[EdgePointMap[i][1]] + origin.y + 5;
                    tempXpts[2] = XPoints[EdgePointMap[i][2]] + origin.x + 3;
                    tempYpts[2] = YPoints[EdgePointMap[i][2]] + origin.y + 5;
                    tempXpts[3] = XPoints[EdgePointMap[i][2]] + origin.x - 3;
                    tempYpts[3] = YPoints[EdgePointMap[i][2]] + origin.y - 5;
                }
                else {
                    tempXpts[0] = XPoints[EdgePointMap[i][1]] + origin.x + 3;
                    tempYpts[0] = YPoints[EdgePointMap[i][1]] + origin.y - 5;
                    tempXpts[1] = XPoints[EdgePointMap[i][1]] + origin.x - 3;
                    tempYpts[1] = YPoints[EdgePointMap[i][1]] + origin.y + 5;
                    tempXpts[2] = XPoints[EdgePointMap[i][2]] + origin.x - 3;
                    tempYpts[2] = YPoints[EdgePointMap[i][2]] + origin.y + 5;
                    tempXpts[3] = XPoints[EdgePointMap[i][2]] + origin.x + 3;
                    tempYpts[3] = YPoints[EdgePointMap[i][2]] + origin.y - 5;
                }
                Edges2D[i] = new Polygon(tempXpts,tempYpts,4);
            }

            // Setup the Corner Array
            for(int i = 0; i < 54; i++){
                Corner2D[i] = new Ellipse2D.Double((XPoints[i]-10+origin.x), (YPoints[i]-10+origin.y), 20, 20);
            }

            // draw hexes
            Color defaultColor = new Color(0x000000);
            for(int i = 0; i < 19; i++){
                Hex hex = hexList.get(i);
                g2d.setColor(resourceTypeColorMap.get(hex.getHexResourceType()));
                g2d.fill(Hexes2D[i]);
                g2d.setColor(defaultColor);
                int val = hex.getHexValue();
                String text = String.format("%s", val);
                if(val == 0){
                    text = "R";
                    g2d.setColor(new Color(0x008080));
                    g2d.fillOval(XCenters[i]+origin.x - 15, YCenters[i]+origin.y - 15, 30, 30);
                    g2d.setColor(defaultColor);
                }
                int w = metrics.stringWidth(text);
                int h = metrics.getHeight();
                g2d.drawString(text, XCenters[i]+origin.x-w/2, YCenters[i]+origin.y+h/2-5);
            }

            Color color;
            Integer playerId;

            // draw edges
            Edge e;
            defaultColor = new Color(0x000000);
            for(int i = 0; i < 72; i++){
                e = edgeList.get(i);
                playerId = e.getPlayerId();

                if(e.getPlayerId() >= 0)
                    color = playerColorMap.get(playerId);
                else
                    color = defaultColor;

                g2d.setColor(color);
                g2d.fill(Edges2D[i]);
            }

            // draw corners
            g2d.setFont(font2);
            metrics = g2d.getFontMetrics();
            Corner c;
            defaultColor = new Color(0xFFFFFF);
            for(int i = 0; i < 54; i++){
                c = cornerList.get(i);
                playerId = c.getPlayerId();

                if(playerId >= 0) {
                    color = playerColorMap.get(playerId);
                }
                else
                    color = defaultColor;

                g2d.setColor(color);
                g2d.fill(Corner2D[i]);

                if(c.hasCity()) {
                    g2d.setColor(new Color(0x000000));
                    String text = "C";
                    int w = metrics.stringWidth(text);
                    int h = metrics.getHeight();
                    g2d.drawString(text, XPoints[i] + origin.x - w / 2, YPoints[i] + origin.y + h/3);
                }
            }

            Color blackColor = new Color(0x000000);
            int i = 0;
            for (ResourceType ColorKey: resourceTypeColorMap.keySet()){
                g2d.setColor(resourceTypeColorMap.get(ColorKey));
                g2d.fillRect(5, (30*i) + 5, 20, 20);
                g2d.setColor(blackColor);
                String text = ColorKey.name().toLowerCase();
                text = text.substring(0,1).toUpperCase() + text.substring(1);
                int w = metrics.stringWidth(text);
                int h = metrics.getHeight();
                g2d.drawString(text,30,(30*i) + 5 + h-6);
                i += 1;
            }
            for(i =0; i < 4; i++){
                String text = "Player " + i;
                int w = metrics.stringWidth(text);
                int h = metrics.getHeight();
                g2d.setColor(playerColorMap.get(i));
                g2d.fillRect(WIDTH - w - 30, 5 + (30*i), 20,20);
                g2d.setColor(blackColor);
                g2d.drawString(text,WIDTH - w - 5,(30*i) + 5 + h-6);
            }

            String[] buildKey = {"Development Card", "City","Settlement","Road"};
            int h = metrics.getHeight();
            g2d.setColor(blackColor);
            for(i = 0; i < buildKey.length; i++){
                String text = buildKey[i];
                g2d.drawString(text, 5, HEIGHT - 5 - (25*(i+1)) - h/2 + 5 - (h*i));
            }
            g2d.setColor(resourceTypeColorMap.get(ResourceType.ORE));
            for(i = 0; i < 3; i++) {
                g2d.fillRect((5 * (i + 1)) + (20 * i), HEIGHT - 55 - h, 20, 20);
            }
            g2d.setColor(resourceTypeColorMap.get(ResourceType.WHEAT));
            for(i = 3; i < 5; i++){
                g2d.fillRect((5 * (i + 1)) + (20 * i), HEIGHT - 55 - h, 20, 20);
            }
            g2d.setColor(resourceTypeColorMap.get(ResourceType.WHEAT));
            g2d.fillRect(5, HEIGHT - 80 - h*2, 20, 20);
            g2d.setColor(resourceTypeColorMap.get(ResourceType.SHEEP));
            g2d.fillRect(30, HEIGHT - 80 - h*2, 20, 20);
            g2d.setColor(resourceTypeColorMap.get(ResourceType.LUMBER));
            g2d.fillRect(55, HEIGHT - 80 - h*2, 20, 20);
            g2d.setColor(resourceTypeColorMap.get(ResourceType.BRICK));
            g2d.fillRect(80, HEIGHT - 80 - h*2, 20, 20);

            g2d.setColor(resourceTypeColorMap.get(ResourceType.LUMBER));
            g2d.fillRect(5, HEIGHT - 105 - h*3, 20, 20);
            g2d.setColor(resourceTypeColorMap.get(ResourceType.BRICK));
            g2d.fillRect(30, HEIGHT - 105 - h*3, 20, 20);

            g2d.setColor(resourceTypeColorMap.get(ResourceType.ORE));
            g2d.fillRect(55, HEIGHT - 25, 20, 20);
            g2d.setColor(resourceTypeColorMap.get(ResourceType.WHEAT));
            g2d.fillRect(5, HEIGHT - 25, 20, 20);
            g2d.setColor(resourceTypeColorMap.get(ResourceType.SHEEP));
            g2d.fillRect(30, HEIGHT - 25, 20, 20);



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
            MoveResult result;

            for(int i = 0; i < 54; i++){
                if(Corner2D[i].contains(e.getX(), e.getY())){
                    System.out.println("Mouse Clicked on Corner #" + i);
                    result = boardPanelListener.onCornerClick(i);

                    if(result != null && result.isSuccess()) {
                        this.revalidate();
                        this.repaint();
                    }
                    else
                        notificationPane.showMessageDialog(null, result.getMessage());

                    return;
                }
            }

            for(int i = 0; i < 72; i++){
                if(Edges2D[i].contains(e.getX(),e.getY())){
                    System.out.println("Mouse Clicked on Edge #" + i);
                    result = boardPanelListener.onEdgeClick(i);

                    if(result != null && result.isSuccess()) {
                        this.revalidate();
                        this.repaint();
                    }
                    else
                        notificationPane.showMessageDialog(null, result.getMessage());

                    return;
                }
            }

            for(int i = 0; i < 19; i++){
                if(Hexes2D[i].contains(e.getX(), e.getY())) {
                    System.out.println("Mouse Clicked on Hex #" + i);
                    result = boardPanelListener.onHexClick(i);

                    if(result != null && result.isSuccess()) {
                        this.revalidate();
                        this.repaint();
                    }
                    else
                        notificationPane.showMessageDialog(null, result.getMessage());

                    return;
                }
            }
        }

        public void mousePressed(MouseEvent e) {}

        public void mouseReleased(MouseEvent e) {}

        public void mouseEntered(MouseEvent e) {}

        public void mouseExited(MouseEvent e) {}
    }

    public interface BoardPanelListener {
        MoveResult onCornerClick(int cornerId);

        MoveResult onEdgeClick(int edgeId);

        MoveResult onHexClick(int hexId);

        List<Hex> onUpdateHexList();

        List<Edge> onUpdateEdgeList();

        List<Corner> onUpdateCornerList();
    }

    private class ControlPanel extends JPanel {

        private final JLabel controlPanelLabel = new JLabel("Control Panel");

        private final JLabel gameStateLabel = new JLabel("Game State: ");
        private final JLabel currentPlayerLabel = new JLabel("Current Player: ");
        private final JLabel victoryPointLabel = new JLabel ("Victory Points: ");

        private final JButton buyRoadBtn = new JButton("Buy Road");
        private final JButton buySettlementBtn = new JButton("Buy Settlement");
        private final JButton buyCityBtn = new JButton("Buy City");
        private final JButton buyDevelopmentCardBtn = new JButton("Buy Development Card");
        private final JButton cancelBuyBtn = new JButton("Cancel purchase");
        private final JButton playDevelopmentCardBtn = new JButton("Play Development Card");

        private final JButton startTurnBtn = new JButton("Start Turn");
        private final JButton endTurnBtn = new JButton("End Turn");
        private final JButton exitGameBtn = new JButton("Exit Game");


        private final Game.UpdateStateListener updateStateListener = new Game.UpdateStateListener() {
            public void updateState(GameState newState) {
                Player currentPlayer = controlPanelListener.onGetNextPlayer();
                currentPlayerLabel.setText("Current Player: " + currentPlayer.getPlayerId());

                victoryPointLabel.setText("Victory Points: " + currentPlayer.getVictoryPoints());
                gameStateLabel.setText("Game State: " + newState);
            }
        };

        private final ActionListener controlPanelActionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                MoveResult result;

                if(e.getSource() == buyRoadBtn) {
                    result = controlPanelListener.onBuyRoad();
                }
                else if(e.getSource() == buySettlementBtn) {
                    result = controlPanelListener.onBuySettlement();
                }
                else if(e.getSource() == buyCityBtn) {
                    result = controlPanelListener.onBuyCity();
                }
                else if(e.getSource() == buyDevelopmentCardBtn) {
                    result = controlPanelListener.onBuyDevCard();
                }
                else if(e.getSource() == playDevelopmentCardBtn) {
                    result = controlPanelListener.onPlayDevCard();
                }
                else if(e.getSource() == startTurnBtn) {
                    result = controlPanelListener.onStartTurn();
                }
                else if(e.getSource() == endTurnBtn) {
                    result = controlPanelListener.onEndTurn();
                }
                else if(e.getSource() == exitGameBtn) {
                    result = controlPanelListener.onExitGame();
                    System.exit(0);
                }
                else if(e.getSource() == cancelBuyBtn) {
                    result = controlPanelListener.onCancelBuy();
                }
                else
                    result = new MoveResult(false, "Could not handle ActionEvent");

                if (result.getMessage() != null && !result.getMessage().isEmpty())
                    notificationPane.showMessageDialog(null, result.getMessage());
            }
        };

        public ControlPanel() {
            setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
            setBorder(BorderFactory.createLineBorder(Color.BLACK));

            buyRoadBtn.addActionListener(controlPanelActionListener);
            buySettlementBtn.addActionListener(controlPanelActionListener);
            buyCityBtn.addActionListener(controlPanelActionListener);
            buyDevelopmentCardBtn.addActionListener(controlPanelActionListener);
            playDevelopmentCardBtn.addActionListener(controlPanelActionListener);
            startTurnBtn.addActionListener(controlPanelActionListener);
            endTurnBtn.addActionListener(controlPanelActionListener);
            exitGameBtn.addActionListener(controlPanelActionListener);
            cancelBuyBtn.addActionListener(controlPanelActionListener);

            this.add(controlPanelLabel);
            this.add(gameStateLabel);
            this.add(currentPlayerLabel);
            this.add(victoryPointLabel);
            this.add(buyRoadBtn);
            this.add(buySettlementBtn);
            this.add(buyCityBtn);
            this.add(buyDevelopmentCardBtn);
            this.add(cancelBuyBtn);
            this.add(playDevelopmentCardBtn);
            this.add(startTurnBtn);
            this.add(endTurnBtn);
            this.add(exitGameBtn);

        }

        public Game.UpdateStateListener getUpdateStateListener() {
            return updateStateListener;
        }
    }

    public interface ControlPanelListener {
        MoveResult onBuyRoad();

        MoveResult onBuySettlement();

        MoveResult onBuyCity();

        MoveResult onBuyDevCard();

        MoveResult onPlayDevCard();

        MoveResult onTradePlayers();

        MoveResult onTradeBank();

        MoveResult onStartTurn();

        MoveResult onEndTurn();

        MoveResult onExitGame();

        MoveResult onCancelBuy();

        Player onGetNextPlayer();


    }

    private class ResourcePanel extends JPanel {

        private final JLabel resourceBoxLabel = new JLabel("Resource Panel");
        private final JLabel wheatCountLabel = new JLabel();
        private final JLabel sheepCountLabel = new JLabel();
        private final JLabel lumberCountLabel = new JLabel();
        private final JLabel oreCountLabel = new JLabel();
        private final JLabel brickCountLabel = new JLabel();

        public final Game.UpdateStateListener updateStateListener = new Game.UpdateStateListener() {
            public void updateState(GameState newState) {
                Map<ResourceType, Integer> resourceMap = resourcePanelListener.onUpdateResourcePanel();

                wheatCountLabel.setText("Wheat: " + resourceMap.get(ResourceType.WHEAT));
                sheepCountLabel.setText("Sheep: " + resourceMap.get(ResourceType.SHEEP));
                lumberCountLabel.setText("Lumber: " + resourceMap.get(ResourceType.LUMBER));
                brickCountLabel.setText("Brick: " + resourceMap.get(ResourceType.BRICK));
                oreCountLabel.setText("Ore: " + resourceMap.get(ResourceType.ORE));


            }
        };

        public ResourcePanel() {
            setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
            setBorder(BorderFactory.createLineBorder(Color.BLACK));

            this.add(resourceBoxLabel);
            this.add(wheatCountLabel);
            this.add(sheepCountLabel);
            this.add(lumberCountLabel);
            this.add(oreCountLabel);
            this.add(brickCountLabel);
        }

        public Game.UpdateStateListener getUpdateStateListener() {
            return this.updateStateListener;
        }
    }

    public interface ResourcePanelListener {
        Map<ResourceType, Integer> onUpdateResourcePanel();
    }


    private class DevCardPanel extends JPanel {

        private final JLabel devCardBoxLabel = new JLabel("Resource Panel");
        private final JLabel knightLabel = new JLabel();
        private final JLabel yearOfPlentyLabel = new JLabel();
        private final JLabel monopolyLabel = new JLabel();
        private final JLabel roadBuilderLabel = new JLabel();
        private final JLabel victoryPointLabel = new JLabel();



        public final Game.UpdateStateListener updateStateListener = new Game.UpdateStateListener() {
            public void updateState(GameState newState) {
                Map<DevelopmentCard, Integer> devCardMap = devCardPanelListener.onUpdateDevCardPanel();

                knightLabel.setText("KNIGHT: " + devCardMap.get(DevelopmentCard.KNIGHT));
                yearOfPlentyLabel.setText("YEAR OF PLENTY: " + devCardMap.get(DevelopmentCard.YEAR_OF_PLENTY));
                monopolyLabel.setText("MONOPOLY: " + devCardMap.get(DevelopmentCard.MONOPOLY));
                roadBuilderLabel.setText("ROAD BUILDER: " + devCardMap.get(DevelopmentCard.ROAD_BUILDER));
                victoryPointLabel.setText("VICTORY POINT: " + devCardMap.get(DevelopmentCard.VICTORY_POINT));

            }
        };

        public DevCardPanel() {
            setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
            setBorder(BorderFactory.createLineBorder(Color.BLACK));

            this.add(devCardBoxLabel);
            this.add(knightLabel);
            this.add(yearOfPlentyLabel);
            this.add(monopolyLabel);
            this.add(roadBuilderLabel);
            this.add(victoryPointLabel);
        }

        public Game.UpdateStateListener getUpdateStateListener() {
            return this.updateStateListener;
        }
    }

    public interface DevCardPanelListener {
        Map<DevelopmentCard, Integer> onUpdateDevCardPanel();
    }
}
