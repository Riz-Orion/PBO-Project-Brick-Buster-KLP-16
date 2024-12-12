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

    public void paintComponent(Graphics g) { 
        super.paintComponent(g);
    
        // Background Hitam
        g.setColor(Color.black);
        g.fillRect(0, 0, getWidth(), getHeight());
    
        g.setFont(new Font("Serif", Font.BOLD, 50));
        g.setColor(Color.white);
        String title = "About";
        FontMetrics titleMetrics = g.getFontMetrics(); 
        int titleWidth = titleMetrics.stringWidth(title); 
        g.drawString(title, (getWidth() - titleWidth) / 2, 80); 
    
        g.drawLine(50, 100, getWidth() - 50, 100);
    
        g.setColor(new Color(50, 50, 50, 200)); 
        g.fillRoundRect(50, 120, getWidth() - 100, getHeight() - 200, 25, 25);
    
        // Teks Konten
        g.setColor(Color.white);
        g.setFont(new Font("Serif", Font.PLAIN, 20));
        String aboutText = """
                Brick Buster is a modern take on the classic arcade game.
                You control a paddle to bounce the ball and destroy all the bricks.
                Featuring power-ups like Multi Ball, Extend Paddle, and Quick Paddle.
                Test your skills, break the bricks, and climb the leaderboard!
                """;
    
        FontMetrics contentMetrics = g.getFontMetrics(); 
        int yPosition = 160;
        int lineSpacing = 30; 
        for (String line : aboutText.split("\n")) {
            int lineWidth = contentMetrics.stringWidth(line.trim()); 
            int xPosition = (getWidth() - lineWidth) / 2; 
            g.drawString(line.trim(), xPosition, yPosition);
            yPosition += lineSpacing;
        }
    
        // Footer
        g.setFont(new Font("Serif", Font.ITALIC, 20)); 
        g.setColor(Color.lightGray);
        String footerText = "Press ESC to return to menu";
        FontMetrics footerMetrics = g.getFontMetrics();
        int footerWidth = footerMetrics.stringWidth(footerText); 
        g.drawString(footerText, (getWidth() - footerWidth) / 2, getHeight() - 50); 
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
