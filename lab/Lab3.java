import java.awt.*;
import java.awt.image.*;
import javax.swing.*;
import java.util.*;

public class Lab3_63050212 extends JPanel {
    private final static Color RED_WINE = new Color(153, 0, 0);
    private final static Color GREEAN_PASTEL_COLOR = Color.decode("#96CEB4");
    public static void main(String[] args) {

        Lab3_63050212 m = new Lab3_63050212();
        JFrame f = new JFrame();
        f.add(m);
        f.setTitle("Lab3_63050212");
        // f.setBackground(Color.decode("#96CEB4"));
        f.setSize(600, 600);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }

    @Override
    public void paintComponent(Graphics g) {
        BufferedImage buffer = new BufferedImage(601, 601, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = buffer.createGraphics();
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, 600, 600);

        // plot
        g2.setColor(Color.decode("#EFDAD7"));
        plot(g2, 100, 400, 6);
        plot(g2, 250, 350, 6);
        plot(g2, 200, 100, 6);
        plot(g2, 550, 200, 6);

        // Exercise1 Task2
        g2.setColor(Color.decode("#AA14F0"));
        bezierCurve(g2, 100, 500, 200, 300, 400, 300, 500, 500);
        g2.setColor(RED_WINE);
        bezierCurve(g2, 100, 400, 250, 350, 200, 100, 550, 200);
        g2.setColor(Color.decode("#E3BEC6"));
        bezierCurve(g2, 200, 500, 500, 300, 100, 300, 400, 500);

        // Exercise2 Polygon
        g2.setColor(Color.decode("#D9D7F1"));
        int xPoly[] = { 150, 250, 325, 375, 400, 275, 100 };
        int yPoly[] = { 150, 100, 125, 225, 325, 375, 300 };
        Polygon poly = new Polygon(xPoly, yPoly, xPoly.length);
        // Polygon poly = new Polygon();
        // g2.setColor(Color.decode("#E5890A"));
        // poly.addPoint(150, 150);
        // poly.addPoint(250, 100);
        // poly.addPoint(325, 125);
        // poly.addPoint(375, 225);
        // poly.addPoint(400, 325);
        // poly.addPoint(275, 375);
        // poly.addPoint(100, 300);
        g2.drawPolygon(poly);

        // Exercise3 Task2
        buffer = floodFill(buffer, 200, 150, Color.BLACK, GREEAN_PASTEL_COLOR);

        g.drawImage(buffer, 0, 0, null);

    }

    // Exercise1 Task1
    public void bezierCurve(Graphics g, int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4) {
        for (int i = 0; i <= 1000; i++) { // prevent to cutting point
            double t = i / 1000.0;
            int x = (int) (Math.pow((1 - t), 3) * x1 +
                    3 * t * Math.pow((1 - t), 2) * x2 +
                    3 * t * t * (1 - t) * x3 +
                    t * t * t * x4);

            int y = (int) (Math.pow((1 - t), 3) * y1 +
                    3 * t * Math.pow((1 - t), 2) * y2 +
                    3 * t * t * (1 - t) * y3 +
                    t * t * t * y4);

            plot(g, x, y, 3);
        }
    }

    // Exercise3 Task1
    public BufferedImage floodFill(BufferedImage m, int x, int y, Color targetColor, Color replacementColor) {
        Queue<Point> q = new LinkedList<Point>();
        Graphics2D g2 = m.createGraphics();

        g2.setColor(replacementColor);
        plot(g2, x, y, 1);
        q.add(new Point(x, y));

        while (!q.isEmpty()) {
            Point p = q.poll();
            // s
            if (p.y < 600 && new Color(m.getRGB(p.x, p.y + 1)).equals(targetColor)) {
                g2.setColor(replacementColor);
                plot(g2, p.x, p.y + 1, 1);
                q.add(new Point(p.x, p.y + 1));
            }
            // n
            if (p.y > 0 && new Color(m.getRGB(p.x, p.y - 1)).equals(targetColor)) {
                g2.setColor(replacementColor);
                plot(g2, p.x, p.y - 1, 1);
                q.add(new Point(p.x, p.y - 1));
            }
            // e
            if (p.x < 600 && new Color(m.getRGB(p.x + 1, p.y)).equals(targetColor)) {
                g2.setColor(replacementColor);
                plot(g2, p.x + 1, p.y, 1);
                q.add(new Point(p.x + 1, p.y));
            }
            // w
            if (p.x > 0 && new Color(m.getRGB(p.x - 1, p.y)).equals(targetColor)) {
                g2.setColor(replacementColor);
                plot(g2, p.x - 1, p.y, 1);
                q.add(new Point(p.x - 1, p.y));
            }
        }
        return m;
    }

    public void plot(Graphics g, int x, int y, int size) {
        g.fillRect(x, y, size, size);
    }
    
}
