import java.util.*;


public class Game {

    public interface GameListener {
        void onExitGame();
    }

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

    private final GameListener gameListener;

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
                bank.deallocateResourceCards(settlementCards);
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
                bank.deallocateResourceCards(cityCards);
                result = new MoveResult(true, "");
                updateState(GameState.BUILD_CITY);

            }

            return result;
        }

        public MoveResult onBuyDevCard() {
            MoveResult result;

            if(gameState != GameState.TURN_STARTED)
                return new MoveResult(false, "You cannot buy development card at this time");

            Map<ResourceType, Integer> devCards = new HashMap();
            devCards.put(ResourceType.ORE, 1);
            devCards.put(ResourceType.SHEEP, 1);
            devCards.put(ResourceType.WHEAT, 1);
            devCards.put(ResourceType.LUMBER, 0);
            devCards.put(ResourceType.BRICK, 0);

            Player p = playerManager.getCurrentPlayer();

            Map<DevelopmentCard, Integer> devCardList = new HashMap();

            // validate player resources
            if(!p.spendResourceCards(devCards)) {
                result = new MoveResult(false,
                        "Player does not have required resources for a development card:\n" +
                                "\t1 - ORE\n" +
                                "\t1 - WHEAT\n" +
                                "\t1 - SHEEP\n"
                );
            }
            else {
                bank.deallocateResourceCards(devCards);

                DevelopmentCard dc = bank.getDevelopmentCard();
                devCardList.put(dc,1);
                p.addDevelopmentCard(devCardList);

                result = new MoveResult(true, "");
                updateState(GameState.TURN_STARTED);
            }

            return result;
        }

        public MoveResult onCancelBuy(){
            MoveResult result;

            if(gameState != GameState.BUILD_CITY && gameState != GameState.BUILD_ROAD && gameState != GameState.BUILD_SETTLEMENT) {
                return new MoveResult(false, "You cannot cancel any purchase so far");
            }
            else if(gameState == GameState.BUILD_CITY){
                Map<ResourceType, Integer> cityCards = new HashMap();
                cityCards.put(ResourceType.ORE, 3);
                cityCards.put(ResourceType.WHEAT, 2);
                cityCards.put(ResourceType.SHEEP, 0);
                cityCards.put(ResourceType.LUMBER, 0);
                cityCards.put(ResourceType.BRICK, 0);

                Player p = playerManager.getCurrentPlayer();

                // validate player resources
                p.addResourceCards(cityCards);

                bank.deallocateResourceCards(cityCards);
                result = new MoveResult(true, "");
                updateState(GameState.TURN_STARTED);
            }
            else if(gameState == GameState.BUILD_SETTLEMENT){
                Map<ResourceType, Integer> settlementCards = new HashMap();
                settlementCards.put(ResourceType.SHEEP, 1);
                settlementCards.put(ResourceType.WHEAT, 1);
                settlementCards.put(ResourceType.BRICK, 1);
                settlementCards.put(ResourceType.LUMBER, 1);
                settlementCards.put(ResourceType.ORE, 0);

                Player p = playerManager.getCurrentPlayer();

                // validate player resources
                p.addResourceCards(settlementCards);

                bank.deallocateResourceCards(settlementCards);
                result = new MoveResult(true, "");
                updateState(GameState.TURN_STARTED);
            }
            else if(gameState == GameState.BUILD_ROAD){
                Map<ResourceType, Integer> roadCards = new HashMap();
                roadCards.put(ResourceType.BRICK, 1);
                roadCards.put(ResourceType.LUMBER, 1);
                roadCards.put(ResourceType.WHEAT, 0);
                roadCards.put(ResourceType.SHEEP, 0);
                roadCards.put(ResourceType.ORE, 0);

                Player p = playerManager.getCurrentPlayer();

                // validate player resources
                p.addResourceCards(roadCards);

                bank.deallocateResourceCards(roadCards);
                result = new MoveResult(true, "");
                updateState(GameState.TURN_STARTED);
            }
            else
                result = new MoveResult(true, "");

            return result;
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

                if(rollVal != 7) {
                    Map<Player, Map<ResourceType, Integer>> playerToResourceMap = playerManager.resolveResources(rolledHexes);

                    for (Map<ResourceType, Integer> resourceMap : playerToResourceMap.values())
                        bank.allocateResourceCards(resourceMap);

                    updateState(GameState.TURN_STARTED);
                }
                else {
                    updateState(GameState.PLACE_ROBBER);
                }

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

        public void onExitGame() {
            gameListener.onExitGame();
        }

        public Player onGetNextPlayer() {
            return playerManager.getCurrentPlayer();
        }
    };

    private final GameUI.BoardPanelListener boardPanelListener = new GameUI.BoardPanelListener() {
        public MoveResult onCornerClick(int cornerId) {
            System.out.println("CORNER DETECTED IN GAME: " + cornerId);

            MoveResult result = new MoveResult();
            boolean success;

            Player currentPlayer = playerManager.getCurrentPlayer();

            if(gameState == GameState.BUILD_SETTLEMENT) {
                success = board.buildSettlement(currentPlayer, cornerId, false);

                result.setSuccess(success);

                if(!result.isSuccess()) {
                    result.setMessage("Failed to build Settlement. Check your placement and try again.");
                }
            }
            else if(gameState == GameState.BUILD_CITY) {
                success = board.buildCity(currentPlayer, cornerId);

                result.setSuccess(success);

                if(!result.isSuccess()) {
                    result.setMessage("Failed to build City. Check your placement and try again.");
                }
            }
            else if(gameState == GameState.SETUP_BOARD) {
                return setupBoardManager.setupCorner(cornerId);
            }
            else {
                result.setMessage("Cannot build Settlement/City at this time.");
            }

            if(result.isSuccess()) {
                currentPlayer.incrementVictoryPoints();

                if(currentPlayer.getVictoryPoints() >= 10) {
                    updateState(GameState.GAME_WON);
                    gameUI.setControlPanel(currentPlayer.getVictoryPoints());
                }
                else
                    updateState(GameState.TURN_STARTED);
            }

            return result;
        }

        public MoveResult onEdgeClick(int edgeId) {
            System.out.println("EDGE DETECTED IN GAME: " + edgeId);

            MoveResult result = new MoveResult();
            boolean success;

            Player currentPlayer = playerManager.getCurrentPlayer();

            if(gameState == GameState.BUILD_ROAD) {
                success = board.buildRoad(currentPlayer, edgeId);

                result.setSuccess(success);

                if(result.isSuccess())
                    updateState(GameState.TURN_STARTED);
            }
            else if(gameState == GameState.SETUP_BOARD) {
                return setupBoardManager.setupEdge(edgeId);
            }
            else
                result.setMessage("Cannot build Road at this time.");

            return result;
        }

        public MoveResult onHexClick(int hexId) {
            System.out.println("HEX DETECTED IN GAME: " + hexId);

            MoveResult result = new MoveResult();

            if(gameState == GameState.PLACE_ROBBER) {
                boolean success = board.moveRobber(hexId);

                if(success) {
                    result.setSuccess(true);

                    updateState(GameState.CHOOSE_VICTIM);
                }
                else
                    result.setMessage("Cannot place Robber here. Check placement and try again");
            }
            else
                result.setMessage("Cannot place Robber at this time");

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

    private final GameUI.DevCardPanelListener devCardPanelListener = new GameUI.DevCardPanelListener() {
        public Map<DevelopmentCard, Integer> onUpdateDevCardPanel() {
            Player currentPlayer = playerManager.getCurrentPlayer();
            return currentPlayer.getDevCards();
        }
    };

    private final GameUI.RobPlayerPanelListener robPlayerPanelListener = new GameUI.RobPlayerPanelListener() {
        public List<Integer> getRobbablePlayers() {
            Player currentPlayer = playerManager.getCurrentPlayer();
            return board.getRobbablePlayers(currentPlayer.getPlayerId());
        }

        public void onRobPlayer(int targetPlayerId) {
            if(targetPlayerId >= 0) {
                Player targetPlayer = playerManager.getPlayerById(targetPlayerId);
                Map<ResourceType, Integer> stolenCardMap = targetPlayer.stealResourceCard();

                if (stolenCardMap != null) {
                    Player currentPlayer = playerManager.getCurrentPlayer();
                    currentPlayer.addResourceCards(stolenCardMap);
                }
            }

            updateState(GameState.TURN_STARTED);
        }
    };

    private GameState gameState;
    private int rollVal;

    public Game(int numPlayers, GameListener gameListener) {
        this.numPlayers = numPlayers;
        this.gameListener = gameListener;

        setupBoardManager = new SetupBoardManager();

        playerManager = new PlayerManager(numPlayers);

        gameUI = new GameUI(this);
        gameUI.setBoardPanelListener(boardPanelListener);
        gameUI.setControlPanelListener(controlPanelListener);
        gameUI.setResourcePanelListener(resourcePanelLister);
        gameUI.setDevCardPanelListener(devCardPanelListener);
        gameUI.setRobPlayerPanelListener(robPlayerPanelListener);

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
            MoveResult moveResult = new MoveResult();

            SetupStage stage = setupStages.get(setupStageIndex);
            Player player = playerManager.getCurrentPlayer();

            if(stage.gameState != GameState.BUILD_SETTLEMENT) {
                moveResult.setSuccess(false);
                moveResult.setMessage(MoveResult.ERROR_SELECT_EDGE_TO_BUILD_MSG);
                return moveResult;
            }

            if(board.buildSettlement(player, cornerId, true)) {
                moveResult.setSuccess(true);

                player.incrementVictoryPoints();

                if(setupStageIndex >= ((numPlayers*4)/2))
                    assignStartingResourceCards(cornerId);

                setupStageIndex++;
            }
            else {
                moveResult.setSuccess(false);
                moveResult.setMessage(MoveResult.ERROR_FAILED_TO_BUILD_MSG);
            }

            if(setupStageIndex == (numPlayers*4))
                updateState(GameState.START_TURN);
            else {
                SetupStage nextStage = setupStages.get(setupStageIndex);
                playerManager.setPlayerById(nextStage.playerId);
                updateState(GameState.SETUP_BOARD);
            }

            return moveResult;
        }

        private void assignStartingResourceCards(int cornerId) {
            Player currentPlayer = playerManager.getCurrentPlayer();
            Map<ResourceType, Integer> initialResourceCards = currentPlayer.getResourceCards();

            ResourceType currentResourceType;
            int currentResourceCount;

            List<Hex> hexList = board.getHexList();
            List<Corner> cornerList;

            for(Hex hex : hexList) {
                cornerList = hex.getCorners();

                for(Corner corner : cornerList) {
                    if(corner.getId() == cornerId) {
                        currentResourceType = hex.getHexResourceType();
                        currentResourceCount = initialResourceCards.get(currentResourceType);
                        initialResourceCards.put(currentResourceType, currentResourceCount+1);
                    }
                }
            }

            currentPlayer.addResourceCards(initialResourceCards);
        }

        private MoveResult setupEdge(int edgeId) {
            MoveResult moveResult = new MoveResult();
            SetupStage stage = setupStages.get(setupStageIndex);
            Player player = playerManager.getCurrentPlayer();

            if(stage.gameState != GameState.BUILD_ROAD) {
                moveResult.setSuccess(false);
                moveResult.setMessage(MoveResult.ERROR_SELECT_CORNER_TO_BUILD_MSG);
            }

            if(board.buildRoad(player, edgeId)) {
                moveResult.setSuccess(true);

                setupStageIndex++;
            }
            else {
                moveResult.setSuccess(false);
                moveResult.setMessage(MoveResult.ERROR_FAILED_TO_BUILD_MSG);
            }

            if(setupStageIndex == (numPlayers*4))
                updateState(GameState.START_TURN);
            else {
                SetupStage nextStage = setupStages.get(setupStageIndex);
                playerManager.setPlayerById(nextStage.playerId);
                updateState(GameState.SETUP_BOARD);
            }

            return moveResult;
        }
    }
}
