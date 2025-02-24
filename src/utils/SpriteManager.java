package utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SpriteManager {

    public static BufferedImage loadImage(String path) {
        try {
            return ImageIO.read(SpriteManager.class.getResourceAsStream(path));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static BufferedImage scaleImage(BufferedImage defaultImage, int scaleWidth, int scaleHeight) {
        BufferedImage scaledImage = new BufferedImage(scaleWidth, scaleHeight, defaultImage.getType());
        Graphics2D graphics2D = scaledImage.createGraphics();
        graphics2D.drawImage(defaultImage, 0, 0, scaleWidth, scaleHeight, null);
        graphics2D.dispose();

        return scaledImage;
    }

    public static BufferedImage[] loadAndSaveSpriteFrames(String[] paths, int width, int height) {
        BufferedImage[] frames = new BufferedImage[paths.length];

        for (int i = 0; i < paths.length; i++) {
            frames[i] = scaleImage(loadImage(paths[i]), width, height);
        }

        return frames;
    }

    public static void cropAndSaveSprites(
            BufferedImage spriteSheet, int width, int height, int row, int col, int cols_per, int size, String state
    ) {
        for (int i = 0; i < size; i++) {
            BufferedImage sprite = spriteSheet.getSubimage(col * width, row * height, width, height);

            String fileName = state + "_" + i + ".png";

            try {
                ImageIO.write(sprite, "png", new File(fileName));
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (col == cols_per - 1) {
                col = 0;
                row++;
            } else {
                col++;
            }
        }
    }

    public static BufferedImage[] loadAndScaleSpriteFrames(String pathName, int size, int width, int height) {
        BufferedImage[] frames = new BufferedImage[size];
        String[] paths = new String[size];

        for (int i = 0; i < size; i++) {
            paths[i] = "" + pathName + "_" + i + ".png";
        }

        for (int i = 0; i < size; i++) {
            frames[i] = scaleImage(loadImage(paths[i]), width, height);
        }

        return frames;
    }

    public static void main(String[] args) {
        cropAndSaveSprites(
                loadImage("/enemies/BIRDSPRITESHEET_White.png"),
                32,
                32,
                11,
                0,
                4,
                8,
                "walk_up"
        );
    }
}
