import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Rainbow extends JPanel {

    private final static Color VIOLET = new Color(128, 0, 128);
    private final static Color INDIGO = new Color(75, 0, 130);

    private Color[] colors = { Color.WHITE, VIOLET, INDIGO, Color.BLUE,
            Color.GREEN, Color.YELLOW, Color.ORANGE, Color.RED };

    Rainbow() {
        setBackground(Color.WHITE);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        int radious = 20;
        // rainbow at bottom center
        int centerX = getWidth() / 2;
        int centerY = getHeight() - 10;

        for (int i = colors.length; i > 0; i--) {
            g.setColor(colors[i - 1]);

            g.fillArc(centerX - i * radious, centerY - i * radious, i * radious
                    * 2, i * radious * 2, 0, 180);
        }
    }

    public static void main(String[] args) {
        Rainbow rainbow = new Rainbow();
        JFrame app = new JFrame();
        app.add(rainbow);
        app.setSize(400, 250);
        app.setVisible(true);
    }
}