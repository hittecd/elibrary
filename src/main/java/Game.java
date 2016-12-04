import javax.naming.ldap.Control;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


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
    private final SetupBoardManager setupBoardManager;
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

        public Player onGetNextPlayer() {
            return playerManager.getCurrentPlayer();
        }
    };
    private final GameUI.BoardPanelListener boardPanelListener = new GameUI.BoardPanelListener() {
        public MoveResult onCornerClick(int cornerId) {
            System.out.println("CORNER DETECTED IN GAME: " + cornerId);

            MoveResult result;
            Player currentPlayer = playerManager.getCurrentPlayer();

            if(gameState == GameState.SETUP_BOARD) {
                result = setupBoardManager.setupCorner(cornerId);
            }
            else if(gameState == GameState.BUILD_SETTLEMENT) {
                result = board.buildSettlement(currentPlayer, cornerId);
            }
            else if(gameState == GameState.BUILD_CITY) {
                result = board.buildCity(currentPlayer, cornerId);
            }
            else
                result = new MoveResult(false, "Cannot build Settlement/City at this time.");

            return result;
        }

        public MoveResult onEdgeClick(int edgeId) {
            System.out.println("EDGE DETECTED IN GAME: " + edgeId);

            MoveResult result;
            Player currentPlayer = playerManager.getCurrentPlayer();

            if(gameState == GameState.SETUP_BOARD) {
                result = setupBoardManager.setupEdge(edgeId);
            }
            else if(gameState == GameState.BUILD_ROAD) {
                result = board.buildRoad(currentPlayer, edgeId);
            }
            else
                result = new MoveResult(false, "Cannot build Road at this time.");

            return result;
        }

        public MoveResult onHexClick(int hexId) {
            System.out.println("HEX DETECTED IN GAME: " + hexId);

            MoveResult result;
            Player currentPlayer = playerManager.getCurrentPlayer();

            if(gameState == GameState.PLACE_ROBBER)
                result = board.placeRobber(currentPlayer, hexId);
            else
                result = new MoveResult(false, "Cannot place Robber at this time");

            return result;
        }
    };
    private final GameUI.ResourcePanelListener resourcePanelLister = new GameUI.ResourcePanelListener() {
        public Map<ResourceType, Integer> onUpdateResourcePanel() {
            Player currentPlayer = playerManager.getCurrentPlayer();
            return currentPlayer.getResourceCards();
        }
    };

    private GameState gameState;

    public Game(int numPlayers) {
        this.numPlayers = numPlayers;
        setupBoardManager = new SetupBoardManager();

        playerManager = new PlayerManager(numPlayers);

        List<Hex> hexList = board.getHexList();
        List<Edge> edgeList = board.getEdgeList();
        List<Corner> cornerList = board.getCornerList();

        gameUI = new GameUI(this, hexList, edgeList, cornerList);
        gameUI.setBoardPanelListener(boardPanelListener);
        gameUI.setControlPanelListener(controlPanelListener);
        gameUI.setResourcePanelListener(resourcePanelLister);

        updateState(GameState.SETUP_BOARD);
    }

    public GameUI getGameUI() {
        return gameUI;
    }

    private class SetupBoardManager {
        int setupStageIndex = 0;
        private final List<SetupStage> setupStages = new ArrayList();

        private SetupBoardManager() {
            for(int i=0; i<numPlayers; i++) {
                setupStages.add(new SetupStage(i, GameState.BUILD_SETTLEMENT));
                setupStages.add(new SetupStage(i, GameState.BUILD_ROAD));
            }

            for(int i=(numPlayers-1); i>=0; i--) {
                setupStages.add(new SetupStage(i, GameState.BUILD_SETTLEMENT));
                setupStages.add(new SetupStage(i, GameState.BUILD_ROAD));
            }
        }

        private class SetupStage {
            Integer playerId;
            GameState gameState;

            private SetupStage(int playerId, GameState gameState) {
                this.playerId = playerId;
                this.gameState = gameState;
            }
        }

        private MoveResult setupCorner(int cornerId) {
            MoveResult result;
            SetupStage stage = setupStages.get(setupStageIndex);
            Player player = playerManager.getPlayerById(stage.playerId);

            if(stage.gameState != GameState.BUILD_SETTLEMENT)
                result = new MoveResult(false, "Select Edge to build Road.");
            else
                result = board.buildSettlement(player, cornerId);

            if(result.isSuccess())
                setupStageIndex++;

            if(setupStageIndex == (numPlayers*4))
                updateState(GameState.START_TURN);

            return result;
        }

        private MoveResult setupEdge(int edgeId) {
            MoveResult result;
            SetupStage stage = setupStages.get(setupStageIndex);
            Player player = playerManager.getPlayerById(stage.playerId);

            if(stage.gameState != GameState.BUILD_ROAD)
                result = new MoveResult(false, "Select Corner to build Settlement");
            else
                result = board.buildRoad(player, edgeId);

            if(result.isSuccess())
                setupStageIndex++;

            if(setupStageIndex == (numPlayers*4))
                updateState(GameState.START_TURN);

            return result;
        }
    }
}
