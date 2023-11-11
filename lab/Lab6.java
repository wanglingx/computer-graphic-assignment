import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;

public class lab6_63050212 extends JPanel implements Runnable
{
    public static void main(String[] args) 
    {
        lab6_63050212 m = new lab6_63050212();
        JFrame f = new JFrame();
        f.add(m);
        f.setTitle("Lab6_63050212");
        f.setSize(600, 600);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
        new Thread(m).start();
    }

    @Override
    public void run() {
        double lastTime = System.currentTimeMillis();
        double currentTime, elapsedTime;
        while (true) {
            currentTime = System.currentTimeMillis();
            elapsedTime = currentTime - lastTime;
            lastTime = currentTime;

            repaint();
        }
    }
    @Override
    public void paintComponent(Graphics g) 
    {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.decode("#F4DFBA"));
        g2.fillRect(0, 0, 600, 600);
           
        /*
         * [1,0,200][2,0,0][1,0,-200]
         * [0,1,200][0,2,0][0,1,-200]
         * [0,0,1  ][0,0,1][0,0,1   ]  
         * 
         * = [2,0,200] [1,0,-200]
         *   [0,2,200] [0,1,-200]
         *   [0,0,1  ] [0,0,1   ]
         * 
         * =    [2,0,-400]
         *      [0,2,-400]
         *      [0,0, 1  ]
         * the square above to scale x2
         */

        //original square
        g2.setColor(Color.decode("#625757"));
        g2.fillRect(200, 200, 200, 200);

        //Example lab
        //g2.setTransform(new AffineTransform(3, 0, 0, 3, -400, -400)); //zoom x3
        g2.setTransform(new AffineTransform(1, 0, 0, 1, 100, 100));
        g2.setColor(Color.decode("#C3B091"));
        g2.fillRect(200, 200, 200, 200);

        // //Task 1 transform above to rotate 30 degree counter-clockwise its centre
        AffineTransform transform = g2.getTransform();
        transform.rotate(Math.toRadians(-30), 200, 200);
        g2.setTransform(transform);
        g2.setColor(Color.decode("#CA965C"));
        g2.fillRect(200, 200, 200, 200);

        // //Task 2 transform the square above to scale x2 around its top-left corner and tranlate -50 pixels on X and 50 on Y
        g2.setTransform(new AffineTransform(2, 0, 0, 2,-400,-400));
        g2.translate(-50,50);
        g2.setColor(Color.decode("#A0937D"));
        g2.fillRect(200, 200, 200, 200);

    }
}
