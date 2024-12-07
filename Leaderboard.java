import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class Leaderboard extends JPanel implements KeyListener {
    private MainContainer container;
    private List<String> leaderboardData;
    private DatabaseManager dbManager = new DatabaseManager();



    public Leaderboard(MainContainer container) {
        this.container = container;
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        addKeyListener(this);
        fetchLeaderboardData();
    }

    public void fetchLeaderboardData() {
        leaderboardData = dbManager.getLeaderboardData();
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
        g.drawString("Leaderboard", getWidth() / 2 - 150, 100);
        
        // Leaderboard Data (Rank, Name, Level, Score)
        g.setFont(new Font("Serif", Font.PLAIN, 20));
        int yOffset = 180;
        for (String entry : leaderboardData) {
            g.drawString(entry, getWidth() / 2 - 200, yOffset);
            yOffset += 40;
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
