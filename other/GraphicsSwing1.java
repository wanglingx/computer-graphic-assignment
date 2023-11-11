import javax.swing.*;
import java.awt.*;

class GraphicsSwing1 extends JPanel {

    private int x;
    private int y;

    public static void main(String[] args) {
        GraphicsSwing1 m = new GraphicsSwing1();

        JFrame f = new JFrame();
        f.add(m);
        f.setTitle("first Swing");
        f.setSize(600, 600);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }

    @Override
    public void paintComponent(Graphics g) {
        g.drawString("Hello", 40, 40);
        g.setColor(Color.BLUE);
        g.fillRect(130, 30, 100, 80);
        g.drawOval(30, 130, 50, 60);
        g.setColor(Color.RED);
        g.drawLine(0, 0, 300, 30);
        g.fillOval(130, 130, 50, 50);
        g.drawArc(30, 200, 40, 50, 90, 60);
        g.fillArc(30, 130, 40, 50, 180, 40);
        g.setColor(Color.PINK);
        g.drawLine(0, 0, 600, 600);
        plot(g, 150, 450);
    }

    public void plot(Graphics g, int x, int y) {
        this.x = x;
        this.y = y;
        for (x = this.x; x < 250; x += 20) {
            for (y = this.y; y < 550; y += 50) {
                int R = (int) (Math.random() * 256);
                int G = (int) (Math.random() * 256);
                int B = (int) (Math.random() * 256);
                Color randomColor = new Color(R, G, B);
                g.setColor(randomColor);
                g.fillOval(x, y, 4, 4);
            }
        }
    }
}
