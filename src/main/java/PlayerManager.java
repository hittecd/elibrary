import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class PlayerManager {

    private int numPlayers;
    private final List<Player> playersList = new ArrayList();
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

    public Player getPlayerById(int playerId) {
        Player result = null;

        for(Player player : playersList) {
            if (player.getPlayerId() == playerId)
                result = player;
        }

        return result;
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
}
