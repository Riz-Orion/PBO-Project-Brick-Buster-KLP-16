import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class About extends JPanel implements KeyListener {
    private MainContainer container;
    private Image backgroundImage;
    private Image resizedBackground;

    public About(MainContainer container) {
        this.container = container;
        
        // Load background image
        try {
            backgroundImage = new ImageIcon("assets/background.jpeg").getImage();
            resizedBackground = resizeBackground(backgroundImage, 800, 600);
        } catch (Exception e) {
            System.err.println("Error loading background image: " + e.getMessage());
            backgroundImage = null;
        }

        // Set up key listener and focus
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        addKeyListener(this);
    }

    private Image resizeBackground(Image img, int width, int height) {
        return img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw background image if available
        if (resizedBackground != null) {
            g.drawImage(resizedBackground, 0, 0, getWidth(), getHeight(), this);
        } else {
            // Draw black background if image loading fails
            g.setColor(Color.black);
            g.fillRect(0, 0, getWidth(), getHeight());
        }

        // Title text
        g.setFont(new Font("Serif", Font.BOLD, 50));
        g.setColor(Color.white);
        String title = "About";
        FontMetrics titleMetrics = g.getFontMetrics();
        int titleWidth = titleMetrics.stringWidth(title);
        g.drawString(title, (getWidth() - titleWidth) / 2, 80);

        // Draw horizontal line
        g.drawLine(50, 100, getWidth() - 50, 100);

        // Content box (semi-transparent)
        g.setColor(new Color(50, 50, 50, 200));
        g.fillRoundRect(50, 120, getWidth() - 100, getHeight() - 200, 25, 25);

        // About text content
        g.setColor(Color.white);
        g.setFont(new Font("Serif", Font.PLAIN, 20));
        String aboutText = """
                Brick Buster is a modern take on the classic arcade game.
                You control a paddle to bounce the ball and destroy all the bricks.
                Featuring power-ups like Multi Ball, Double Score, Extend Paddle, and Quick Paddle.
                Test your skills, break the bricks, and climb the leaderboard!
                """;

        FontMetrics contentMetrics = g.getFontMetrics();
        int yPosition = 160;
        int lineSpacing = 30;
        int maxTextWidth = getWidth() - 150;
                
        for (String paragraph : aboutText.split("\n")) {
            String[] words = paragraph.split(" ");
            StringBuilder line = new StringBuilder();
            for (String word : words) {
                String testLine = line + word + " ";
                if (contentMetrics.stringWidth(testLine) > maxTextWidth) {
                    int xPosition = (getWidth() - contentMetrics.stringWidth(line.toString())) / 2;
                    g.drawString(line.toString(), xPosition, yPosition);
                    yPosition += lineSpacing;
                    line = new StringBuilder(word + " ");
                } else {
                    line.append(word).append(" ");
                    }
                }
            int xPosition = (getWidth() - contentMetrics.stringWidth(line.toString())) / 2;
            g.drawString(line.toString(), xPosition, yPosition);
            yPosition += lineSpacing;
        }

        // Footer text
        g.setFont(new Font("Serif", Font.ITALIC, 20));
        String footerText = "Press ESC to go back to Main Menu";
        FontMetrics footerMetrics = g.getFontMetrics();
        int footerWidth = footerMetrics.stringWidth(footerText);
        g.drawString(footerText, (getWidth() - footerWidth) / 2, getHeight() - 40);
    }
    

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            container.showCard("MainMenu");
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}
}
