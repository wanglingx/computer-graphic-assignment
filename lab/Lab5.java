import javax.swing.*;
import java.awt.*;

public class lab5_63050212 extends JPanel implements Runnable {
    double circleMove = 50;
    double xO = 50;
    double squareRotate = 0;
    double x = 0.0, y = 550.0;
    double yR = 460;
    double velocity = 100;
    double angle = 90;
    double velocityY;
    
        public static void main(String[] args) {
        lab5_63050212 m = new lab5_63050212();
        JFrame f = new JFrame();
        f.add(m);
        f.setTitle("Lab5_63050212");
        f.setSize(600, 600);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
        new Thread(m).start();
    }

    @Override
    public void run() { // Thread
        double startTime = System.currentTimeMillis();
        double lastTime  = System.currentTimeMillis();
        double currentTime, elapsedTime;
        velocityY = velocity * Math.sin(Math.toRadians(angle));
        while (true) {
            // control time
            currentTime = System.currentTimeMillis();
            elapsedTime = currentTime - lastTime;
            lastTime = currentTime;

            // update logic
            // move forwaed backward 100 pixel per second
            if (xO == 50.0) {
                circleMove += 100.0 * elapsedTime / 1000.0;
                if (circleMove >= 535.0)
                    xO = 535;
            }
            if (xO == 535.0) {
                circleMove -= 100. * elapsedTime / 1000.0;
                if (circleMove <= 50.09)
                    xO = 50;
            }

            squareRotate += 0.5 * elapsedTime / 1000.0;

            //move rectagle 
            if ((currentTime - startTime) / 1000 >= 3) {
                if (yR == 460) {
                    y -= velocityY * elapsedTime / 1000.0;
                    if (y <= 0) {
                        yR = 0;
                    }
                }
                if (yR == 0) {
                    y += velocityY * elapsedTime / 1000.0;
                    if (y >= 460) {
                        yR = 460;
                    }
                }
            }
            // display
            repaint();
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, 600, 600);
        g2.setColor(Color.BLACK);
        //g2.translate(circleMove, 0);
        //g2.translate(-circleMove, 0);

        //1.draw midpoint circle run!!!
        fillmidPointCircle(g2, (int) circleMove, 60, 50, 2, Color.decode("#96CEB4"));
        
        //2. Rect run!!!!
        //g2.rotate(squareRotate,35,35);
        g2.setColor(Color.decode("#FBCAFF"));
        g2.fillRect(10, (int) y, 100, 100);
        
        //3.Rect Rotate
        g2.rotate(squareRotate, 300, 300);
        g2.setColor(Color.decode("#BAABDA"));
        g2.fillRect(200, 200, 200, 200);
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

    public void fillmidPointCircle(Graphics g, int xc, int yc, int r, int size , Color c) {
        for (int i = 0; i <= r; i++) {
            g.setColor(c);
            midPointCircle(g, xc, yc, i, size);
        }
    }
}
