import javax.swing.*;
import java.awt.*;

public class MainContainer extends JPanel {
    private CardLayout cardLayout;
    private Gameplay gamePanel;

    public MainContainer() {
        cardLayout = new CardLayout();
        setLayout(cardLayout);

        gamePanel = new Gameplay(this, "Default Player");

        add(gamePanel, "Game");

        cardLayout.show(this, "MainMenu");
    }

    public void setGamePanel(Gameplay newGamePanel) {
        remove(gamePanel);

        gamePanel = newGamePanel;

        add(gamePanel, "Game");

        revalidate();
        repaint();
    }
}