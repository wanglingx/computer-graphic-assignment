//Assignment1_63050188_63050212
import java.awt.*;
import java.awt.image.*;
import java.util.logging.Logger;
import javax.swing.*;
import java.util.*;

class Tiger extends JPanel 
{
    private final static Color Cream = Color.decode("#FFEDDB");
    private final static Color BLACK = Color.decode("#000000");
    private final static Color Creamwhite = Color.decode("#FFF5E1");//wattle
    private final static Color goldsand = Color.decode("#EAB595"); // nose
    private final static Color Orangegy = Color.decode("#F98404"); // body/dark og
    private final static Color Creamorange = Color.decode("#FFD07F");//body/light og
    private final static Color Green_bg = Color.decode("#2d9a8f");

    public static void main(String[] args) 
    {
        Tiger m = new Tiger();
        JFrame f = new JFrame();
        f.add(m);
        f.setTitle("TIGER");
        f.setBackground(Color.WHITE);
        f.setSize(600, 600);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }

    @Override
    public void paintComponent(Graphics g) 
    {
        BufferedImage buffer = new BufferedImage(601, 601, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = buffer.createGraphics();
        Logger log = Logger.getLogger("com.api.jar");

        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, 600, 600);

        // Heads
        g2.setColor(Color.BLACK);
        bezierCurve(g2, 190, 252, 214, 181, 333, 158, 388, 217);// upper
        bezierCurve(g2, 388, 217, 421, 266, 422, 353, 332, 385);// right
        bezierCurve(g2, 190, 253, 173, 288, 174, 353, 249, 383);// left
        bezierCurve(g2, 281, 275, 335, 320, 350, 230, 395, 235);// rCheek
        bezierCurve(g2, 253, 275, 225, 320, 205, 260, 184, 273);// lcheek
        bezierCurve(g2, 249, 383, 252, 401, 321, 401, 332, 385);// chin

        // Moustache
        plot(g2, 300, 267, 3);
        plot(g2, 310, 270, 3);
        plot(g2, 305, 276, 3);
        plot(g2, 318, 276, 3);
        plot(g2, 320, 267, 3);
        
        plot(g2, 235, 273, 3);
        plot(g2, 225, 270, 3);
        plot(g2, 230, 280, 3);
        plot(g2, 220, 276, 3);
        plot(g2, 213, 271, 3);

        g2.setColor(Cream);
        bezierCurve(g2, 317, 355, 333, 365, 335, 370, 332, 383);// rWattle
        bezierCurve(g2, 260, 370, 250, 370, 248, 374, 251, 385);// lWattle

        // eyes
        g2.setColor(Color.BLACK);
        bezierCurve(g2, 215, 240, 230, 230, 240, 241, 246, 244); // left
        bezierCurve(g2, 305, 244, 320, 235, 330, 230, 346, 230); // right
        g2.setColor(Creamorange);
        bezierCurve(g2, 255, 192, 230, 240, 270, 244, 235, 265);// eye block left
        bezierCurve(g2, 235, 265, 190, 265, 215, 237, 204, 230);
        bezierCurve(g2, 305, 193, 265, 240, 300, 268, 330, 256);// eye block right
        bezierCurve(g2, 305, 193, 360, 205, 376, 240, 330, 256);

        // nose
        g2.setColor(Color.BLACK);
        bezierCurve(g2, 250, 270, 265, 263, 270, 263, 285, 270);// up nose
        bezierCurve(g2, 250, 270, 267, 285, 267, 285, 285, 270);// down nose
        // bezierCurve(g2, 267, 281, 264, 287, 265, 290, 267, 293);// line

        // mount
        bezierCurve(g2, 267, 281, 267, 310, 225, 290, 240, 305);// left up
        bezierCurve(g2, 240, 305, 265, 355, 236, 370, 260, 370);// left side
        bezierCurve(g2, 267, 281, 267, 310, 306, 280, 315, 310);// right up
        bezierCurve(g2, 315, 310, 320, 330, 319, 370, 308, 370);// right side
        bezierCurve(g2, 260, 370, 270, 350, 299, 360, 308, 370);// down

        // ear
        g2.setColor(Cream);
        bezierCurve(g2, 394, 228, 500, 266, 425, 266, 408, 282);
        g2.setColor(Color.BLACK);
        bezierCurve(g2, 392, 220, 500, 266, 425, 266, 411, 282);// ear

        // Body
        bezierCurve(g2, 406, 313, 471, 351, 475, 440, 392, 490);// back
        bezierCurve(g2, 390, 488, 390, 513, 310, 520, 328, 451);// right foot
        bezierCurve(g2, 390, 490, 422, 456, 426, 431, 415, 415);// curve right
        bezierCurve(g2, 232, 376, 228, 423, 260, 460, 258, 487); // left side
        bezierCurve(g2, 320, 451, 350, 520, 255, 516, 258, 487); // left foot

        // feeth
        bresenhamLine(g2, 347, 502, 347, 512); // right nail1
        bresenhamLine(g2, 359, 504, 359, 513); // 2
        bresenhamLine(g2, 370, 503, 370, 512); // 3
        bresenhamLine(g2, 273, 502, 273, 512); // left nail1
        bresenhamLine(g2, 285, 504, 285, 513); // 2
        bresenhamLine(g2, 296, 503, 296, 513); // 3

        // tail
        bezierCurve(g2, 453, 380, 512, 390, 450, 250, 530, 290);// tail up
        bezierCurve(g2, 454, 400, 512, 410, 480, 290, 530, 310);// tail down
        bezierCurve(g2, 530, 290, 550, 302, 550, 308, 530, 310);// last tail

        // body/pattern
        g2.setColor(Color.BLACK);
        int xPoly1[] = { 265, 309, 345, 379, 345, 330, 298 };
        int yPoly1[] = { 403, 410, 407, 390, 418, 425, 415 };
        Polygon poly1 = new Polygon(xPoly1, yPoly1, xPoly1.length);
        g2.drawPolygon(poly1); // near Wattle

        int xPoly2[] = { 345, 379, 399, 425, 420, 425, 379 };
        int yPoly2[] = { 395, 370, 360, 325, 350, 370, 380 };
        Polygon poly2 = new Polygon(xPoly2, yPoly2, xPoly2.length);
        g2.drawPolygon(poly2); // near ear

        int xPoly3[] = { 355, 390, 410, 440, 445, 400 };
        int yPoly3[] = { 430, 425, 398, 395, 375, 395 };
        Polygon poly3 = new Polygon(xPoly3, yPoly3, xPoly3.length);
        g2.drawPolygon(poly3);// near tail

        int xPoly4[] = { 368, 378, 389, 418, 400, 380, 356 };
        int yPoly4[] = { 448, 445, 443, 420, 445, 455, 460 };
        Polygon poly4 = new Polygon(xPoly4, yPoly4, xPoly4.length);
        g2.drawPolygon(poly4);// right foot up

        int xPoly5[] = { 420, 425, 435, 451, 440, 425, 415 };
        int yPoly5[] = { 458, 455, 452, 420, 437, 446, 460 };
        Polygon poly5 = new Polygon(xPoly5, yPoly5, xPoly5.length);
        g2.drawPolygon(poly5);// beside tail

        int xPoly6[] = { 263, 278, 289, 330, 300, 275, 248 };
        int yPoly6[] = { 453, 455, 445, 435, 430, 446, 444 };
        Polygon poly6 = new Polygon(xPoly6, yPoly6, xPoly6.length);
        g2.drawPolygon(poly6);// left foot side

        int xPoly7[] = { 233, 240, 255, 270, 280, 250, 233 };
        int yPoly7[] = { 390, 400, 410, 430, 435, 420, 400 };
        Polygon poly7 = new Polygon(xPoly7, yPoly7, xPoly7.length);
        g2.drawPolygon(poly7);// left side

        // tail/pattern
        g2.fillOval(527, 295, 17, 17);
        bezierCurve(g2, 492, 289, 480, 300, 490, 330, 518, 307); // tail1
        bezierCurve(g2, 487, 300, 480, 320, 490, 350, 503, 320); // tail2
        bezierCurve(g2, 480, 360, 480, 365, 490, 390, 495, 350); // tail4
        bezierCurve(g2, 474, 375, 480, 380, 490, 385, 495, 350); // tail5

        // face/pattern
        int xPoly8[] = { 330, 345, 365, 360, 380, 385, 390, 360 };
        int yPoly8[] = { 355, 320, 305, 280, 290, 250, 305, 320 };
        Polygon poly8 = new Polygon(xPoly8, yPoly8, xPoly8.length);
        g2.drawPolygon(poly8); // right side

        int xPoly9[] = { 182, 200, 210, 230, 235, 210, 206 };
        int yPoly9[] = { 290, 300, 316, 318, 313, 300, 285 };
        Polygon poly9 = new Polygon(xPoly9, yPoly9, xPoly9.length);
        g2.drawPolygon(poly9);// left side

        int xPoly10[] = { 255, 260, 265 };
        int yPoly10[] = { 192, 225, 190 };
        Polygon poly10 = new Polygon(xPoly10, yPoly10, xPoly10.length);
        g2.drawPolygon(poly10);// hair1

        int xPoly11[] = { 270, 275, 280 };
        int yPoly11[] = { 189, 225, 186 };
        Polygon poly11 = new Polygon(xPoly11, yPoly11, xPoly11.length);
        g2.drawPolygon(poly11);// hair2

        int xPoly12[] = { 285, 290, 295 };
        int yPoly12[] = { 184, 220, 185 };
        Polygon poly12 = new Polygon(xPoly12, yPoly12, xPoly12.length);
        g2.drawPolygon(poly12);// hair3

        // paint head
        buffer = floodFill(buffer, 290, 250, Color.WHITE, Orangegy);

        // paint pattern cheek
        buffer = floodFill(buffer, 214, 307, Color.WHITE, BLACK); //left
        buffer = floodFill(buffer, 375, 299, Color.WHITE, BLACK);//right

        // paint Cheek
        buffer = floodFill(buffer, 216, 339, Color.WHITE, Cream);// left
        buffer = floodFill(buffer, 358, 349, Color.WHITE, Cream);// right

        // paint left eye
        buffer = floodFill(buffer, 223, 242, Color.WHITE, Creamorange);//left
        buffer = floodFill(buffer, 305, 210, Color.WHITE, Creamorange);//right

        // paint nose
        buffer = floodFill(buffer, 269, 273, Color.WHITE, goldsand);

        // paint mouth
        buffer = floodFill(buffer, 280, 329, Color.WHITE, goldsand);

        // paint ear
        buffer = floodFill(buffer, 422, 253, Color.WHITE, Creamorange);
        buffer = floodFill(buffer, 400, 225, Color.WHITE, BLACK);

        // paint Wattle
        buffer = floodFill(buffer, 281, 378, Color.WHITE, Creamwhite);

        // paint hair
        buffer = floodFill(buffer, 262, 196, Color.WHITE, BLACK);
        buffer = floodFill(buffer, 276, 195, Color.WHITE, BLACK);
        buffer = floodFill(buffer, 292, 189, Color.WHITE, BLACK);

        // paint body
        buffer = floodFill(buffer, 342, 443, Color.WHITE, Orangegy);
        buffer = floodFill(buffer, 416, 465, Color.WHITE, Orangegy);

        // paint tail
        buffer = floodFill(buffer, 470, 387, Color.WHITE, Orangegy);
        buffer = floodFill(buffer, 490, 348, Color.WHITE, Orangegy);
        buffer = floodFill(buffer, 512, 296, Color.WHITE, Orangegy);

        // paint tiger pattern
        buffer = floodFill(buffer, 252, 412, Color.WHITE, BLACK);
        buffer = floodFill(buffer, 304, 434, Color.WHITE, BLACK);
        buffer = floodFill(buffer, 332, 416, Color.WHITE, BLACK);
        buffer = floodFill(buffer, 408, 359, Color.WHITE, BLACK);
        buffer = floodFill(buffer, 392, 411, Color.WHITE, BLACK);
        buffer = floodFill(buffer, 379, 449, Color.WHITE, BLACK);
        buffer = floodFill(buffer, 432, 446, Color.WHITE, BLACK);
        buffer = floodFill(buffer, 496, 322, Color.WHITE, BLACK);
        buffer = floodFill(buffer, 482, 371, Color.WHITE, BLACK);
        
        //Background
        buffer = floodFill(buffer, 10, 10, Color.WHITE, Green_bg);

        log.info("paint body pass");
        g.drawImage(buffer, 0, 0, null);
    }

    public void plot(Graphics g, int x, int y, int size) 
    {
        g.fillRect(x, y, size, size);
    }

    // BezierCurve
    public void bezierCurve(Graphics g, int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4) 
    {
        for (int i = 0; i <= 1000; i++) 
        {
            double t = i / 1000.0;
            int x = (int) (Math.pow((1 - t), 3) * x1 +
                    3 * t * Math.pow((1 - t), 2) * x2 +
                    3 * t * t * (1 - t) * x3 +
                    t * t * t * x4);

            int y = (int) (Math.pow((1 - t), 3) * y1 +
                    3 * t * Math.pow((1 - t), 2) * y2 +
                    3 * t * t * (1 - t) * y3 +
                    t * t * t * y4);

            plot(g, x, y, 2);
        }
    }

    // BresenhamLine
    public void bresenhamLine(Graphics g, int x1, int y1, int x2, int y2) 
    {
        int dx = Math.abs(x2 - x1);
        int dy = Math.abs(y2 - y1);

        int sx = (x1 < x2) ? 1 : -1;
        int sy = (y1 < y2) ? 1 : -1;
        boolean isSwap = false;

        if (dy > dx) 
        {
            int d = dx;
            dx = dy;
            dy = d;
            isSwap = true;
        }
        int D = 2 * dy - dx;

        int x = x1;
        int y = y1;

        for (int i = 1; i <= dx; i++) 
        {
            plot(g, x, y, 2);
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

    public BufferedImage floodFill(BufferedImage m, int x, int y, Color targetColor, Color replacementColor) 
    {
        Queue<Point> q = new LinkedList<Point>();
        Graphics2D g2 = m.createGraphics();

        g2.setColor(replacementColor);
        plot(g2, x, y, 1);
        q.add(new Point(x, y));

        while (!q.isEmpty()) 
        {
            Point p = q.poll();
            // s
            if (p.y < 600 && new Color(m.getRGB(p.x, p.y + 1)).equals(targetColor)) 
            {
                g2.setColor(replacementColor);
                plot(g2, p.x, p.y + 1, 1);
                q.add(new Point(p.x, p.y + 1));
            }
            // n
            if (p.y > 0 && new Color(m.getRGB(p.x, p.y - 1)).equals(targetColor)) 
            {
                g2.setColor(replacementColor);
                plot(g2, p.x, p.y - 1, 1);
                q.add(new Point(p.x, p.y - 1));
            }
            // e
            if (p.x < 600 && new Color(m.getRGB(p.x + 1, p.y)).equals(targetColor)) 
            {
                g2.setColor(replacementColor);
                plot(g2, p.x + 1, p.y, 1);
                q.add(new Point(p.x + 1, p.y));
            }
            // w
            if (p.x > 0 && new Color(m.getRGB(p.x - 1, p.y)).equals(targetColor)) 
            {
                g2.setColor(replacementColor);
                plot(g2, p.x - 1, p.y, 1);
                q.add(new Point(p.x - 1, p.y));
            }
        }
        return m;
    }
}
