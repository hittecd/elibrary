import javax.naming.ldap.Control;
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
        gameState = newState;

        for(UpdateStateListener listener : updateStateListenersList)
            listener.updateState(newState);
    }

    private final int numPlayers;
    private final Bank bank = new Bank();
    private final Board board = new Board();
    private final PlayerManager playerManager;
    private final GameUI gameUI;
    private final GameUI.ControlPanelListener controlPanelListener = new GameUI.ControlPanelListener() {
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
    };
    private final GameUI.BoardPanelListener boardPanelListener = new GameUI.BoardPanelListener() {
        public void onCornerClick(int cornerId) {
            System.out.println("CORNER DETECTED IN GAME: " + cornerId);

            if(gameState == GameState.BUILD_SETTLEMENT) {
                board.buildSettlement(cornerId);
            }
            else if(gameState == GameState.BUILD_CITY) {
                board.buildCity(cornerId);
            }
        }

        public void onEdgeClick(int edgeId) {
            System.out.println("EDGE DETECTED IN GAME: " + edgeId);

            Player currentPlayer = playerManager.getCurrentPlayer();

            if(gameState == GameState.SETUP_BOARD) {

            }
            else if(gameState == GameState.BUILD_ROAD) {
                board.buildRoad(currentPlayer, edgeId);
            }
        }

        public void onHexClick(int hexId) {
            System.out.println("HEX DETECTED IN GAME: " + hexId);

            board.placeRobber(hexId);
        }
    };

    private GameState gameState;

    public Game(int numPlayers) {
        this.numPlayers = numPlayers;

        playerManager = new PlayerManager(numPlayers);

        List<Hex> hexList = board.getHexList();
        List<Edge> edgeList = board.getEdgeList();
        List<Corner> cornerList = board.getCornerList();

        gameUI = new GameUI(this, hexList, edgeList, cornerList);
        gameUI.setBoardPanelListener(boardPanelListener);
        gameUI.setControlPanelListener(controlPanelListener);

        updateState(GameState.SETUP_BOARD);
    }

    public GameUI getGameUI() {
        return gameUI;
    }

    private class SetupBoardManager {

        int turn = 0;

        private SetupBoardManager() {

        }

    }

}
