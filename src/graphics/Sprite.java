package graphics;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import util.Size;
import util.Vector;

public class Sprite {
    private BufferedImage image;

    public Sprite(String spritePath) {
        try {
            image = ImageIO.read(new File(spritePath));
        } catch (Exception e) {
            System.out.println("Failed to load sprite: " + spritePath);
        }
    }

    public BufferedImage getImage() {
        return image;
    }

    public void draw(Graphics g, Vector screenPosition, Size size, boolean flip) {
        if (flip) {
            g.drawImage(
                    image,
                    (int) (screenPosition.x() + size.width() / 2),
                    (int) (screenPosition.y() - size.height() / 2),
                    (int) -size.width(),
                    (int) size.height(),
                    null);
        } else {
            g.drawImage(
                    image,
                    (int) (screenPosition.x() - size.width() / 2),
                    (int) (screenPosition.y() - size.height() / 2),
                    (int) size.width(),
                    (int) size.height(),
                    null);
        }
    }

    public void render(Graphics g, Vector screenPosition, Size size) {
        draw(g, screenPosition, size, false);
    }
}
