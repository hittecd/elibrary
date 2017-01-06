import javax.swing.*;


public class GameManager {

    private final JFrame mainGameFrameUI = new JFrame("Welcome to Settlers of Catan!");;
    private final MainMenuUI mainMenuUI = new MainMenuUI();
    private final DatabaseHelper databaseHelper = new DatabaseHelper();
    private final MainMenuUI.MainMenuUIListener mainMenuUIListener = new MainMenuUI.MainMenuUIListener() {
        public void onNewGame(int numPlayers) {
            newGame(numPlayers);
        }

        public void onQuitSoC() {
            quitSoC();
        }
    };
    private final Game.GameListener gameListener = new Game.GameListener() {
        public void onExitGame() {
            mainGameFrameUI.remove(currentGame.getGameUI());

            mainGameFrameUI.setContentPane(mainMenuUI);
            mainGameFrameUI.pack();
            mainGameFrameUI.setLocationRelativeTo(null);
            mainGameFrameUI.revalidate();
            mainGameFrameUI.repaint();
        }
    };

    private Game currentGame;

    public GameManager() {
        mainMenuUI.setMainMenuUIListener(mainMenuUIListener);
    }

    private void start() {
        mainGameFrameUI.add(mainMenuUI);
        mainGameFrameUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainGameFrameUI.setExtendedState(JFrame.MAXIMIZED_BOTH); // set ui to full screen
        mainGameFrameUI.setVisible(true);
    }

    public static void main(String[] args) {
        GameManager gameManager = new GameManager();
        gameManager.start();
    }

    private void newGame(int numPlayers) {
        System.out.println("CREATING NEW GAME: " + numPlayers);

        currentGame = new Game(numPlayers, gameListener);

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

    private void quitSoC() {
        System.exit(0);
    }

}
