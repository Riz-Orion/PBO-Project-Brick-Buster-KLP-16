import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainMenu extends JPanel implements KeyListener {
    private String[] menuOptions = {"Start Game", "Leaderboard", "About"};
    private int selectedIndex = 0;
    private MainContainer container;
    private Image[] resizedIcons;
    private Image backgroundImage;
    private Image resizedBackground;

    public MainMenu(MainContainer container) {
        this.container = container;

        // Load and resize icons
        String[] iconPaths = {"assets/play2.png", "assets/podium.png", "assets/info.png"};
        resizedIcons = new Image[iconPaths.length];
        for (int i = 0; i < iconPaths.length; i++) {
            resizedIcons[i] = resizeIcon(new ImageIcon(iconPaths[i]).getImage(), 50, 50);
        }

        // Load background image
        try {
            backgroundImage = new ImageIcon("assets/background.jpeg").getImage();
            resizedBackground = resizeBackground(backgroundImage, 800, 600);
        } catch (Exception e) {
            System.err.println("Error loading background image: " + e.getMessage());
            backgroundImage = null;
        }

        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        addKeyListener(this);
    }

    private Image resizeIcon(Image img, int width, int height) {
        return img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
    }

    private Image resizeBackground(Image img, int width, int height) {
        return img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (resizedBackground != null) {
            g.drawImage(resizedBackground, 0, 0, getWidth(), getHeight(), this);
        } else {
            g.setColor(Color.black);
            g.fillRect(0, 0, getWidth(), getHeight());
        }

        // Add a translucent overlay for modern design
        Graphics2D g2d = (Graphics2D) g;
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f));
        g2d.setColor(new Color(0, 0, 0, 150));
        g2d.fillRoundRect(50, 50, getWidth() - 100, getHeight() - 100, 30, 30);

        // Reset alpha for other components
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

        // Title
        g.setColor(Color.white);
        g.setFont(new Font("SansSerif", Font.BOLD, 50));
        g.drawString("Brick Buster", getWidth() / 2 - 170, 120);

        // Menu Options
        g.setFont(new Font("SansSerif", Font.PLAIN, 30));
        for (int i = 0; i < menuOptions.length; i++) {
            if (i == selectedIndex) {
                g.setColor(new Color(170, 84, 134)); // Highlight selected option
            } else {
                g.setColor(Color.white);
            }

            // Draw resized icon
            int iconX = getWidth() / 2 - 200; // Icon position
            int iconY = 200 + i * 70;         // Icon aligned with text
            if (resizedIcons[i] != null) {
                g.drawImage(resizedIcons[i], iconX, iconY - 30, this); // Align icon vertically
            }

            // Draw text
            g.drawString(menuOptions[i], getWidth() / 2 - 100, 200 + i * 70);
        }

        // Footer
        g.setFont(new Font("SansSerif", Font.ITALIC, 20));
        g.setColor(Color.lightGray);
        g.drawString("Use ↑/↓ to navigate, Enter to select", getWidth() / 2 - 150, getHeight() - 60);
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
