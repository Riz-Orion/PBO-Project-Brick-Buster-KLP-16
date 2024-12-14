import java.awt.*;

public class PowerUpItem implements PowerUp {
    private int x, y;
    private String type;

    public PowerUpItem(int x, int y, String type) {
        this.x = x;
        this.y = y;
        this.type = type;
    }

    @Override
    public void move() {
        y += 1; // Power-up turun ke bawah
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x, y, 20, 20);
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.magenta);
        g.fillRect(x, y, 20, 20);
        g.setColor(Color.white);
        g.setFont(new Font("Serif", Font.BOLD, 10));
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

    @Override
    public String getType() {
        return type;
    }
}
