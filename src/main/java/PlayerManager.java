import sun.rmi.server.InactiveGroupException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class PlayerManager {

    private final int numPlayers;
    private final List<Player> playersList = new ArrayList();

    private Player largestArmyPlayer = null;

    private int currentPlayerIndex;
    private Player currentPlayer;

    public PlayerManager(int numPlayers) {
        this.numPlayers = numPlayers;

        for(int i=0; i<numPlayers; i++) {
            playersList.add(new Player(i));
        }

        currentPlayerIndex = 0;
        currentPlayer = playersList.get(currentPlayerIndex);
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void updateCurrentPlayer() {
        currentPlayerIndex++;

        // if the adjusted player index is greater than the number of players
        // reset (wrap) the player index
        if((currentPlayerIndex + 1) > numPlayers)
            currentPlayerIndex = 0;

        currentPlayer = playersList.get(currentPlayerIndex);
    }

    public Player getLargestArmyPlayer() {
        return largestArmyPlayer;
    }

    public Player getPlayerById(int playerId) {
        Player result = null;

        for(Player player : playersList) {
            if (player.getPlayerId() == playerId)
                result = player;
        }

        return result;
    }


    public void setPlayerById(int playerId) {
        currentPlayer = playersList.get(playerId);
    }

    public Map<Player, Map<ResourceType, Integer>> resolveResources(List<Hex> hexList) {
        Map<Player, Map<ResourceType, Integer>> result = new HashMap();
        Map<ResourceType, Integer> resourceMap;
        ResourceType resourceType;
        int resourceCount;

        for(Player p : playersList) {
            resourceMap = new HashMap();

            for (Hex h : hexList) {
                resourceType = h.getHexResourceType();

                if(resourceMap.get(resourceType) == null)
                    resourceCount = 0;
                else
                    resourceCount = resourceMap.get(h.getHexResourceType());

                for(Corner c : h.getCorners())
                    if(c.getPlayerId() == p.getPlayerId()) {
                        if(c.hasCity())
                            resourceCount += 2;
                        else
                            resourceCount++;
                    }

                resourceMap.put(resourceType, resourceCount);
            }

            p.addResourceCards(resourceMap);

            result.put(p, resourceMap);
        }

        return result;
    }

    public boolean knightPlayed() {
        if(currentPlayer.playDevelopmentCard(DevelopmentCard.KNIGHT)) {
            currentPlayer.incrementKnightCount();
            updateLargestArmy();

            return true;
        }

        return false;
    }

    private void updateLargestArmy() {
        Player currentLargestArmyPlayer = largestArmyPlayer;
        Player newLargestArmyPlayer = null;
        int maxKnightCount = 0;

        for(Player p : playersList) {
            int playerKnightCount = p.getKnightCount();
            if(playerKnightCount > 3 && playerKnightCount > maxKnightCount) {
                newLargestArmyPlayer = p;
            }
        }

        if(newLargestArmyPlayer != null) {
            newLargestArmyPlayer.awardLargestArmyBonus();
        }

        if(currentLargestArmyPlayer != null) {
            currentLargestArmyPlayer.loseLargestArmyBonus();
        }

        largestArmyPlayer = newLargestArmyPlayer;
    }

    public boolean victoryPointPlayed() {
        if(currentPlayer.playDevelopmentCard(DevelopmentCard.VICTORY_POINT)) {
            currentPlayer.incrementVictoryPoints();

            return true;
        }

        return false;
    }

    public boolean roadBuilderPlayed() {
        if(currentPlayer.playDevelopmentCard(DevelopmentCard.ROAD_BUILDER)) {
            return true;
        }

        return false;
    }

    public boolean yearOfPlentyPlayed() {
        return currentPlayer.playDevelopmentCard(DevelopmentCard.YEAR_OF_PLENTY);
    }

    public void allocateYearOfPlentyCards(Map<ResourceType, Integer> resourceCardMap) {
        currentPlayer.addResourceCards(resourceCardMap);
    }

    public boolean monopolyPlayed() {
        return currentPlayer.playDevelopmentCard(DevelopmentCard.MONOPOLY);
    }

    public void reallocateMonopolyCards(ResourceType resourceType) {
        int totalResourceTypeCount = 0;
        int playerResourceTypeCount;

        for(Player player : playersList) {
            if(player != currentPlayer) {
                Map<ResourceType, Integer> resourceMap = player.getResourceCards();

                playerResourceTypeCount = resourceMap.get(resourceType);
                totalResourceTypeCount += playerResourceTypeCount;

                Map<ResourceType, Integer> lostResourceCards = new HashMap();
                lostResourceCards.put(resourceType, playerResourceTypeCount);
                player.spendResourceCards(lostResourceCards);
            }
        }

        Map<ResourceType, Integer> gainedResourceCards = new HashMap();
        gainedResourceCards.put(resourceType, totalResourceTypeCount);

        currentPlayer.addResourceCards(gainedResourceCards);
    }
}
