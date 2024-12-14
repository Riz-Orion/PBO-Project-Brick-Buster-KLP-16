public abstract class AbstractBall {
    protected int x, y, xDir, yDir;

    public AbstractBall(int x, int y, int xDir, int yDir) {
        this.x = x;
        this.y = y;
        this.xDir = xDir;
        this.yDir = yDir;
    }

    public abstract void move();

    public void reverseXDir() {
        xDir = -xDir;
    }

    public void reverseYDir() {
        yDir = -yDir;
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public int getXDir() { return xDir; }
    public int getYDir() { return yDir; }
    public void setY(int y) { this.y = y; }
}
