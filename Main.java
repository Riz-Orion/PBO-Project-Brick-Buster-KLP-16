import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Brick Buster");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setSize(700, 600);

        MainContainer container = new MainContainer();
        frame.add(container);

        frame.setVisible(true);
    }
}