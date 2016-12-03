

public class ControlPanel {

    public interface ControlPanelListener {
        void onBuyRoad();
        void onBuySettlement();
        void onBuyCity();
        void onBuyDevCard();

        void onPlayDevCard();

        void onTradePlayers();
        void onTradeBank();

        void onStartTurn();
        void onEndTurn();
        void onExitGame();
    };

    private ControlPanelListener controlPanelListener;

    public void setControlPanelListener(ControlPanelListener listener) {
        this.controlPanelListener= listener;
    }

    private GameState currentState;

    public ControlPanel(Game game) {
        game.registerUpdateStateListener(new Game.UpdateStateListener() {
            public void updateState(GameState newState) {

            }
        });
    }

}
