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
        requestFocusInWindow();

        // Key Binding sebagai alternatif
        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "exit");
        getActionMap().put("exit", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                container.showCard("MainMenu");
            }
        });
    }

    @Override
    public void addNotify() {
        super.addNotify();
        requestFocusInWindow();
    }

    public void fetchLeaderboardData() {
        leaderboardData = dbManager.getLeaderboardData();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Background Tetap Hitam
        g.setColor(Color.black);
        g.fillRect(0, 0, getWidth(), getHeight());

        g.setFont(new Font("Serif", Font.BOLD, 50));
        g.setColor(Color.white);
        String title = "Leaderboard";
        FontMetrics titleMetrics = g.getFontMetrics(); 
        int titleWidth = titleMetrics.stringWidth(title); 
        g.drawString(title, (getWidth() - titleWidth) / 2, 80); 

        g.drawLine(50, 100, getWidth() - 50, 100);

        g.setColor(new Color(50, 50, 50, 200)); 
        g.fillRoundRect(50, 140, getWidth() - 100, getHeight() - 200, 25, 25);

        // Leaderboard Data
        g.setFont(new Font("Serif", Font.PLAIN, 20));
        g.setColor(Color.white);
        int yOffset = 180;
        for (String entry : leaderboardData) {
            g.drawString(entry, getWidth() / 2 - 200, yOffset);
            yOffset += 40;
        }

        // Footer
        g.setFont(new Font("Serif", Font.ITALIC, 20));
        g.setColor(Color.lightGray);
        g.drawString("Press ESC to return to menu", getWidth() / 2 - 150, getHeight() - 30);
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
