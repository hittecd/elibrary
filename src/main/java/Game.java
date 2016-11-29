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

    private final GameUI gameUI = new GameUI();
    private final Bank bank = new Bank();
    private final Board board = new Board();
    private final PlayerManager playerManager;

    private int numPlayers;
    private GameState currentState;

    public Game(int numPlayers) {
        this.numPlayers = numPlayers;
        this.playerManager = new PlayerManager(numPlayers);
    }

}
