public class Ball extends AbstractBall {

    public Ball(int x, int y, int xDir, int yDir) {
        super(x, y, xDir, yDir);
    }

    @Override
    public void move() {
        x += xDir;
        y += yDir;

        if (x < 0 || x > 670) {
            reverseXDir();
        }
        if (y < 0) {
            reverseYDir();
        }
    }
}
