import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class Leaderboard extends JPanel implements KeyListener {
    private MainContainer container;
    private List<String> leaderboardData;
    private DatabaseManager dbManager = new DatabaseManager();
    private Image backgroundImage;
    private Image resizedBackground;

    public Leaderboard(MainContainer container) {
        this.container = container;
        
        // Load background image
        try {
            backgroundImage = new ImageIcon("assets/background.jpeg").getImage();
            resizedBackground = resizeBackground(backgroundImage, 800, 600);
        } catch (Exception e) {
            System.err.println("Error loading background image: " + e.getMessage());
            backgroundImage = null;
        }

        // Setup key listener and focus
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        addKeyListener(this);
        
        fetchLeaderboardData();
        requestFocusInWindow();

        // Key Binding untuk tombol ESC
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

        g.setFont(new Font("Serif", Font.BOLD, 50));
        g.setColor(Color.white);
        String title = "Leaderboard";
        FontMetrics titleMetrics = g.getFontMetrics();
        int titleWidth = titleMetrics.stringWidth(title);
        g.drawString(title, (getWidth() - titleWidth) / 2, 80);

        g.drawLine(50, 100, getWidth() - 50, 100);

        g.setColor(new Color(50, 50, 50, 200));
        g.fillRoundRect(50, 140, getWidth() - 100, getHeight() - 200, 25, 25);

        // Header Tabel
        g.setFont(new Font("Serif", Font.BOLD, 20));
        g.setColor(Color.white);
        int yOffset = 180; 
        int col1X = 100; 
        int col3X = getWidth() - 200; 
        int col2X = (col1X + col3X) / 2; 

        g.drawString("Nama", col1X, yOffset);
        g.drawString("Level", col2X, yOffset);
        g.drawString("Score", col3X, yOffset);

        g.drawLine(75, yOffset + 5, getWidth() - 75, yOffset + 5);

        // Menampilkan Data Leaderboard
        g.setFont(new Font("Serif", Font.PLAIN, 18));
        yOffset += 40; 
        if (leaderboardData != null && !leaderboardData.isEmpty()) {
            for (String entry : leaderboardData) {
                String[] parts = entry.split(" - "); 
                if (parts.length == 3) {
                    String playerName = parts[0].split("\\. ")[1]; 
                    String level = parts[1].split(": ")[1];
                    String score = parts[2].split(": ")[1]; 

                    // Tampilkan data di kolom
                    g.drawString(playerName, col1X, yOffset);
                    g.drawString(level, col2X, yOffset);
                    g.drawString(score, col3X, yOffset);
                }
                yOffset += 30;
            }
        } else {
            g.setColor(Color.lightGray);
            g.drawString("No data available", (getWidth() - 200) / 2, yOffset);
        }

        // Footer 
        g.setFont(new Font("Serif", Font.ITALIC, 20));
        g.setColor(Color.lightGray);
        String footerText = "Press ESC to return to menu";
        FontMetrics footerMetrics = g.getFontMetrics();
        int footerWidth = footerMetrics.stringWidth(footerText);
        g.drawString(footerText, (getWidth() - footerWidth) / 2, getHeight() - 20); 
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
