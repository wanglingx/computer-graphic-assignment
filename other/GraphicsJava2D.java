//import javax.swing.*;
import java.awt.image.*;
import java.awt.*;
import java.util.*;
class Tool2D
{
    private int x, y;
    public void plotRGB(Graphics g, int x, int y) 
    {
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
    
    public void plot(Graphics g, int x, int y, int size) 
    {
        g.fillRect(x, y, size, size);
    }

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
    public void ddaLine(Graphics g, int x1, int y1, int x2, int y2) 
    {
        double dx = x2 - x1;
        double dy = y2 - y1;

        double x, y;
        double m = dy / dx;

        if (m <= 1 && m >= 0) 
        {
            y = Math.min(y1, y2);
            for (x = Math.min(x1, x2); x <= Math.max(x1, x2); x++) 
            {
                y = y + m;
                plot(g, (int) x, (int) y, 3);
            }
        }

        else if (m >= -1 && m < 0) 
        {
            y = Math.min(y1, y2);
            for (x = Math.max(x1, x2); x >= Math.min(x1, x2); x--) 
            {
                y = y - m;
                plot(g, (int) x, (int) y, 3);
            }
        }

        else if (m > 1) 
        {
            x = Math.min(x1, x2);
            for (y = Math.min(y1, y2); y <= Math.max(y1, y2); y++) 
            {
                x = (x + 1 / m);
                plot(g, (int) x, (int) y, 3);
            }
        }

        else {
            x = Math.min(x1, x2);
            for (y = Math.max(y1, y2); y >= Math.min(y1, y2); y--) 
            {
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
        for (int x = x1; x <= x2; x++) 
        {
            plot(g, x, y, 2);
            if (D >= 0) 
            {
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

    public void midPointCircle(Graphics g, int xc, int yc, int r, int size) {
        int x = 0;
        int y = r;
        int D = 1 - r;

        while (x <= y) {
            plot(g, x + xc, y + yc, size);
            plot(g, x + xc, -y + yc, size);
            plot(g, -x + xc, y + yc, size);
            plot(g, -x + xc, -y + yc, size);
            plot(g, y + xc, x + yc, size);
            plot(g, y + xc, -x + yc, size);
            plot(g, -y + xc, x + yc, size);
            plot(g, -y + xc, -x + yc, size);

            x += 1;
            D = D + 2 * x + 1;

            if (D >= 0) {
                y -= 1;
                D = D - 2 * y;
            }
        }
    }

    public void midPointEllipse(Graphics g, int xc, int yc, int a, int b, int size) {
        // REGION 1
        int x = 0;
        int y = b;
        int D = Math.round(b * b - a * a * b + a * a / 4);

        while (b * b * x <= a * a * y) {
            plot(g, x + xc, y + yc, size);
            plot(g, x + xc, -y + yc, size);
            plot(g, -x + xc, y + yc, size);
            plot(g, -x + xc, -y + yc, size);
            x++;
            D = D + 2 * b * b * x + b * b;
            if (D >= 0) {
                y--;
                D = D - 2 * a * a * y;
            }
        }
        // REGION2
        x = a;
        y = 0;
        D = Math.round(a * a - b * b * a + b * b / 4);

        while (b * b * x >= a * a * y) {
            plot(g, x + xc, y + yc, size);
            plot(g, x + xc, -y + yc, size);
            plot(g, -x + xc, y + yc, size);
            plot(g, -x + xc, -y + yc, size);
            y++;
            D = D + 2 * a * a * y + a * a;
            if (D >= 0) {
                x--;
                D = D - 2 * b * b * x;
            }
        }
    }

    public void fillmidPointCircle(Graphics g, int xc, int yc, int r, int size) {
        for (int i = 0; i <= r; i++) {
            midPointCircle(g, xc, yc, i, size);
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
}