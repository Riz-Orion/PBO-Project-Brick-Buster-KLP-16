import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainMenu extends JPanel implements KeyListener {
    private String[] menuOptions = {"Start Game", "Leaderboard", "About"};
    private int selectedIndex = 0;
    private MainContainer container;
    private Image[] resizedIcons;

    public MainMenu(MainContainer container) {
        this.container = container;

        // Load and resize icons
        String[] iconPaths = {"assets/play.png", "assets/medal.png", "assets/about.png"};
        resizedIcons = new Image[iconPaths.length];
        for (int i = 0; i < iconPaths.length; i++) {
            resizedIcons[i] = resizeIcon(new ImageIcon(iconPaths[i]).getImage(), 40, 40);
        }

        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        addKeyListener(this);
    }

    private Image resizeIcon(Image img, int width, int height) {
        return img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Background
        g.setColor(Color.black);
        g.fillRect(0, 0, getWidth(), getHeight());

        // Title
        g.setColor(Color.white);
        g.setFont(new Font("Serif", Font.BOLD, 40));
        g.drawString("Brick Breaker", getWidth() / 2 - 150, 100);

        // Menu Options
        g.setFont(new Font("Serif", Font.PLAIN, 30));
        for (int i = 0; i < menuOptions.length; i++) {
            if (i == selectedIndex) {
                g.setColor(Color.green); // Highlight selected option
            } else {
                g.setColor(Color.white);
            }

            // Draw resized icon
            int iconX = getWidth() / 2 - 180; // Icon position
            int iconY = 200 + i * 50;         // Icon aligned with text
            if (resizedIcons[i] != null) {
                g.drawImage(resizedIcons[i], iconX, iconY - 30, this); // Align icon vertically
            }

            // Draw text
            g.drawString(menuOptions[i], getWidth() / 2 - 100, 200 + i * 50);
        }

        // Footer
        g.setFont(new Font("Serif", Font.ITALIC, 20));
        g.setColor(Color.gray);
        g.drawString("Use ↑/↓ to navigate, Enter to select", getWidth() / 2 - 150, getHeight() - 50);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            selectedIndex = (selectedIndex - 1 + menuOptions.length) % menuOptions.length;
            repaint();
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            selectedIndex = (selectedIndex + 1) % menuOptions.length;
            repaint();
        } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            handleSelection();
        }
    }

    private void handleSelection() {
        switch (selectedIndex) {
            case 0: // Start Game
            String playerName = JOptionPane.showInputDialog(
                this, "Enter your name:", "Player Name",
                JOptionPane.PLAIN_MESSAGE
            );

            if (playerName != null && !playerName.trim().isEmpty()) {
                Gameplay newGamePanel = new Gameplay(container, playerName.trim());
                container.setGamePanel(newGamePanel);
                container.showCard("Game");
            } else {
                JOptionPane.showMessageDialog(
                    this, "Name cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE
                );
            }
            break;
            case 1: // Leaderboard
                container.showCard("Leaderboard");
                break;
            case 2: // About
                container.showCard("About");
                break;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}
}