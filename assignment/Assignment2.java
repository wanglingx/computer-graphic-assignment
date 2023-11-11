//63050188 wafidah sookwises
//63050212 Annisa Nurfitria Singsathit
//Reference : https://youtu.be/nCADw1liCLw

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Assignment2___63050188_63050212 extends JPanel implements Runnable {
    private ShadowType shadowType = ShadowType.CENTER;
    private int shadowSize = 40;
    private float shadowOpacity = 0.9f;
    private Color shadowColor = Color.decode("#FDA65D");
    //setting animation type
    double squareRotate = 0;
    
    public static void main(String[] args) {
        Assignment2___63050188_63050212 m = new Assignment2___63050188_63050212();
        JFrame f = new JFrame();
        f.add(m);
        f.setTitle("SPACE 101");
        f.setSize(600, 600);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
        new Thread(m).start();
        
    }
    @Override
    public void run() {
        // set Time
        // double startTime = System.currentTimeMillis();
        double lastTime = System.currentTimeMillis();
        double currentTime, elapsedTime;
        
        while (true) { // control time
            currentTime = System.currentTimeMillis();
            elapsedTime = currentTime - lastTime;
            lastTime = currentTime;

            //sun
        
            // control moon
            squareRotate += 0.2 * elapsedTime / 1000.0;
            //moveCircle += 100 * elapsedTime / 1000.0;
           
            repaint();
        }

    }
    
    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, 600, 600);

        int size = shadowSize * 2;
        int x = 0;
        int y = 0;
        int width = getWidth() - size;
        int height = getHeight() - size;

        if (shadowType == ShadowType.TOP) {
            x = shadowSize;
            y = size;
        }
        else if (shadowType == ShadowType.BOT) {
            x = shadowSize;
            y = 0;
        }
        else if (shadowType == ShadowType.TOP_LEFT) {
            x = size;
            y = size;
        }
        else if (shadowType == ShadowType.TOP_RIGHT) {
            x = 0;
            y = size;
        }
        else if (shadowType == ShadowType.BOT_LEFT) {
            x = size;
            y = 0;
        }
        else if (shadowType == ShadowType.BOT_RIGHT) {
            x = 0;
            y = 0;
        }
        else {
            x = shadowSize;
            y = shadowSize;
        }
        
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g3 = img.createGraphics();
        g3.setBackground(getBackground());
        g3.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        //create shadow and sun
        fillmidPointCircle(g3, 60, 60, 60, 3, Color.decode("#FDA65D"));
        ShadowRenderer render = new ShadowRenderer(shadowSize, shadowOpacity, shadowColor);
        g2.drawImage(render.createShadow(img), 0, 0, null);
        g2.drawImage(img, x, y, null);


        // earth and spattern
        fillmidPointCircle(g2, 370, 350, 80, 3, Color.decode("#2F86A6"));
        // g2.setColor(Color.decode("#34BE82"));

       
        // moon ng
        g2.rotate(squareRotate, 370, 350);
        fillmidPointCircle(g2, 350, 500, 30, 3, Color.decode("#FFDD93"));
        fillmidPointCircle(g2, 330, 500, 30, 3, Color.BLACK);
        
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

    public void fillmidPointCircle(Graphics g, int xc, int yc, int r, int size, Color c) {
        for (int i = 0; i <= r; i++) {
            g.setColor(c);
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

    public void fillmidPointEllipse(Graphics g, int xc, int yc, int a,int b, int size, Color c) {
        for (int i = 0; i <= a; i++) {
            g.setColor(c);
            midPointEllipse(g, xc, yc, a, b, size);
        }
    }

}

enum ShadowType {
    CENTER, TOP_RIGHT, TOP_LEFT, BOT_RIGHT, BOT_LEFT, BOT, TOP;
}

//Implement drop shadow code by Romain Guy
class ShadowRenderer {
    public static final String SIZE_CHANGED_PROPERTY = "shadow_size";
    public static final String OPACITY_CHANGED_PROPERTY = "shadow_opacity";
    public static final String COLOR_CHANGED_PROPERTY = "shadow_color";

    // size of the shadow in pixels (defines the fuzziness)
    private int size = 5;

    // opacity of the shadow
    private float opacity = 0.5f;

    // color of the shadow
    private Color color = Color.BLACK;

    public ShadowRenderer() {
        this(5, 0.5f, Color.BLACK);
    }

    public ShadowRenderer(final int size, final float opacity, final Color color) {
        // noinspection ThisEscapedInObjectConstruction
        this.size = size;
        this.opacity = opacity;
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public float getOpacity() {
        return opacity;
    }

    public int getSize() {
        return size;
    }

    public BufferedImage createShadow(final BufferedImage image) {
        // Written by Sesbastien Petrucci
        int shadowSize = size * 2;

        int srcWidth = image.getWidth();
        int srcHeight = image.getHeight();

        int dstWidth = srcWidth + shadowSize;
        int dstHeight = srcHeight + shadowSize;

        int left = size;
        int right = shadowSize - left;

        int yStop = dstHeight - right;

        int shadowRgb = color.getRGB() & 0x00FFFFFF;
        int[] aHistory = new int[shadowSize];
        int historyIdx;

        int aSum;

        BufferedImage dst = new BufferedImage(dstWidth, dstHeight,BufferedImage.TYPE_INT_ARGB);

        int[] dstBuffer = new int[dstWidth * dstHeight];
        int[] srcBuffer = new int[srcWidth * srcHeight];

        GraphicsUtilities.getPixels(image, 0, 0, srcWidth, srcHeight, srcBuffer);

        int lastPixelOffset = right * dstWidth;
        float hSumDivider = 1.0f / shadowSize;
        float vSumDivider = opacity / shadowSize;

        int[] hSumLookup = new int[256 * shadowSize];
        for (int i = 0; i < hSumLookup.length; i++) {
            hSumLookup[i] = (int) (i * hSumDivider);
        }

        int[] vSumLookup = new int[256 * shadowSize];
        for (int i = 0; i < vSumLookup.length; i++) {
            vSumLookup[i] = (int) (i * vSumDivider);
        }

        int srcOffset;
        // blur it into the destination picture
        for (int srcY = 0, dstOffset = left * dstWidth; srcY < srcHeight; srcY++) {

            // first pixels are empty
            for (historyIdx = 0; historyIdx < shadowSize;) {
                aHistory[historyIdx++] = 0;
            }

            aSum = 0;
            historyIdx = 0;
            srcOffset = srcY * srcWidth;

            // compute the blur average with pixels from the source image
            for (int srcX = 0; srcX < srcWidth; srcX++) {

                int a = hSumLookup[aSum];
                    dstBuffer[dstOffset++] = a << 24;
                    aSum -= aHistory[historyIdx];

                    a = srcBuffer[srcOffset + srcX] >>> 24;
                    aHistory[historyIdx] = a; 
                    aSum += a; 

                if (++historyIdx >= shadowSize) {
                    historyIdx -= shadowSize;
                }
            }

            for (int i = 0; i < shadowSize; i++) {

                int a = hSumLookup[aSum];
                dstBuffer[dstOffset++] = a << 24;

                aSum -= aHistory[historyIdx];

                if (++historyIdx >= shadowSize) {
                    historyIdx -= shadowSize;
                }
            }
        }

        // vertical pass
        for (int x = 0, bufferOffset = 0; x < dstWidth; x++, bufferOffset = x) {
            aSum = 0;
            // first pixels are empty
            for (historyIdx = 0; historyIdx < left;) {
                aHistory[historyIdx++] = 0;
            }
            // and then they come from the dstBuffer
            for (int y = 0; y < right; y++, bufferOffset += dstWidth) {
                int a = dstBuffer[bufferOffset] >>> 24; 
                aHistory[historyIdx++] = a; 
                aSum += a; 
            }
            bufferOffset = x;
            historyIdx = 0;
            // compute the blur avera`ge with pixels from the previous pass
            for (int y = 0; y < yStop; y++, bufferOffset += dstWidth) {

                int a = vSumLookup[aSum];
                dstBuffer[bufferOffset] = a << 24 | shadowRgb; // store alpha value + shadow color

                aSum -= aHistory[historyIdx]; // substract the oldest pixel from the sum

                a = dstBuffer[bufferOffset + lastPixelOffset] >>> 24; // extract the new pixel ...
                aHistory[historyIdx] = a; // ... and store its value into history
                aSum += a; // ... and add its value to the sum

                if (++historyIdx >= shadowSize) {
                    historyIdx -= shadowSize;
                }
            }
            // blur the end of the column - no pixels to grab anymore
            for (int y = yStop; y < dstHeight; y++, bufferOffset += dstWidth) {

                int a = vSumLookup[aSum];
                dstBuffer[bufferOffset] = a << 24 | shadowRgb;

                aSum -= aHistory[historyIdx]; // substract the oldest pixel from the sum

                if (++historyIdx >= shadowSize) {
                    historyIdx -= shadowSize;
                }
            }
        }
        GraphicsUtilities.setPixels(dst, 0, 0, dstWidth, dstHeight, dstBuffer);
        return dst;
    }
}
