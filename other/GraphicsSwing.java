//63050159 PHONGSAKORN KAWHAOLAI
import javax.swing.*;
import java.awt.*;

public class GraphicsSwing extends JPanel
{
    public static void main(String[] args)
    {
        // System.out.println("Hello world"); //for test

        GraphicsSwing m = new GraphicsSwing(); //Create panel from JPanel

        // Varaible v = new Varaible();

        JFrame f = new JFrame(); //Create frame
        f.add(m);
        f.setTitle("159 Phongsakorn Swing");
        f.setSize(600,600);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }

    // class Varaible {
    //     int dx,dy;
    // }

    @Override
    public void paintComponent(Graphics g)
    {
        //lab2
        // plot(g, 200, 200, 10);

        //naiveLine TEST
        // g.setColor(Color.RED);
        // naiveLine(g, 100, 100, 400, 200);
        // naiveLine(g, 400, 200, 100 , 100);
        // naiveLine(g, 100, 100, 200, 400);

        //ddaLine TEST
//        g.setColor(Color.GREEN);
//        ddaLine(g, 100, 100, 400, 200);
//        ddaLine(g, 400, 200, 100, 100);
//        ddaLine(g, 100, 100, 200, 400);
        
        //bresenHamOldVersion TEST
        // g.setColor(Color.ORANGE);
        // bresenhamLineOldVersion(g, 100, 100, 400, 200);
        // bresenhamLineOldVersion(g, 400, 200, 100, 100);
        // bresenhamLineOldVersion(g, 100, 100, 200, 400);

        // // bresenHam TEST
        // g.setColor(Color.BLUE);
        // bresenhamLine(g, 100, 100, 400, 200);
        // bresenhamLine(g, 400, 200, 100, 100);
         bresenhamLine(g, 100, 100, 200, 400);
    }

    public void naiveLine(Graphics g, int x1, int y1, int x2, int y2)
    {
        double dx = x2 - x1;
        double dy = y2 - y1;

        double m = dy / dx;
        double b = y1 - m * x1;

        for(int x = x1; x <= x2; x++)
        {
            int y = (int) Math.round(m * x + b);
            plot(g, x, y, 1);
        }
    }

    public void ddaLine(Graphics g, int x1, int y1, int x2, int y2)
    {
        double dx = x2 - x1;
        double dy = y2 - y1;

        double x, y;
        double m = dy / dx;
        
        if (m <= 1 && m >= 0) // o between 1
        {
            y = Math.min(y1, y2);
            for(x = Math.min(x1, x2); x <= Math.max(x1, x2); x++)
            {
                y += m;
                plot(g, (int)x, (int)y, 1);
            }            
        }
        else if (m >= -1 && m < 0) // -0 between -1
        {
            y = Math.min(y1, y2);
            for (x = Math.max(x1, x2); x >= Math.min(x1, x2); x--)
            {
                y -= m;
                plot(g, (int)x, (int)y, 1);
            }
        }
        else if (m > 1)
        {
            x = Math.min(x1, x2);
            for (y = Math.min(y1, y2); y <= Math.max(y1, y2); y++)
            {
                x += 1/m;
                plot(g, (int)x, (int)y, 1);
            }
        }
        else 
        {
            x = Math.min(x1, x2);
            for (y = Math.max(y1, y2); y >= Math.min(y1, y2); y--)
            {
                x = x + 1/m;
                plot(g, (int)x, (int)y, 1);
            }
        }
    }
    
    public void bresenhamLine(Graphics g, int x1, int y1, int x2, int y2)
    {
        
        int dx = Math.abs(x2 - x1);
        int dy = Math.abs(y2 - y1);

        int sx = (x1 < x2) ? 1 : -1;
        int sy = (y1 < y2) ? 1 : -1;
        boolean isSwap = false;
        
        if(dy > dx)
        {
            // swap(dx, dy);
            int k = dx;
            dx = dy;
            dy = k;       
            isSwap = true;
        }

        int D = 2 * dy - dx;
        
        int x = x1;
        int y = y1;
        
        for (int i=1; i <= dx; i++)
        {
            plot(g, x, y, 1);
            if (D >= 0)
            {
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

    //origlnal bresenhamLine fix speed fastttt and not use multiple and dive (60 year to present use) more than ddaLine
    public void bresenhamLineOldVersion(Graphics g, int x1, int y1, int x2, int y2)
    {
        int dx = x2 - x1;
        int dy = y2 - y1;

        int Dk = 2 * dy - dx;

        int y = y1;

        for(int x = x1; x <= x2; x++)
        {
            plot (g, x, y, 2);
            if (Dk >= 0)
            {
                y++;
                Dk = Dk - 2 * dx;
            }
            Dk = Dk + 2 * dy;
        }
    }

    public void swap(int dx, int dy)
    {
        int k = dx;
        dx = dy;
        dy = k;       
    }
    
    public void plot(Graphics g, int x, int y,int size)
    {
        g.fillRect(x,y,size,size);
    }
}
