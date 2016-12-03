import java.util.ArrayList;
import java.util.List;


public class Game {

    public interface GameListener {};

    private GameListener gameListener;

    public void setGameListener(GameListener gameListener) {
        this.gameListener = gameListener;
    }

    public interface UpdateStateListener {
        void updateState(GameState newState);
    }

    private final List<UpdateStateListener> updateStateListenersList = new ArrayList();

    public void registerUpdateStateListener(UpdateStateListener updateStateListener) {
        updateStateListenersList.add(updateStateListener);
    }

    public void unregisterUpdateStateListener(UpdateStateListener updateStateListener) {
        updateStateListenersList.remove(updateStateListener);
    }

    public void updateState(GameState newState) {
        currentState = newState;

        for(UpdateStateListener listener : updateStateListenersList)
            listener.updateState(newState);
    }

    private final int numPlayers;
    private final Bank bank = new Bank();
    private final Board board = new Board();
    private final PlayerManager playerManager;
    private final GameUI gameUI;

    private GameState currentState;

    public Game(int numPlayers) {
        this.numPlayers = numPlayers;

        playerManager = new PlayerManager(numPlayers);
        gameUI = new GameUI(this);

        gameUI.setBoardPanelListener(new BoardPanel.BoardPanelListener() {
            public void onEdgeClick() {
                switch (currentState) {
                    case BUILD_ROAD:
                        board.buildRoad();
                        break;
                    case SETUP_BOARD_2:
                        board.buildRoad();
                        break;
                    case SETUP_BOARD_4:
                        board.buildRoad();
                        break;
                    case SETUP_BOARD_6:
                        board.buildRoad();
                        break;
                    case SETUP_BOARD_8:
                        board.buildRoad();
                        break;
                    case SETUP_BOARD_10:
                        board.buildRoad();
                        break;
                    case SETUP_BOARD_12:
                        board.buildRoad();
                        break;
                    default:
                        System.out.println("Cannot build road at this time - currentState = " + currentState);
                }
            }

            public void onCornerClick() {
                switch (currentState) {
                    case BUILD_SETTLEMENT:
                        board.buildSettlement();
                        break;
                    case BUILD_CITY:
                        board.buildCity();
                        break;
                    case SETUP_BOARD_1:
                        board.buildSettlement();
                        break;
                    case SETUP_BOARD_3:
                        board.buildSettlement();
                        break;
                    case SETUP_BOARD_5:
                        board.buildSettlement();
                        break;
                    case SETUP_BOARD_7:
                        board.buildSettlement();
                        break;
                    case SETUP_BOARD_9:
                        board.buildSettlement();
                        break;
                    case SETUP_BOARD_11:
                        board.buildSettlement();
                        break;
                    default:
                        System.out.println("Cannot build road at this time - currentState = " + currentState);
                }
            }

            public void onHexClick() {
                if(currentState == GameState.PLACE_ROBBER)
                    board.placeRobber();
            }
        });

        gameUI.setControlPanelListener(new ControlPanel.ControlPanelListener() {
            public void onBuyRoad() {}

            public void onBuySettlement() {}

            public void onBuyCity() {}

            public void onBuyDevCard() {}

            public void onPlayDevCard() {}

            public void onTradePlayers() {}

            public void onTradeBank() {}

            public void onStartTurn() {}

            public void onEndTurn() {}

            public void onExitGame() {}
        });

        // gameUI.setResourcePanel(new ResourcePanel.ResourcePanelListener() {});

        updateState(GameState.SETUP_BOARD_1);
    }

    public GameUI getGameUI() {
        return gameUI;
    }

}
