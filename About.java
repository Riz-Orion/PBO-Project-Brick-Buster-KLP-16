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
        g.drawString("About", getWidth() / 2 - 100, 100);

        // Description
        g.setFont(new Font("Serif", Font.PLAIN, 20));
        String aboutText = "Brick Breaker is a classic arcade game.\n\n"
                + "Created as a learning project to demonstrate Java GUI capabilities.\n\n"
                + "Developed by YourName.";
        int yPosition = 200;
        for (String line : aboutText.split("\n")) {
            g.drawString(line, getWidth() / 2 - 300, yPosition);
            yPosition += 30;
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
