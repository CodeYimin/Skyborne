package structures;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

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

    public void draw(Graphics g, Vector screenPosition, Vector size, boolean flip) {
        if (flip) {
            g.drawImage(
                    image,
                    (int) (screenPosition.getX() + size.getX() / 2),
                    (int) (screenPosition.getY() - size.getY() / 2),
                    (int) -size.getX(),
                    (int) size.getY(),
                    null);
        } else {
            g.drawImage(
                    image,
                    (int) (screenPosition.getX() - size.getX() / 2),
                    (int) (screenPosition.getY() - size.getY() / 2),
                    (int) size.getX(),
                    (int) size.getY(),
                    null);
        }
    }

    public void render(Graphics g, Vector screenPosition, Vector size) {
        draw(g, screenPosition, size, false);
    }
}
