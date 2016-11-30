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
    };

    public void registerUpdateStateListener(UpdateStateListener updateStateListener) {
        updateStateListenersList.add(updateStateListener);
    }

    public void unregisterUpdateStateListener(UpdateStateListener updateStateListener) {
        updateStateListenersList.remove(updateStateListener);
    }

    private final List<UpdateStateListener> updateStateListenersList = new ArrayList();

    private final int numPlayers;
    private final Bank bank = new Bank();
    private final Board board = new Board();
    private final PlayerManager playerManager;
    private final GameUI gameUI;

    private GameState currentState;

    public Game(int numPlayers) {
        this.numPlayers = numPlayers;
        this.playerManager = new PlayerManager(numPlayers);
        this.gameUI = new GameUI();
    }

    public GameUI getGameUI() {
        return gameUI;
    }

}
