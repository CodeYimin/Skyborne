package structures;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class Sprite {
    private BufferedImage image;

    public Sprite(String imagePath) {
        try {
            image = ImageIO.read(new File(imagePath));
        } catch (Exception e) {
            System.out.println("Failed to load sprite: " + imagePath);
        }
    }

    public void draw(Graphics g, Vector centerPosition, Vector size, double angle, boolean flipX, boolean flipY) {
        BufferedImage rotatedImage = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
        Graphics2D g2d = rotatedImage.createGraphics();

        // Rotation
        g2d.rotate(Math.PI / 2 - angle, image.getWidth() / 2, image.getHeight() / 2);

        // Flip and Save image
        if (flipX) {
            g2d.scale(-1, 1);
            g2d.drawImage(image, null, -image.getWidth(), 0);
        } else if (flipY) {
            g2d.scale(1, -1);
            g2d.drawImage(image, null, 0, -image.getHeight());
        } else {
            g2d.drawImage(image, null, 0, 0);
        }

        // Draw
        g.drawImage(
                rotatedImage,
                (int) (centerPosition.getX() - size.getX() / 2),
                (int) (centerPosition.getY() - size.getY() / 2),
                (int) size.getX(),
                (int) size.getY(),
                null);
    }
}
