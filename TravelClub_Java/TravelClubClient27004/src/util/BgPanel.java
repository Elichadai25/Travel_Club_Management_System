package util;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 * BgPanel - a JPanel that paints a background image with a semi-transparent overlay.
 * Used by all module panels for consistent background styling.
 */
public class BgPanel extends JPanel {

    private final BufferedImage bg;
    private final Color overlay;

    public BgPanel(LayoutManager layout, String imagePath, Color overlay) {
        super(layout);
        this.overlay = overlay;
        this.bg = load(imagePath);
        setOpaque(true);
    }

    public BgPanel(LayoutManager layout) {
        this(layout, "/images/dashboard_bg.jpeg", new Color(255, 252, 245, 195));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (bg != null) {
            g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
            g.setColor(overlay);
            g.fillRect(0, 0, getWidth(), getHeight());
        }
    }

    private static BufferedImage load(String path) {
        try {
            InputStream is = BgPanel.class.getResourceAsStream(path);
            if (is != null) return ImageIO.read(is);
        } catch (Exception ignored) {}
        return null;
    }
}
