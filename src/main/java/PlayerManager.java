import java.util.ArrayList;
import java.util.List;


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
    }

    public Player getPlayerById(int playerId) {
        Player result = null;

        for(Player player : playersList) {
            if (player.getPlayerId() == playerId)
                result = player;
        }

        return result;
    }
}
