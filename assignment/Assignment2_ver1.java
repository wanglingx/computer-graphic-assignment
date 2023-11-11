//63050188 wafidah sookwises
//63050212 Annisa Nurfitria Singsathit
import javax.swing.*;
import java.awt.*;
public class Assignment2_63050188_63050212 extends JPanel implements Runnable
{
    // check scene status
    int checkScene = 0;

    // set color
    private final Color red = new Color(255, 0, 127);
    private final Color red_mid = new Color(255, 51, 153);
    private final Color red_mid_light = new Color(255, 102, 178);
    private final Color red_light = new Color(255, 153, 204);
    private final Color red_wh = new Color(255, 204, 229);
    private final Color green_neo = Color.decode("#E7FBBE");
    private final Color green_light = Color.decode("#9AD0EC");
    private final Color green_dark = Color.decode("#1572A1");
    private final Color green_pt = Color.decode("#94B4A4");
    private final Color peach = Color.decode("#FFB2A6");
    private final Color orange_summer = Color.decode("#F58634");
    private final Color yellow_light = Color.decode("#feffd5");
    
    // setting animation type
    double circlelastrun = 470;
    double checkCirclelast = 470;
    Color circleLastRGB;
    double squareRotate = 0;

    // circle5
    double circle5 = 410;
    double checlCircle5 = 410;
    Color circle5RGB;

    // circle4
    double circle4 = 350;
    double checlCircle4 = 350;
    Color circle4RGB;

    // circle 3
    double circle3 = 290;
    double checlCircle3 = 290;
    Color circle3RGB;
    // circle 2

    double circle2 = 230;
    double checlCircle2 = 230;
    Color circle2RGB;

    // circle 1
    double circle1 = 170;
    double checlCircle1 = 170;
    Color circle1RGB;

    // circle 0
    double circle0 = 110;
    double checlCircle0 = 110;
    Color circle0RGB;
    double velocity = 200;
    Color bgRGB = green_neo;

    // key correct
    int countY = 290;
    int fixPosition = 290;

    // circle up
    double circle11 = 279;
    double checkCircle11 = 301;
    Color circle11RGB;

    // circle down
    double circle01 = 279;
    double checkCircle01 = 257;
    double velWave = 50;

    public static void main(String[] args) 
    {
        Assignment2_63050188_63050212 m = new Assignment2_63050188_63050212();
        JFrame f = new JFrame();
        f.add(m);
        f.setTitle("LOADING 101");
        f.setSize(600, 600);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
        new Thread(m).start();
    }

    @Override
    public void run() 
    {
        // set Time
        double startTime = System.currentTimeMillis();
        double lastTime = System.currentTimeMillis();
        double currentTime, elapsedTime;
        while (true) 
        {
            // control time
            currentTime = System.currentTimeMillis();
            elapsedTime = currentTime - lastTime;
            lastTime = currentTime;

            // control last circle
            if (checkCirclelast == 470)
            {
                circlelastrun -= velocity * elapsedTime / 1000.0;
                if ((int) circlelastrun == 110)
                    checkCirclelast = 110;
            }

            if (checkCirclelast == 110) 
            {
                circlelastrun += velocity * elapsedTime / 1000.0;
                if ((int) circlelastrun == 470)
                    checkCirclelast = 470;
            }

            // change color circle last
            if ((int) circlelastrun == 470)
                circleLastRGB = red;
            else if ((int) circlelastrun == 410)
                circleLastRGB = red_mid;
            else if ((int) circlelastrun == 350)
                circleLastRGB = red_mid_light;
            else if ((int) circlelastrun == 290)
                circleLastRGB = red_light;
            else if ((int) circlelastrun == 230)
                circleLastRGB = red_wh;
            else if ((int) circlelastrun == 170)
                circleLastRGB = red_wh;
            else if ((int) circlelastrun == 110)
                circleLastRGB = red_wh;

            // control 5
            if (circlelastrun <= 410 || checlCircle5 == 410) 
            {
                circle5 += velocity * elapsedTime / 1000.0;
                if (circle5 >= 470) 
                {
                    circle5RGB = red;
                    checlCircle5 = 470;
                }
            }

            if (circlelastrun >= 470 || checlCircle5 == 470) 
            {
                circle5 -= velocity * elapsedTime / 1000.0;
                if (circle5 <= 410) 
                {
                    circle5RGB = red_mid;
                    checlCircle5 = 410;
                }
            }

            // control 4
            if (circlelastrun <= 350 || checlCircle4 == 350) 
            {
                circle4 += velocity * elapsedTime / 1000.0;
                if (circle4 >= 410) 
                {
                    circle4RGB = red_mid;
                    checlCircle4 = 410;
                }
            }

            if (circlelastrun >= 410 || checlCircle4 == 410) 
            {
                circle4 -= velocity * elapsedTime / 1000.0;
                if (circle4 <= 350) 
                {
                    circle4RGB = red_mid_light;
                    checlCircle4 = 350;
                }
            }

            //control 3
            if (circlelastrun <= 290 || checlCircle3 == 290) 
            {
                circle3 += velocity * elapsedTime / 1000.0;
                if (circle3 >= 350) 
                {
                    circle3RGB = red_mid_light;
                    checlCircle3 = 350;
                }
            }

            if (circlelastrun >= 350 || checlCircle3 == 350) 
            {
                circle3 -= velocity * elapsedTime / 1000.0;
                if (circle3 <= 290) 
                {
                    circle3RGB = red_light;
                    checlCircle3 = 290;
                }
            }

            // //control 2
            if (circlelastrun <= 230 || checlCircle2 == 230) 
            {
                circle2 += velocity * elapsedTime / 1000.0;
                if (circle2 >= 290) 
                {
                    circle2RGB = red_light;
                    checlCircle2 = 290;
                }
            }

            if (circlelastrun >= 290 || checlCircle2 == 290) 
            {
                circle2 -= velocity * elapsedTime / 1000.0;
                if (circle2 <= 230) 
                {
                    circle2RGB = red_light;
                    checlCircle2 = 230;
                }
            }

            // //control 1
            if (circlelastrun <= 170 || checlCircle1 == 170) 
            {
                circle1 += velocity * elapsedTime / 1000.0;
                if (circle1 >= 230) 
                {
                    circle1RGB = red_light;
                    checlCircle1 = 230;
                }
            }

            if (circlelastrun >= 230 || checlCircle1 == 230) 
            {
                circle1 -= velocity * elapsedTime / 1000.0;
                if (circle1 <= 170) 
                {
                    circle1RGB = red_wh;
                    checlCircle1 = 170;
                }
            }

            // control 0
            if (circlelastrun <= 110 || checlCircle0 == 110) 
            {
                circle0 += velocity * elapsedTime / 1000.0;
                if (circle0 >= 170) 
                {
                    circle0RGB = red_wh;
                    checlCircle0 = 170;
                }
            }

            if (circlelastrun >= 170 || checlCircle0 == 170) 
            {
                circle0 -= velocity * elapsedTime / 1000.0;
                if (circle0 <= 110) 
                {
                    circle0RGB = red_wh;
                    checlCircle0 = 110;
                }
            }

            // change scene 1 and new draw
            if ((currentTime - startTime) / 1000 >= 5) 
            {
                checkScene = 1;
                bgRGB = yellow_light;

                // control circle down
                if (checkCircle01 == 257) 
                {
                    circle01 += velWave * elapsedTime / 1000.0;
                    if (circle01 >= 301) 
                    {
                        checkCircle01 = 301;
                    }
                }

                if (checkCircle01 == 301)
                {
                    circle01 -= velWave * elapsedTime / 1000.0;
                    if (circle01 <= 257) 
                    {
                        checkCircle01 = 257;
                    }
                }

                // control circle down
                if (checkCircle11 == 301) 
                {
                    circle11 -= velWave * elapsedTime / 1000.0;
                    if (circle11 <= 257) 
                    {
                        checkCircle11 = 257;
                    }
                }

                if (checkCircle11 == 257) 
                {
                    circle11 += velWave * elapsedTime / 1000.0;
                    if (circle11 >= 301) 
                    {
                        checkCircle11 = 301;
                    }
                }
            }

            // chang scene 2 and new draw
            if ((currentTime - startTime) / 1000 >= 14) 
            {
                checkScene = 2;
                bgRGB = green_light;
                break;
            }
            repaint();
        }
    }// end run()

    @Override
    public void paintComponent(Graphics g) 
    {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, 600, 600);

        //start scene
        if (checkScene == 0) 
        {
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            fillmidPointCircle(g2, 290, 279, 250, 2, bgRGB);
            // circle0
            fillmidPointCircle(g2, (int) circle0, 279, 22, 3, circle0RGB);
            // circle 1
            fillmidPointCircle(g2, (int) circle1, 279, 22, 3, circle1RGB);
            // circle2
            fillmidPointCircle(g2, (int) circle2, 279, 22, 3, circle2RGB);
            // circle3
            fillmidPointCircle(g2, (int) circle3, 279, 22, 3, circle3RGB);
            // circle4
            fillmidPointCircle(g2, (int) circle4, 279, 22, 3, circle4RGB);
            // circle5
            fillmidPointCircle(g2, (int) circle5, 279, 22, 3, circle5RGB);
            // circle run
            fillmidPointCircle(g2, (int) circlelastrun, 279, 22, 3, circleLastRGB);
        }

        // scene2
        if (checkScene == 1) 
        {
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            fillmidPointCircle(g2, 290, 279, 250, 2, bgRGB);
            // circle0
            fillmidPointCircle(g2, 110, (int) circle01, 22, 3, orange_summer);
            // circle 1
            fillmidPointCircle(g2, 170, (int) circle11, 22, 3, circle1RGB);
            // circle2
            fillmidPointCircle(g2, 230, (int) circle01, 22, 3, peach);
            // circle3
            fillmidPointCircle(g2, 290, (int) circle11, 22, 3, circle3RGB);
            // circle4
            fillmidPointCircle(g2, 350, (int) circle01, 22, 3, circle4RGB);
            // circle5
            fillmidPointCircle(g2, 410, (int) circle11, 22, 3, green_pt);
            // circle run
            fillmidPointCircle(g2, 470, (int) circle01, 22, 3, circleLastRGB);
        }

        //last scene
        if (checkScene == 2) 
        {
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            fillmidPointCircle(g2, 290, 279, 250, 2, bgRGB);
            for (int i = 110; i <= 450; i++) 
            {
                if (fixPosition == 290) 
                {
                    fillmidPointCircle(g2, i, countY, 22, 2, green_dark);
                    countY++;
                    if (countY >= 410) 
                    {
                        countY = 410;
                        fixPosition = 410;
                    }
                }

                if (fixPosition == 410) 
                {
                    if (!(i == 110 && countY < 279))
                        fillmidPointCircle(g2, i, countY, 22, 2, green_dark);
                    countY--;
                    if (countY <= 188) 
                    {
                        countY = 290;
                        fixPosition = 290;
                        i = 110;
                    }
                }
            }
        }
    }// end painComponent

    public void plot(Graphics g, int x, int y, int size) 
    {
        g.fillRect(x, y, size, size);
    }

    public void midPointCircle(Graphics g, int xc, int yc, int r, int size) 
    {
        int x = 0;
        int y = r;
        int D = 1 - r;

        while (x <= y) 
        {
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

    public void fillmidPointCircle(Graphics g, int xc, int yc, int r, int size, Color c) 
    {
        for (int i = 0; i <= r; i++) 
        {
            g.setColor(c);
            midPointCircle(g, xc, yc, i, size);
        }
    }
}