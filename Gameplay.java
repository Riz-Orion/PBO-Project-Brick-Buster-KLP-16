import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Gameplay extends JPanel implements KeyListener, Runnable {
    private boolean play = false;
    private boolean levelComplete = false; // Status untuk level selesai
    private int score = 0;
    private int level = 1;
    private int totalBricks;
    private int playerX = 310;
    private int ballPosX = 120;
    private int ballPosY = 350;
    private int ballXDir = -1;
    private int ballYDir = -2;
    private int ballSpeed = 8;

    private MainContainer container;
    private MapGenerator map;
    private String playerName;

    public Gameplay(MainContainer container, String playerName) {
        this.container = container;
        this.playerName = playerName;
        map = new MapGenerator(3, 7);
        totalBricks = 3 * 7;
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);

        Thread gameThread = new Thread(this);
        gameThread.start();
    }

    public void paint(Graphics g) {
        // Background
        g.setColor(new Color(139, 69, 19));
        g.fillRect(1, 1, 692, 592);

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
        g.setColor(Color.green);
        g.fillRect(playerX, 550, 100, 8);

        // Ball
        g.setColor(Color.yellow);
        g.fillOval(ballPosX, ballPosY, 20, 20);

        // Level Complete Notification
        if (levelComplete) {
            g.setColor(Color.green);
            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("Level " + level + " Complete!", 230, 300);

            g.setFont(new Font("serif", Font.BOLD, 20));
            g.drawString("Press Enter to Continue", 230, 350);
            return; // Jangan lanjutkan rendering jika level selesai
        }

        // Win/Lose Message
        if (ballPosY > 560) {
            play = false;
            ballXDir = 0;
            ballYDir = 0;
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
        repaint();
    }

    private void startNextLevel() {
        levelComplete = false; // Reset status level selesai
        level++;
        ballSpeed -= 1;
        ballSpeed = Math.max(ballSpeed, 4);

        ballPosX = 120;
        ballPosY = 350;
        ballXDir = -1;
        ballYDir = -2;
        playerX = 310;

        int rows = 3 + level;
        int cols = 7 + level;
        map = new MapGenerator(rows, cols);
        totalBricks = rows * cols;

        play = true;
        repaint();
    }

    @Override
    public void run() {
        while (true) {
            if (play) {
                if (new Rectangle(ballPosX, ballPosY, 20, 20).intersects(new Rectangle(playerX, 550, 100, 8))) {
                    ballYDir = -ballYDir;
                }

                A: for (int i = 0; i < map.map.length; i++) {
                    for (int j = 0; j < map.map[0].length; j++) {
                        if (map.map[i][j] > 0) {
                            int brickX = j * map.brickWidth + 80;
                            int brickY = i * map.brickHeight + 50;
                            int brickWidth = map.brickWidth;
                            int brickHeight = map.brickHeight;

                            Rectangle rect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
                            Rectangle ballRect = new Rectangle(ballPosX, ballPosY, 20, 20);
                            Rectangle brickRect = rect;

                            if (ballRect.intersects(brickRect)) {
                                map.setBrickValue(0, i, j);
                                totalBricks--;
                                score += 5;

                                if (ballPosX + 19 <= brickRect.x || ballPosX + 1 >= brickRect.x + brickRect.width) {
                                    ballXDir = -ballXDir;
                                } else {
                                    ballYDir = -ballYDir;
                                }
                                break A;
                            }
                        }
                    }
                }

                if (totalBricks <= 0 && !levelComplete) {
                    levelUp();
                }

                ballPosX += ballXDir;
                ballPosY += ballYDir;

                if (ballPosX < 0) {
                    ballXDir = -ballXDir;
                }
                if (ballPosY < 0) {
                    ballYDir = -ballYDir;
                }
                if (ballPosX > 670) {
                    ballXDir = -ballXDir;
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
                resetGame();
            }
        }
    }

    private void resetGame() {
        play = true;
        ballPosX = 120;
        ballPosY = 350;
        ballXDir = -1;
        ballYDir = -2;
        playerX = 310;
        score = 0;
        level = 1;
        ballSpeed = 8;
        map = new MapGenerator(3, 7);
        totalBricks = 3 * 7;

        repaint();
    }

    public void moveRight() {
        play = true;
        playerX += 20;
    }

    public void moveLeft() {
        play = true;
        playerX -= 20;
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}
}