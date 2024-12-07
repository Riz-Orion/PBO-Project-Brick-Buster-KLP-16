import java.awt.*;

public class PowerUp {
    private int x, y;
    private int width, height;
    private String type; // Jenis power-up (e.g., "doubleBall", "extendPaddle", "doubleScore")

    public PowerUp(int x, int y, String type) {
        this.x = x;
        this.y = y;
        this.width = 20;
        this.height = 20;
        this.type = type;
    }

    public void draw(Graphics g) {
        g.setColor(Color.cyan);
        g.fillRect(x, y, width, height);

        g.setColor(Color.black);
        g.setFont(new Font("serif", Font.BOLD, 10));
        if (type.equals("multiBall")) {
            g.drawString("MB", x + 5, y + 15);
        } else if (type.equals("extendPaddle")) {
            g.drawString("EP", x + 5, y + 15);
        } else if (type.equals("doubleScore")) {
            g.drawString("DS", x + 5, y + 15);
        } else if (type.equals("quickPaddle")) {
            g.drawString("QP", x + 5, y + 15);
        }
    }

    public void move() {
        y += 1; // Power-up jatuh ke bawah
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public String getType() {
        return type;
    }
}
