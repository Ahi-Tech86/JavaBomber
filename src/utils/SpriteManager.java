package utils;

import javax.imageio.ImageIO;
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

    public static void main(String[] args) {
        cropAndSaveSprites(
                loadImage("/objects/dynamite.png"),
                32,
                32,
                0,
                0,
                3,
                9,
                "dynamite"
        );
    }
}
