import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;


public class GameManager {

    private JFrame mainGameFrameUI;
    private MainMenuUI mainMenuUI;
    private Game currentGame;
    private DatabaseHelper databaseHelper;

    public GameManager() {
        // TODO: init database helper

        // init main game frame
        mainGameFrameUI = new JFrame("Welcome to Settlers of Catan!");

        // init main menu ui
        MainMenuUI.MainMenuUIListener listener = new MainMenuUI.MainMenuUIListener() {
            public void startNewGame(int numPlayers) {
                newGame(numPlayers);
            }
        };
        mainMenuUI = new MainMenuUI(listener); // automatically sets up ui

        mainGameFrameUI.add(mainMenuUI);

        mainGameFrameUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainGameFrameUI.setExtendedState(JFrame.MAXIMIZED_BOTH); // set ui to full screen
        mainGameFrameUI.setVisible(true);
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
