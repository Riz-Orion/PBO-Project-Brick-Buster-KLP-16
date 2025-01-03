import javax.swing.*;
import java.awt.*;

public class MainContainer extends JPanel {
    private CardLayout cardLayout;
    private MainMenu mainMenu;
    private Gameplay gamePanel;
    private Leaderboard leaderboard;
    private About about;
    private AudioManager audioManager;

    public MainContainer() {
        cardLayout = new CardLayout();
        setLayout(cardLayout);

        // Inisialisasi AudioManager
        audioManager = AudioManager.getInstance();

        // Create all panels
        mainMenu = new MainMenu(this, audioManager);
        gamePanel = new Gameplay(this, "Default Player", audioManager);
        leaderboard = new Leaderboard(this);
        about = new About(this);

        // Add panels to CardLayout
        add(mainMenu, "MainMenu");
        add(gamePanel, "Game");
        add(leaderboard, "Leaderboard");
        add(about, "About");

        // Play main menu BGM
        audioManager.playBGM("assets/Sound/menu_bgm.wav", true);

        cardLayout.show(this, "MainMenu");
    }

    public void showCard(String name) {
        if ("Leaderboard".equals(name)) {
            leaderboard.fetchLeaderboardData();
        }
        cardLayout.show(this, name);

        Component currentPanel = null;

        for (Component comp : getComponents()) {
            if (comp.isVisible()) {
                currentPanel = comp;
                break;
            }
        }
    
        if (currentPanel instanceof JPanel) {
            ((JPanel) currentPanel).requestFocusInWindow();
        }
    }

    public void setGamePanel(Gameplay newGamePanel) {
        // Remove old gameplay panel
        remove(gamePanel);

        // Set new gameplay panel
        gamePanel = newGamePanel;

        // Add the new panel to the CardLayout
        add(gamePanel, "Game");

        // Refresh the layout
        revalidate();
        repaint();
    }
}