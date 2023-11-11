import javax.swing.*;
import java.awt.*;

public class Lab4_63050212 extends JPanel {          
    public static void main(String[] args) {
        Lab4_63050212 m = new Lab4_63050212();
        JFrame f = new JFrame();
        f.add(m);
        f.setTitle("Lab4_63050212");
        f.setSize(600, 600);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }

    @Override
    public void paintComponent(Graphics g) {
        midPointCircle(g, 450, 200, 100, 2);
        midPointEllipse(g, 300, 400, 200, 100, 2);
        g.setColor(Color.decode("#96CEB4"));
        fillmidPointCircle(g, 150, 200, 100, 2);
    }

    public void plot(Graphics g, int x, int y, int size) {
        g.fillRect(x, y, size, size);
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

    public void fillmidPointCircle(Graphics g, int xc, int yc, int r, int size) {
        for (int i = 0; i <= r; i++) {
            midPointCircle(g, xc, yc, i, size);
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
}
