

public class BoardPanel {

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

    public BoardPanel(Game game) {
        game.registerUpdateStateListener(new Game.UpdateStateListener() {
            public void updateState(GameState newState) {

            }
        });
    }

}
