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

    public void render(Graphics g, Vector screenPosition, Size size) {
        g.drawImage(image,
                (int) screenPosition.getX(),
                (int) screenPosition.getY(),
                (int) size.getWidth(),
                (int) size.getHeight(),
                null);
    }
}
