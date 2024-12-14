public class Ball {
    private int x, y, xDir, yDir;

    public Ball(int x, int y, int xDir, int yDir) {
        this.x = x;
        this.y = y;
        this.xDir = xDir;
        this.yDir = yDir;
    }

    public void move() {
        x += xDir;
        y += yDir;

        if (x < 0 || x > 670) {
            xDir = -xDir;
        }
        if (y < 0) {
            yDir = -yDir;
        }
    }

    public void reverseXDir() {
        xDir = -xDir;
    }

    public void reverseYDir() {
        yDir = -yDir;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setY (int y) {
        this.y = y;
    }

    public int getXDir() {
        return xDir;
    }

    public int getYDir() {
        return yDir;
    }
}
