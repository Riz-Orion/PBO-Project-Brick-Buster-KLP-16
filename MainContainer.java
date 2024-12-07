import javax.swing.*;
import java.awt.*;

public class MainContainer extends JPanel {
    private CardLayout cardLayout;
    private MainMenu mainMenu;
    private Gameplay gamePanel;
    private Leaderboard leaderboard;
    private About about;

    public MainContainer() {
        cardLayout = new CardLayout();
        setLayout(cardLayout);

        // Create all panels
        mainMenu = new MainMenu(this);
        gamePanel = new Gameplay(this, "Default Player");
        leaderboard = new Leaderboard(this);
        about = new About(this);

        // Add panels to CardLayout
        add(mainMenu, "MainMenu");
        add(gamePanel, "Game");
        add(leaderboard, "Leaderboard");
        add(about, "About");

        cardLayout.show(this, "MainMenu");
    }

    public void showCard(String name) {
        if ("Leaderboard".equals(name)) {
            leaderboard.fetchLeaderboardData();
        }
        cardLayout.show(this, name);
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