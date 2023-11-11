import javax.swing.*;
import java.awt.*;

class GraphicsSwing2 extends JPanel {
    public static void main(String[] args) {
        GraphicsSwing2 m = new GraphicsSwing2();

        JFrame f = new JFrame();
        f.add(m);
        f.setTitle("first Swing");
        f.setBackground(Color.black);
        f.setSize(600, 600);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }

    @Override
    public void paintComponent(Graphics g) {
        // naiveLine(g, 100, 100, 400, 200);
        // naiveLine(g, 400, 200, 100, 100);
        // naiveLine(g, 100, 100, 200, 400);

        // Exsercise 2.2-2.4
        // g.setColor(Color.CYAN);
        // ddaLine(g, 100, 100, 400, 200);
        // g.setColor(Color.ORANGE);
        // ddaLine(g, 400, 200, 100, 100);
        // g.setColor(Color.YELLOW);
        // ddaLine(g, 100, 100, 200, 400);

        // Exsercise 3.2-3.4
        // g.setColor(Color.RED);
        // bresenhamLine(g, 100, 100, 400, 200);
        // g.setColor(Color.GREEN);
        // bresenhamLine(g, 400, 200, 100, 100);
        g.setColor(Color.decode("#96CEB4"));
        bresenhamLine(g, 100, 100, 200, 400);

    }

    // Exwecise 1.1
    public void naiveLine(Graphics g, int x1, int y1, int x2, int y2) {
        double dx = x2 - x1;
        double dy = y2 - y1;

        double m = dy / dx;
        double b = y1 - m * x1;

        for (int x = x1; x <= x2; x++) {
            int y = (int) Math.round(m * x + b);
            plot(g, x, y, 3);

        }
    }

    // Exsercise 2.1
    public void ddaLine(Graphics g, int x1, int y1, int x2, int y2) {
        double dx = x2 - x1;
        double dy = y2 - y1;

        double x, y;
        double m = dy / dx;

        if (m <= 1 && m >= 0) {
            y = Math.min(y1, y2);
            for (x = Math.min(x1, x2); x <= Math.max(x1, x2); x++) {
                y = y + m;
                plot(g, (int) x, (int) y, 3);
            }
        }

        else if (m >= -1 && m < 0) {
            y = Math.min(y1, y2);
            for (x = Math.max(x1, x2); x >= Math.min(x1, x2); x--) {
                y = y - m;
                plot(g, (int) x, (int) y, 3);
            }
        }

        else if (m > 1) {
            x = Math.min(x1, x2);
            for (y = Math.min(y1, y2); y <= Math.max(y1, y2); y++) {
                x = (x + 1 / m);
                plot(g, (int) x, (int) y, 3);
            }
        }

        else {
            x = Math.min(x1, x2);
            for (y = Math.max(y1, y2); y >= Math.min(y1, y2); y--) {
                x = (x + 1 / m);
                plot(g, (int) x, (int) y, 3);
            }
        }
    }
    

    public void bresenham(Graphics g, int x1, int y1, int x2, int y2) // bresenham faster than ddA 1.5
    {
        int dx = x2 - x1;
        int dy = y2 - y1;

        int D = 2 * dy - dx;
        int y = y1;
        for (int x = x1; x <= x2; x++) {
            plot(g, x, y, 2);
            if (D >= 0) {
                y++;
                D = D - 2 * dx;
            }
            D = D + 2 * dy;
        }
    }

    // Exsercise 3.1
    public void bresenhamLine(Graphics g, int x1, int y1, int x2, int y2) 
    {
        int dx = Math.abs(x2 - x1);
        int dy = Math.abs(y2 - y1);

        int sx = (x1 < x2) ? 1 : -1;
        int sy = (y1 < y2) ? 1 : -1;
        boolean isSwap = false;

    if (dy > dx) {
            int d = dx;
                dx = dy;
                dy = d;
            isSwap = true;
        }
        int D = 2 * dy - dx;

        int x = x1;
        int y = y1;

        for (int i = 1; i <= dx; i++) {
            plot(g, x, y, 3);
            if (D >= 0) {
                if (isSwap)
                    x += sx;
                else
                    y += sy;

                D -= 2 * dx;
            }
            if (isSwap)
                y += sx;
            else
                x += sy;  

            D += 2 * dy;
        }
    }

    // public int swap(int dx, int dy) {
    //     int d = dx;
    //     dx = dy;
    //     dy = d;
    //     return this.dx;
    // }

    public void plot(Graphics g, int x, int y, int size) {
        g.fillRect(x, y, size, size);
    }
}