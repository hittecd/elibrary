import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;


public class MainMenuUI extends JPanel {

    public interface MainMenuUIListener extends ActionListener {};

    private MainMenuUIListener mainMenuUIListener;

    private JLabel newGameLabel = new JLabel("New Game:");
    public JButton newGameBtn3 = new JButton("3 Players");

    /**
     * TODO: implement load features
     * private JLabel loadGameLabel;
     * private JComboBox loadGameMenu;
     * private JButton loadGameBtn;
    */

    /**
     * TODO: implement delete game features
     * private JButton deleteGameBtn;
     */

    /**
     * TODO: impelment quit game features
     * private JButton quitGameBtn;
     */

    public MainMenuUI(MainMenuUIListener listener) {
        setMainMenuUIListener(listener);

        newGameBtn3.addActionListener(mainMenuUIListener);

        this.add(newGameLabel);
        this.add(newGameBtn3);

        this.setLayout(new FlowLayout(FlowLayout.CENTER));
    }

    public void setMainMenuUIListener(MainMenuUIListener mainMenuUIListener) {
        this.mainMenuUIListener = mainMenuUIListener;
    }

}
