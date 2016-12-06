import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;


public class GameManager {

    private final JFrame mainGameFrameUI = new JFrame("Welcome to Settlers of Catan!");;
    private final MainMenuUI mainMenuUI = new MainMenuUI();
    private final DatabaseHelper databaseHelper = new DatabaseHelper();
    private final MainMenuUI.MainMenuUIListener listener = new MainMenuUI.MainMenuUIListener() {
        public void onNewGame(int numPlayers) {
            newGame(numPlayers);
        }
    };

    private Game currentGame;

    public GameManager() {
        mainMenuUI.setMainMenuUIListener(listener);

        mainGameFrameUI.add(mainMenuUI);
        mainGameFrameUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainGameFrameUI.setExtendedState(JFrame.MAXIMIZED_BOTH); // set ui to full screen
        mainGameFrameUI.setVisible(true);
    }

    public static void main(String[] args) {
        GameManager gameManager = new GameManager();
    }

    private void newGame(int numPlayers) {
        System.out.println("CREATING NEW GAME: " + numPlayers);

        currentGame = new Game(numPlayers);

        mainGameFrameUI.remove(mainMenuUI);


        mainGameFrameUI.setContentPane(currentGame.getGameUI());
        mainGameFrameUI.pack();
        mainGameFrameUI.setLocationRelativeTo(null);
        mainGameFrameUI.revalidate();
        mainGameFrameUI.repaint();
    }

    /**
     * TODO: implement load game support
     * loadGame()
     */

    /**
     * TODO: implement delete game support
     * deleteGame()
     */

    /**
     * TODO: implement quit support
     * quit()
     */

}
