import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
import java.util.Random;

public class JavaGraphics extends JApplet implements Runnable {
    Thread animator;

    // variables for animating the sun
    int xSunPosition;
    int ySunPosition;

    // variables for the sky changing color
    int skyR, skyG, skyB;
    Color skyColor = new Color(skyR, skyG, skyB);

    // booleans for controlling when which animations occur
    boolean sunIsRising = true;
    boolean sunIsSetting = false;
    boolean cloudDrift = false;
    boolean starsComeOut = false;

    // variables for animating the cloud
    int cloudX = 850;
    int cloudY = 100;
    int cloudTracker = 0;

    // variables for keeping the stars hidden until they should come out
    Color starColor = skyColor;
    int starR, starG, starB;

    // variables for the points of the stars
    int starX;
    int starY;
    int xPoints[] = { starX, starX + 11, starX + 7, starX + 3, starX + 14 };
    int yPoints[] = { starY, starY + 8, starY - 5, starY + 8, starY };

    // random number object for star twinkling
    Random random = new Random();
    // control the star color by whether the twinkling should be going
    boolean startedTwinkling = false;
    // color variables for each of the stars
    Color star1Color;
    Color star2Color;
    Color star3Color;
    Color star4Color;
    Color star5Color;
    Color star6Color;
    Color star7Color;
    Color star8Color;
    Color star9Color;
    Color star10Color;

    // for the double buffer to prevent flickeriness
    BufferedImage img;
    Graphics g2;

    @Override
    public void init() {
        JRootPane root = this.getRootPane();
        root.putClientProperty("defeatSystemEventQueueCheck", Boolean.TRUE);

    }

    @Override
    public void start() {
        img = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
        g2 = img.createGraphics();
        xSunPosition = 550;
        ySunPosition = 600;
        animator = new Thread(this);
        animator.start();
        skyR = 0;
        skyG = 0;
        skyB = 0;
        skyColor = new Color(skyR, skyG, skyB);
        starColor = skyColor;
    }

    @Override
    public void stop() {
        animator = null;
    }

    @Override
    public void destroy() {

    }

    @Override
    public void paint(Graphics g) {

        // sky
        g2.setColor(skyColor);
        g2.fillRect(0, 0, getWidth(), getHeight() - 100);

        // stars
        g2.setColor(starColor);

        // first star
        if (startedTwinkling == false)
            star1Color = starColor;
        g2.setColor(star1Color);
        starX = 50;
        starY = 50;
        updatePoints(starX, starY);
        g2.fillPolygon(xPoints, yPoints, 5);
        g2.fillOval(starX + 5, starY - 1, 6, 7);

        // second star
        if (startedTwinkling == false)
            star2Color = starColor;
        g2.setColor(star2Color);
        starX = 100;
        starY = 180;
        updatePoints(starX, starY);
        g2.fillPolygon(xPoints, yPoints, 5);
        g2.fillOval(starX + 5, starY - 1, 6, 7);

        // third star
        if (startedTwinkling == false)
            star3Color = starColor;
        g2.setColor(star3Color);
        starX = 175;
        starY = 70;
        updatePoints(starX, starY);
        g2.fillPolygon(xPoints, yPoints, 5);
        g2.fillOval(starX + 5, starY - 1, 6, 7);

        // fourth star
        if (startedTwinkling == false)
            star4Color = starColor;
        g2.setColor(star4Color);
        starX = 280;
        starY = 120;
        updatePoints(starX, starY);
        g2.fillPolygon(xPoints, yPoints, 5);
        g2.fillOval(starX + 5, starY - 1, 6, 7);

        // fifth star
        if (startedTwinkling == false)
            star5Color = starColor;
        g2.setColor(star5Color);
        starX = 330;
        starY = 25;
        updatePoints(starX, starY);
        g2.fillPolygon(xPoints, yPoints, 5);
        g2.fillOval(starX + 5, starY - 1, 6, 7);

        // sixth star
        if (startedTwinkling == false)
            star6Color = starColor;
        g2.setColor(star6Color);
        starX = 450;
        starY = 160;
        updatePoints(starX, starY);
        g2.fillPolygon(xPoints, yPoints, 5);
        g2.fillOval(starX + 5, starY - 1, 6, 7);

        // seventh star
        if (startedTwinkling == false)
            star7Color = starColor;
        g2.setColor(star7Color);
        starX = 500;
        starY = 50;
        updatePoints(starX, starY);
        g2.fillPolygon(xPoints, yPoints, 5);
        g2.fillOval(starX + 5, starY - 1, 6, 7);

        // eighth star
        if (startedTwinkling == false)
            star8Color = starColor;
        g2.setColor(star8Color);
        starX = 560;
        starY = 110;
        updatePoints(starX, starY);
        g2.fillPolygon(xPoints, yPoints, 5);
        g2.fillOval(starX + 5, starY - 1, 6, 7);

        // ninth star
        if (startedTwinkling == false)
            star9Color = starColor;
        g2.setColor(star9Color);
        starX = 650;
        starY = 30;
        updatePoints(starX, starY);
        g2.fillPolygon(xPoints, yPoints, 5);
        g2.fillOval(starX + 5, starY - 1, 6, 7);

        // tenth star
        if (startedTwinkling == false)
            star10Color = starColor;
        g2.setColor(star10Color);
        starX = 725;
        starY = 200;
        updatePoints(starX, starY);
        g2.fillPolygon(xPoints, yPoints, 5);
        g2.fillOval(starX + 5, starY - 1, 6, 7);

        // sun, yellow
        g2.setColor(Color.yellow);
        g2.fillOval(xSunPosition, ySunPosition, 110, 110);

        // cloud, white
        g2.setColor(Color.white);
        g2.fillOval(cloudX, cloudY, 50, 60);
        g2.fillOval(cloudX + 15, cloudY - 25, 70, 80);
        g2.fillOval(cloudX + 30, cloudY + 30, 70, 50);
        g2.fillOval(cloudX + 60, cloudY, 80, 60);
        g2.fillOval(cloudX + 50, cloudY - 30, 60, 40);
        g2.fillOval(cloudX + 80, cloudY - 20, 70, 60);
        g2.fillOval(cloudX + 80, cloudY + 20, 70, 60);
        g2.fillOval(cloudX + 100, cloudY, 70, 60);

        // grass, green
        g2.setColor(new Color(35, 130, 5));
        g2.fillRect(0, getHeight() - 100, getWidth(), getHeight());

        // house, purple
        g2.setColor(new Color(200, 165, 245));
        g2.fillRect(300, 300, 200, 200);

        // door, brown
        g2.setColor(new Color(150, 80, 50));
        g2.fillRect(410, 380, 60, 120);

        // roof, dark brown
        g2.setColor(new Color(100, 55, 35));
        int xRoof[] = { 280, 400, 520 };
        int yRoof[] = { 300, 195, 300 };
        g2.fillPolygon(xRoof, yRoof, 3);

        // window and doorknob, white
        g2.setColor(Color.white);
        g2.fillRect(330, 330, 40, 40);
        g2.fillOval(458, 440, 7, 7);

        // windowpanes, black
        g2.setColor(Color.black);
        g2.fillRect(347, 330, 6, 40);
        g2.fillRect(330, 347, 40, 6);

        g.drawImage(img, 0, 0, this);
    }

    public static void main(String s[]) {
        JFrame f = new JFrame("Java Graphics");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JApplet applet = new JavaGraphics();
        f.getContentPane().add(applet);
        applet.init();
        f.pack();
        f.setSize(new Dimension(800, 600));
        f.setVisible(true);
        applet.start();
    }

    public void run() {
        while (Thread.currentThread() == animator) {

            // sunrise animation
            if (sunIsRising) {
                if (ySunPosition > 10)
                    ySunPosition -= 1;

                if (skyR < 200) {
                    skyR++;
                }
                if (skyG < 230) {
                    skyG++;
                }
                if (skyB < 250) {
                    skyB++;
                }

                skyColor = new Color(skyR, skyG, skyB);
                starColor = skyColor;

                // once the sun is risen, run the cloud animation
                if (ySunPosition == 10) {
                    sunIsRising = false;
                    cloudDrift = true;
                }
            }

            // cloud animation
            if (cloudDrift) {
                // drift the cloud by decreasing cloudX
                cloudX--;
                cloudTracker++;

                // after the cloud has gone a ways, decrease y every fifth x
                // so that the cloud goes up and disappears at the top of the screen
                if (cloudTracker > 500) {
                    if (cloudTracker % 5 == 0)
                        cloudY--;
                }
                // once the cloud is gone, run the sunset
                if (cloudY == -40) {
                    cloudDrift = false;
                    sunIsSetting = true;
                }
            }

            // sunset animation
            if (sunIsSetting) {
                if (ySunPosition < 600) {
                    ySunPosition += 1;
                }

                // once the sun is low, start darkening the sky
                if (ySunPosition > 350) {

                    if (skyR > 0)
                        skyR--;
                    if (skyG > 0)
                        skyG--;
                    if (skyB > 0)
                        skyB--;
                }
                skyColor = new Color(skyR, skyG, skyB);
                // stars still hidden
                starColor = skyColor;

                // when the sky is completely dark, run the stars
                if (skyR == 0 && skyG == 0 && skyB == 0) {
                    sunIsSetting = false;
                    starsComeOut = true;
                }
            }

            // star animation
            if (starsComeOut) {

                // fade the stars in
                if (starR < 255) {
                    starR++;
                    starG++;
                    starB++;
                    starColor = new Color(starR, starG, starB);
                }

                // make them twinkle
                if (starR == 255)
                    startedTwinkling = true;
                if (startedTwinkling) {
                    int randNumber = random.nextInt(10); // Returns random int >= 0 and < n

                    switch (randNumber + 1) {
                        case 1: // twinkle star 1
                            star1Color = new Color(starR - 30, starG - 30, starB - 30);
                            repaint();
                            try {
                                Thread.sleep(60); // time in milliseconds
                            } catch (Exception e) {
                                break;
                            }
                            star1Color = new Color(starR - 60, starG - 60, starB - 60);
                            repaint();
                            try {
                                Thread.sleep(60); // time in milliseconds
                            } catch (Exception e) {
                                break;
                            }
                            star1Color = new Color(starR - 30, starG - 30, starB - 30);
                            repaint();
                            try {
                                Thread.sleep(60); // time in milliseconds
                            } catch (Exception e) {
                                break;
                            }
                            star1Color = new Color(starR, starG, starB);
                            repaint();
                            try {
                                Thread.sleep(60); // time in milliseconds
                            } catch (Exception e) {
                                break;
                            }

                            break;
                        case 2: // twinkle star
                            star2Color = new Color(starR - 30, starG - 30, starB - 30);
                            repaint();
                            try {
                                Thread.sleep(60); // time in milliseconds
                            } catch (Exception e) {
                                break;
                            }
                            star2Color = new Color(starR - 60, starG - 60, starB - 60);
                            repaint();
                            try {
                                Thread.sleep(60); // time in milliseconds
                            } catch (Exception e) {
                                break;
                            }
                            star2Color = new Color(starR - 30, starG - 30, starB - 30);
                            repaint();
                            try {
                                Thread.sleep(60); // time in milliseconds
                            } catch (Exception e) {
                                break;
                            }
                            star2Color = new Color(starR, starG, starB);
                            repaint();
                            try {
                                Thread.sleep(60); // time in milliseconds
                            } catch (Exception e) {
                                break;
                            }

                            break;
                        case 3: // etc
                            star3Color = new Color(starR - 30, starG - 30, starB - 30);
                            repaint();
                            try {
                                Thread.sleep(60); // time in milliseconds
                            } catch (Exception e) {
                                break;
                            }
                            star3Color = new Color(starR - 60, starG - 60, starB - 60);
                            repaint();
                            try {
                                Thread.sleep(60); // time in milliseconds
                            } catch (Exception e) {
                                break;
                            }
                            star3Color = new Color(starR - 30, starG - 30, starB - 30);
                            repaint();
                            try {
                                Thread.sleep(60); // time in milliseconds
                            } catch (Exception e) {
                                break;
                            }
                            star3Color = new Color(starR, starG, starB);
                            repaint();
                            try {
                                Thread.sleep(60); // time in milliseconds
                            } catch (Exception e) {
                                break;
                            }

                            break;
                        case 4:
                            star4Color = new Color(starR - 30, starG - 30, starB - 30);
                            repaint();
                            try {
                                Thread.sleep(60); // time in milliseconds
                            } catch (Exception e) {
                                break;
                            }
                            star4Color = new Color(starR - 60, starG - 60, starB - 60);
                            repaint();
                            try {
                                Thread.sleep(60); // time in milliseconds
                            } catch (Exception e) {
                                break;
                            }
                            star4Color = new Color(starR - 30, starG - 30, starB - 30);
                            repaint();
                            try {
                                Thread.sleep(60); // time in milliseconds
                            } catch (Exception e) {
                                break;
                            }
                            star4Color = new Color(starR, starG, starB);
                            repaint();
                            try {
                                Thread.sleep(60); // time in milliseconds
                            } catch (Exception e) {
                                break;
                            }

                            break;
                        case 5:
                            star5Color = new Color(starR - 30, starG - 30, starB - 30);
                            repaint();
                            try {
                                Thread.sleep(60); // time in milliseconds
                            } catch (Exception e) {
                                break;
                            }
                            star5Color = new Color(starR - 60, starG - 60, starB - 60);
                            repaint();
                            try {
                                Thread.sleep(60); // time in milliseconds
                            } catch (Exception e) {
                                break;
                            }
                            star5Color = new Color(starR - 30, starG - 30, starB - 30);
                            repaint();
                            try {
                                Thread.sleep(60); // time in milliseconds
                            } catch (Exception e) {
                                break;
                            }
                            star5Color = new Color(starR, starG, starB);
                            repaint();
                            try {
                                Thread.sleep(60); // time in milliseconds
                            } catch (Exception e) {
                                break;
                            }

                            break;
                        case 6:
                            star6Color = new Color(starR - 30, starG - 30, starB - 30);
                            repaint();
                            try {
                                Thread.sleep(60); // time in milliseconds
                            } catch (Exception e) {
                                break;
                            }
                            star6Color = new Color(starR - 60, starG - 60, starB - 60);
                            repaint();
                            try {
                                Thread.sleep(60); // time in milliseconds
                            } catch (Exception e) {
                                break;
                            }
                            star6Color = new Color(starR - 30, starG - 30, starB - 30);
                            repaint();
                            try {
                                Thread.sleep(60); // time in milliseconds
                            } catch (Exception e) {
                                break;
                            }
                            star6Color = new Color(starR, starG, starB);
                            repaint();
                            try {
                                Thread.sleep(60); // time in milliseconds
                            } catch (Exception e) {
                                break;
                            }

                            break;
                        case 7:
                            star7Color = new Color(starR - 30, starG - 30, starB - 30);
                            repaint();
                            try {
                                Thread.sleep(60); // time in milliseconds
                            } catch (Exception e) {
                                break;
                            }
                            star7Color = new Color(starR - 60, starG - 60, starB - 60);
                            repaint();
                            try {
                                Thread.sleep(60); // time in milliseconds
                            } catch (Exception e) {
                                break;
                            }
                            star7Color = new Color(starR - 30, starG - 30, starB - 30);
                            repaint();
                            try {
                                Thread.sleep(60); // time in milliseconds
                            } catch (Exception e) {
                                break;
                            }
                            star7Color = new Color(starR, starG, starB);
                            repaint();
                            try {
                                Thread.sleep(60); // time in milliseconds
                            } catch (Exception e) {
                                break;
                            }

                            break;
                        case 8:
                            star8Color = new Color(starR - 30, starG - 30, starB - 30);
                            repaint();
                            try {
                                Thread.sleep(60); // time in milliseconds
                            } catch (Exception e) {
                                break;
                            }
                            star8Color = new Color(starR - 60, starG - 60, starB - 60);
                            repaint();
                            try {
                                Thread.sleep(60); // time in milliseconds
                            } catch (Exception e) {
                                break;
                            }
                            star8Color = new Color(starR - 30, starG - 30, starB - 30);
                            repaint();
                            try {
                                Thread.sleep(60); // time in milliseconds
                            } catch (Exception e) {
                                break;
                            }
                            star8Color = new Color(starR, starG, starB);
                            repaint();
                            try {
                                Thread.sleep(60); // time in milliseconds
                            } catch (Exception e) {
                                break;
                            }

                            break;
                        case 9:
                            star9Color = new Color(starR - 30, starG - 30, starB - 30);
                            repaint();
                            try {
                                Thread.sleep(60); // time in milliseconds
                            } catch (Exception e) {
                                break;
                            }
                            star9Color = new Color(starR - 60, starG - 60, starB - 60);
                            repaint();
                            try {
                                Thread.sleep(60); // time in milliseconds
                            } catch (Exception e) {
                                break;
                            }
                            star9Color = new Color(starR - 30, starG - 30, starB - 30);
                            repaint();
                            try {
                                Thread.sleep(60); // time in milliseconds
                            } catch (Exception e) {
                                break;
                            }
                            star9Color = new Color(starR, starG, starB);
                            repaint();
                            try {
                                Thread.sleep(60); // time in milliseconds
                            } catch (Exception e) {
                                break;
                            }

                            break;
                        case 10: // twinkle star 10
                            star10Color = new Color(starR - 30, starG - 30, starB - 30);
                            repaint();
                            try {
                                Thread.sleep(60); // time in milliseconds
                            } catch (Exception e) {
                                break;
                            }
                            star10Color = new Color(starR - 60, starG - 60, starB - 60);
                            repaint();
                            try {
                                Thread.sleep(60); // time in milliseconds
                            } catch (Exception e) {
                                break;
                            }
                            star10Color = new Color(starR - 30, starG - 30, starB - 30);
                            repaint();
                            try {
                                Thread.sleep(60); // time in milliseconds
                            } catch (Exception e) {
                                break;
                            }
                            star10Color = new Color(starR, starG, starB);
                            repaint();
                            try {
                                Thread.sleep(60); // time in milliseconds
                            } catch (Exception e) {
                                break;
                            }

                            break;
                        default: // do-nothing, shouldn't ever get this
                            break;
                    }

                }
                // end twinkle loop
            }

            repaint();
            try {
                Thread.sleep(27); // time in milliseconds
            } catch (Exception e) {
                break;
            }
        }
    }

    private void updatePoints(int x, int y) {
        xPoints[0] = x;
        xPoints[1] = x + 11;
        xPoints[2] = x + 7;
        xPoints[3] = x + 3;
        xPoints[4] = x + 14;
        yPoints[0] = y;
        yPoints[1] = y + 8;
        yPoints[2] = y - 5;
        yPoints[3] = y + 8;
        yPoints[4] = y;
    }
}