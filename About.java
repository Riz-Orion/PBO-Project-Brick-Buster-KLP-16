import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class About extends JPanel implements KeyListener {
    private MainContainer container;

    public About(MainContainer container) {
        this.container = container;
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        addKeyListener(this);
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
        g.drawString("About", getWidth() / 2 - 100, 80);
    
        // Description
        g.setFont(new Font("Serif", Font.PLAIN, 20));
        String aboutText = "Brick Buster is a modern take on the classic arcade game.\n" +
                            " In this game, you take control of a paddle to bounce the ball and destroy all the bricks above.\n" +
                            " Featuring power-ups like Multi Ball, Extend Paddle, Double Score, and Quick Paddle, the game challenges your skills.\n" +
                            " With each brick broken, you score points and advance through increasingly challenging levels.\n" +
                            " This game invites you to test your skills and climb the leaderboard!";
    
        FontMetrics fm = g.getFontMetrics();
        int maxLineWidth = getWidth() - 110;  // Max width for each line (leaving some padding)
        int yPosition = 150;
    
        String[] words = aboutText.split(" ");
        StringBuilder currentLine = new StringBuilder();
    
        for (String word : words) {
            currentLine.append(word).append(" ");
            if (fm.stringWidth(currentLine.toString()) > maxLineWidth) {
                g.drawString(currentLine.toString().trim(), 20, yPosition);
                yPosition += 30;
                currentLine.setLength(0);  // Reset the current line
                currentLine.append(word).append(" ");
            }
        }
        
        // Draw the last line if there is any remaining text
        if (currentLine.length() > 0) {
            g.drawString(currentLine.toString().trim(), 20, yPosition);
        }
    
        // Footer instruction
        g.setFont(new Font("Serif", Font.ITALIC, 20));
        g.setColor(Color.gray);
        g.drawString("Press ESC to return to menu", getWidth() / 2 - 150, getHeight() - 50);
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
