import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;


public class MainMenuUI extends JPanel {

    private final JPanel logoPanel = new JPanel();
    private final JPanel controlPanel = new JPanel();
    private final JLabel newGameLabel = new JLabel("New Game");
    private final JButton newGameBtn3 = new JButton("3 Players");
    private final JButton newGameBtn4 = new JButton("4 Players");

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

    private final ActionListener mainMenuUIActionListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == newGameBtn3)
                mainMenuUIListener.onNewGame(3);
            else if(e.getSource() == newGameBtn4)
                mainMenuUIListener.onNewGame(4);
        }
    };

    private MainMenuUIListener mainMenuUIListener;

    public MainMenuUI() {
        Border blackline = BorderFactory.createLineBorder(Color.BLACK);

        //format logo panel
        try {
            BufferedImage myPicture = ImageIO.read(this.getClass().getResource("images/settlers-of-catan-main.jpg"));
            JLabel picLabel = new JLabel(new ImageIcon(myPicture));
            logoPanel.add(picLabel);
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        logoPanel.setBorder(blackline);

        //format control panel
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.PAGE_AXIS));

        newGameBtn3.addActionListener(mainMenuUIActionListener);
        newGameBtn4.addActionListener(mainMenuUIActionListener);

        controlPanel.add(newGameLabel);
        controlPanel.add(newGameBtn3);
        controlPanel.add(newGameBtn4);

        controlPanel.setBorder(blackline);
        controlPanel.setPreferredSize(new Dimension(300, 300));

        //format main ui
        this.add(logoPanel);
        this.add(controlPanel);
        this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        this.setBorder(blackline);
    }

    public void setMainMenuUIListener(MainMenuUIListener mainMenuUIListener) {
        this.mainMenuUIListener = mainMenuUIListener;
    }

    public interface MainMenuUIListener {
        void onNewGame(int numPlayers);
    }

}
