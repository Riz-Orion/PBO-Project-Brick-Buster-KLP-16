import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Gameplay extends JPanel implements KeyListener, Runnable {
    private boolean play = false;
    private boolean levelComplete = false; // Status untuk level selesai
    private int score = 0;
    private int level = 1;
    private int totalBricks;
    private int playerX = 310;
    private int ballSpeed = 8;
    private int paddleSpeed = 20;

    private MainContainer container;
    private MapGenerator map;
    private String playerName;
    private DatabaseManager dbManager = new DatabaseManager();
    private ArrayList<Ball> balls = new ArrayList<>();
    private AudioManager audioManager;
    private Image backgroundImage;

    // Power-up variables
    private ArrayList<PowerUp> powerUps = new ArrayList<>();
    private boolean extendPaddleActive = false;
    private boolean doubleScoreActive = false;
    boolean quickPaddleActive = false;
    private long powerUpTimer = 0;
    private String powerUpMessage = "";
    private boolean gameOverSoundPlayed = false;

    public Gameplay(MainContainer container, String playerName, AudioManager audioManager) {
        this.container = container;
        this.playerName = playerName;
        this.audioManager = audioManager;

        try {
            backgroundImage = new ImageIcon("assets/game_background.png").getImage();
        } catch (Exception e) {
            System.out.println("Background image not found!");
            e.printStackTrace();
        }
        

        map = new MapGenerator(3, 7);
        totalBricks = 3 * 7;
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);

        // Add the first ball to the game
        balls.add(new Ball(120, 350, -1, -2));

        Thread gameThread = new Thread(this);
        gameThread.start();
    }

    public void paint(Graphics g) {
        // Gambar background
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }

        // Drawing map
        map.draw((Graphics2D) g);

        // Borders
        g.setColor(Color.yellow);
        g.fillRect(0, 0, 3, 592);
        g.fillRect(0, 0, 692, 3);
        g.fillRect(691, 0, 3, 592);

        // Scores and Level
        g.setColor(Color.white);
        g.setFont(new Font("serif", Font.BOLD, 25));
        g.drawString("Score: " + score, 560, 30);
        g.drawString("Level: " + level, 20, 30);

        // Player Name
        g.setColor(Color.white);
        g.setFont(new Font("serif", Font.BOLD, 20));
        g.drawString("Player: " + playerName, 300, 30);

        // Paddle
        int paddleWidth = extendPaddleActive ? 150 : 100;
        g.setColor(Color.green);
        g.fillRect(playerX, 550, paddleWidth, 8);

        // Balls
        g.setColor(Color.yellow);
        for (Ball ball : balls) {
            g.fillOval(ball.getX(), ball.getY(), 20, 20);
        }

        // Power-Ups
        for (PowerUp powerUp : powerUps) {
            powerUp.draw(g);
        }

        // Draw the power-up activation message if available
        if (!powerUpMessage.isEmpty()) {
            g.setColor(Color.cyan);
            g.setFont(new Font("serif", Font.BOLD, 20));
            g.drawString(powerUpMessage, 230, 300);  // Adjust position as needed

        // Optionally, clear the message after a few seconds (e.g., 2 seconds)
        if (System.currentTimeMillis() - powerUpTimer > 1500) {
            powerUpMessage = "";  // Clear the message
        }
    }

        // Level Complete Notification
        if (levelComplete) {
            g.setColor(Color.green);
            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("Level " + level + " Complete!", 230, 300);

            g.setFont(new Font("serif", Font.BOLD, 20));
            g.drawString("Press Enter to Continue", 230, 350);
            return; // Jangan lanjutkan rendering jika level selesai
        }

        // Game Over
        if (balls.isEmpty()) {
            play = false;
            
            if (!gameOverSoundPlayed) {
                dbManager.savePlayerData(playerName, score, level);
                audioManager.stopBGM();
                audioManager.playSoundEffect("assets/Sound/game_over.wav"); // Putar suara
                gameOverSoundPlayed = true; // Set flag agar tidak diputar lagi
            }

            g.setColor(Color.red);
            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("Game Over, Score: " + score, 190, 300);

            g.setFont(new Font("serif", Font.BOLD, 20));
            g.drawString("Press Enter to Restart or ESC for Menu", 170, 350);
        }

        g.dispose();
    }

    private void levelUp() {
        levelComplete = true; // Tandai bahwa level selesai
        play = false;

        // Mainkan efek suara saat level selesai
        audioManager.playSoundEffect("assets/Sound/level_complete.wav");
        repaint();
    }

    private void startNextLevel() {
        levelComplete = false; // Reset status level selesai
        level++;
        ballSpeed = Math.max(ballSpeed - 1, 4); // Kurangi kecepatan bola, batas minimal
    
        // Reset posisi bola dan paddle
        balls.clear();
        balls.add(new Ball(120, 350, -1, -2)); // Tambahkan bola baru
        playerX = 310;
    
        // Tambah kesulitan dengan lebih banyak brick
        int rows = 3 + level;
        int cols = 7 + level;
        map = new MapGenerator(rows, cols);
        totalBricks = rows * cols;

        powerUps.clear();
        deactivatePowerUp();
        play = true;
        repaint();
    }

    @Override
    public void run() {
        while (true) {
            if (play) {
                ArrayList<Ball> ballsToRemove = new ArrayList<>();

                for (Ball ball : balls) {
                    // Deteksi tabrakan dengan dinding
                    if (ball.getX() <= 0 || ball.getX() >= 670) { // Dinding kiri atau kanan
                        ball.reverseXDir();
                        AudioManager.getInstance().playSoundEffect("assets/Sound/ball_bounce.wav");
                    }
                    if (ball.getY() <= 0) { // Dinding atas
                        ball.reverseYDir();
                        AudioManager.getInstance().playSoundEffect("assets/Sound/ball_bounce.wav");
                    }
    
                    // Deteksi tabrakan dengan paddle
                    if (new Rectangle(ball.getX(), ball.getY(), 20, 20).intersects(
                            new Rectangle(playerX, 550, extendPaddleActive ? 150 : 100, 8))) {
                        ball.reverseYDir();
                        ball.setY(550 - 20); 
                        AudioManager.getInstance().playSoundEffect("assets/Sound/ball_bounce.wav");
                    }

                    // Ball and brick collision
                    A: for (int i = 0; i < map.map.length; i++) {
                        for (int j = 0; j < map.map[0].length; j++) {
                            if (map.map[i][j] > 0) {
                                int brickX = j * map.brickWidth + 80;
                                int brickY = i * map.brickHeight + 50;
                                int brickWidth = map.brickWidth;
                                int brickHeight = map.brickHeight;

                                Rectangle rect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
                                Rectangle ballRect = new Rectangle(ball.getX(), ball.getY(), 20, 20);
                                Rectangle brickRect = rect;

                                if (ballRect.intersects(brickRect)) {
                                    map.setBrickValue(0, i, j);
                                    totalBricks--;
                                    score += doubleScoreActive ? 10 : 5;

                                    if (ball.getX() + 19 <= brickRect.x || ball.getX() + 1 >= brickRect.x + brickRect.width) {
                                        ball.reverseXDir();
                                    } else {
                                        ball.reverseYDir();
                                    }

                                    audioManager.playSoundEffect("assets/Sound/hit_brick.wav");

                                    // Drop power-up
                                    if (Math.random() < 0.2) {
                                        String[] powerUpTypes = {"multiBall", "extendPaddle", "doubleScore", "quickPaddle"};
                                        String type = powerUpTypes[(int) (Math.random() * powerUpTypes.length)];
                                        powerUps.add(new PowerUpItem(brickX + brickWidth / 2 - 10, brickY, type));
                                    }
                                    break A;
                                }
                            }
                        }
                    }

                    // Move the ball
                    ball.move();

                    // Remove ball if it goes out of bounds
                    if (ball.getY() > 600) {
                        ballsToRemove.add(ball);
                    }
                }

                balls.removeAll(ballsToRemove);

                if (System.currentTimeMillis() - powerUpTimer > 5000) {
                    extendPaddleActive = false;
                    doubleScoreActive = false;
                    quickPaddleActive = false;
                    paddleSpeed = 20;
                }

                // Power-ups logic
                for (int i = 0; i < powerUps.size(); i++) {
                    PowerUp powerUp = powerUps.get(i);
                    powerUp.move();

                    if (powerUp.getBounds().intersects(new Rectangle(playerX, 550, extendPaddleActive ? 150 : 100, 8))) {
                        activatePowerUp(powerUp.getType());
                        powerUps.remove(i);
                        i--;

                        // Mainkan efek suara saat power-up diambil
                        audioManager.playSoundEffect("assets/Sound/collect_power.wav");
                    } else if (powerUp.getBounds().y > 600) {
                        powerUps.remove(i);
                        i--;
                    }
                }

                if (totalBricks <= 0 && !levelComplete) {
                    levelUp();
                }
            }

            repaint();

            try {
                Thread.sleep(ballSpeed);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void activatePowerUp(String type) {
        powerUpTimer = System.currentTimeMillis();
        switch (type) {
            case "multiBall":
                // Gandakan bola dengan arah yang berbeda
                ArrayList<Ball> newBalls = new ArrayList<>();
                for (Ball ball : balls) {
                    // Membuat bola baru dengan arah yang berbeda
                    Ball newBall = new Ball(ball.getX(), ball.getY(), -ball.getXDir(), -ball.getYDir());
                    newBalls.add(newBall);
                }
                balls.addAll(newBalls); // Tambahkan semua bola baru ke daftar bola
                break;
            case "extendPaddle":
                extendPaddleActive = true;
                break;
            case "doubleScore":
                doubleScoreActive = true;
                break;
            case "quickPaddle":
                quickPaddleActive = true;
                paddleSpeed = 40;
        }
        // Store the message to display it on screen
        powerUpMessage = type + " activated!";
    }

    private void deactivatePowerUp() {
        extendPaddleActive = false;
        doubleScoreActive = false;
        quickPaddleActive = false;
        paddleSpeed = 20;
        powerUpTimer = 0;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if (playerX >= 600) {
                playerX = 600;
            } else {
                moveRight();
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            if (playerX <= 10) {
                playerX = 10;
            } else {
                moveLeft();
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (levelComplete) {
                startNextLevel();
            } else if (!play) {
                resetGame(true);
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            boolean allBallsLost = true;
            for (Ball ball : balls) {
                if (ball.getY() <= 560) {
                    allBallsLost = false; // Masih ada bola yang aktif
                    break;
                }
            }
        
            if (allBallsLost) {
                container.showCard("MainMenu");
                audioManager.playBGM("assets/Sound/menu_bgm.wav", true);
            }
        }
    }

    private void resetGame(boolean resetPlayerData) {
        play = true;
        gameOverSoundPlayed = false;
    
        // Reset daftar bola
        balls.clear();
        balls.add(new Ball(120, 350, -1, -2)); // Tambahkan bola awal
    
        // Reset posisi paddle
        playerX = 310;
    
        // Reset atribut permainan berdasarkan parameter
        if (resetPlayerData) {
            score = 0;
            level = 1;
        }
    
        ballSpeed = 8;
    
        // Reset peta brick
        map = new MapGenerator(3, 7);
        totalBricks = 3 * 7;
    
        // Hapus semua power-up
        powerUps.clear();
        deactivatePowerUp();
    
        // Mainkan BGM
        audioManager.playBGM("assets/Sound/gameplay_bgm.wav", true);
    
        repaint();
    }
    
    
    public void moveRight() {
        play = true;
        playerX += paddleSpeed;
    }

    public void moveLeft() {
        play = true;
        playerX -= paddleSpeed;
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}
}