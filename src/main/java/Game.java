import javax.naming.ldap.Control;
import java.util.*;


public class Game {

    public interface GameListener {};

    public interface UpdateStateListener {
        void updateState(GameState newState);
    }

    private final List<UpdateStateListener> updateStateListenersList = new ArrayList();

    public void registerUpdateStateListener(UpdateStateListener updateStateListener) {
        updateStateListenersList.add(updateStateListener);
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
        public MoveResult onBuyRoad() {
            MoveResult result;

            if(gameState != GameState.TURN_STARTED)
                return new MoveResult(false, "You cannot buy a Road at this time");

            Map<ResourceType, Integer> roadCards = new HashMap();
            roadCards.put(ResourceType.BRICK, 1);
            roadCards.put(ResourceType.LUMBER, 1);
            roadCards.put(ResourceType.WHEAT, 0);
            roadCards.put(ResourceType.SHEEP, 0);
            roadCards.put(ResourceType.ORE, 0);

            Player p = playerManager.getCurrentPlayer();

            // validate player resources
            if(!p.spendResourceCards(roadCards)) {
                result = new MoveResult(false,
                        "Player does not have required resources for a Road:\n" +
                        "\t1 - LUMBER\n" +
                        "\t1 - BRICK\n"
                );
            }
            else {
                bank.allocateResourceCards(roadCards);
                result = new MoveResult(true, "");
                updateState(GameState.BUILD_ROAD);
            }

            return result;
        }

        public MoveResult onBuySettlement() {
            MoveResult result;

            if(gameState != GameState.TURN_STARTED)
                return new MoveResult(false, "You cannot buy a Settlement at this time");

            Map<ResourceType, Integer> settlementCards = new HashMap();
            settlementCards.put(ResourceType.SHEEP, 1);
            settlementCards.put(ResourceType.WHEAT, 1);
            settlementCards.put(ResourceType.BRICK, 1);
            settlementCards.put(ResourceType.LUMBER, 1);
            settlementCards.put(ResourceType.ORE, 0);

            Player p = playerManager.getCurrentPlayer();

            // validate player resources
            if(!p.spendResourceCards(settlementCards)) {
                result = new MoveResult(false,
                        "Player does not have required resources for a Settlement:\n" +
                                "\t1 - WHEAT\n" +
                                "\t1 - SHEEP\n" +
                                "\t1 - LUMBER\n" +
                                "\t1 - BRICK\n"
                );
            }
            else {
                bank.allocateResourceCards(settlementCards);
                result = new MoveResult(true, "");
                updateState(GameState.BUILD_SETTLEMENT);

            }

            return result;
        }

        public MoveResult onBuyCity() {
            MoveResult result;

            if(gameState != GameState.TURN_STARTED)
                return new MoveResult(false, "You cannot buy a City at this time");

            Map<ResourceType, Integer> cityCards = new HashMap();
            cityCards.put(ResourceType.ORE, 3);
            cityCards.put(ResourceType.WHEAT, 2);
            cityCards.put(ResourceType.SHEEP, 0);
            cityCards.put(ResourceType.LUMBER, 0);
            cityCards.put(ResourceType.BRICK, 0);

            Player p = playerManager.getCurrentPlayer();

            // validate player resources
            if(!p.spendResourceCards(cityCards)) {
                result = new MoveResult(false,
                        "Player does not have required resources for a City:\n" +
                                "\t3 - ORE\n" +
                                "\t2 - WHEAT\n"
                );
            }
            else {
                bank.allocateResourceCards(cityCards);
                result = new MoveResult(true, "");
                updateState(GameState.BUILD_CITY);

            }

            return result;
        }

        public MoveResult onBuyDevCard() {
            return new MoveResult(false, "NOT IMPLEMENTED");
        }

        public MoveResult onPlayDevCard() {
            return new MoveResult(false, "NOT IMPLEMENTED");
        }

        public MoveResult onTradePlayers() {
            return new MoveResult(false, "NOT IMPLEMENTED");
        }

        public MoveResult onTradeBank() {
            return new MoveResult(false, "NOT IMPLEMENTED");
        }

        public MoveResult onStartTurn() {
            MoveResult result;

            if(gameState != GameState.START_TURN) {
                result = new MoveResult(false, "Cannot Start Turn at this time");
            }
            else {
                rollVal = rollDice();

                List<Hex> rolledHexes = board.getHexesByRollValue(rollVal);
                Map<Player, Map<ResourceType, Integer>> playerToResourceMap = playerManager.resolveResources(rolledHexes);

                for (Map<ResourceType, Integer> resourceMap : playerToResourceMap.values())
                    bank.allocateResourceCards(resourceMap);

                updateState(GameState.TURN_STARTED);

                result = new MoveResult(true, "A " + rollVal + " was rolled!");
            }

            return result;
        }

        public MoveResult onEndTurn() {
            MoveResult result;

            if(gameState != GameState.TURN_STARTED)
                result = new MoveResult(false, "Cannot End Turn at this time");
            else {
                playerManager.updateCurrentPlayer();

                updateState(GameState.START_TURN);

                result = new MoveResult(true, "");
            }

            return result;
        }

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
                result = board.buildSettlement(currentPlayer, cornerId, false);
                if(result.isSuccess()) {
                    updateState(GameState.TURN_STARTED);
                    currentPlayer.addVictoryPointSettlement();
                    gameUI.setControlPanel(currentPlayer.getVictoryPoints());
                    if(currentPlayer.getVictoryPoints() >= 5){
                        updateState(GameState.GAME_WON);
                        result = new MoveResult(false, "Player " + currentPlayer.getPlayerId() + " Win !");

                    }
                }

            }
            else if(gameState == GameState.BUILD_CITY) {
                result = board.buildCity(currentPlayer, cornerId);
                if(result.isSuccess()) {
                    updateState(GameState.TURN_STARTED);
                    currentPlayer.addVictoryPointCity();
                    gameUI.setControlPanel(currentPlayer.getVictoryPoints());
                    if(currentPlayer.getVictoryPoints() >= 5){
                        updateState(GameState.GAME_WON);
                        result = new MoveResult(false, "Player " + currentPlayer.getPlayerId() + " Win !");
                    }
                }
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
                if(result.isSuccess())
                    updateState(GameState.TURN_STARTED);
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

        public List<Hex> onUpdateHexList() {
            return board.getHexList();
        }

        public List<Edge> onUpdateEdgeList() {
            return board.getEdgeList();
        }

        public List<Corner> onUpdateCornerList() {
            return board.getCornerList();
        }
    };
    private final GameUI.ResourcePanelListener resourcePanelLister = new GameUI.ResourcePanelListener() {
        public Map<ResourceType, Integer> onUpdateResourcePanel() {
            Player currentPlayer = playerManager.getCurrentPlayer();
            return currentPlayer.getResourceCards();
        }
    };

    private GameState gameState;
    private int rollVal;

    public Game(int numPlayers) {
        this.numPlayers = numPlayers;
        setupBoardManager = new SetupBoardManager();

        playerManager = new PlayerManager(numPlayers);

        gameUI = new GameUI(this);
        gameUI.setBoardPanelListener(boardPanelListener);
        gameUI.setControlPanelListener(controlPanelListener);
        gameUI.setResourcePanelListener(resourcePanelLister);

        updateState(GameState.SETUP_BOARD);
    }

    public GameUI getGameUI() {
        return gameUI;
    }

    private int rollDice() {
        Random r = new Random();
        int low = 2;
        int high = 12;
        return (r.nextInt(high-low) + low);
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
            Player player = playerManager.getCurrentPlayer();

            if(stage.gameState != GameState.BUILD_SETTLEMENT)
                result = new MoveResult(false, "Select Edge to build Road.");
            else
                result = board.buildSettlement(player, cornerId, true);

            if(result.isSuccess())
                setupStageIndex++;

            if(setupStageIndex == (numPlayers*4))
                updateState(GameState.START_TURN);
            else {
                SetupStage nextStage = setupStages.get(setupStageIndex);
                playerManager.setPlayerById(nextStage.playerId);
                updateState(GameState.SETUP_BOARD);
            }

            return result;
        }

        private MoveResult setupEdge(int edgeId) {
            MoveResult result;
            SetupStage stage = setupStages.get(setupStageIndex);
            Player player = playerManager.getCurrentPlayer();

            if(stage.gameState != GameState.BUILD_ROAD)
                result = new MoveResult(false, "Select Corner to build Settlement");
            else
                result = board.buildRoad(player, edgeId);

            if(result.isSuccess())
                setupStageIndex++;

            if(setupStageIndex == (numPlayers*4))
                updateState(GameState.START_TURN);
            else {
                SetupStage nextStage = setupStages.get(setupStageIndex);
                playerManager.setPlayerById(nextStage.playerId);
                updateState(GameState.SETUP_BOARD);
            }

            return result;
        }
    }
}
