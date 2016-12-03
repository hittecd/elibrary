import javax.swing.*;

public class GameUI extends JPanel{

    private final BoardPanel boardPanel;
    private final ResourcePanel resourcePanel;
    private final ControlPanel controlPanel;

    public void setBoardPanelListener(BoardPanel.BoardPanelListener listener) {
        boardPanel.setBoardPanelListener(listener);
    }

    public void setResourcePanel(ResourcePanel.ResourcePanelListener listener) {
        resourcePanel.setResourcePanelListener(listener);
    }

    public void setControlPanelListener(ControlPanel.ControlPanelListener listener) {
        controlPanel.setControlPanelListener(listener);
    }

    public GameUI(Game game) {
        boardPanel = new BoardPanel(game);
        resourcePanel = new ResourcePanel(game);
        controlPanel = new ControlPanel(game);

        // setup gameUI layout
    }

}
