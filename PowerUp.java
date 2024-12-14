import java.awt.*;

public interface PowerUp {
    void move();
    Rectangle getBounds();
    void draw(Graphics g);
    String getType();
}
