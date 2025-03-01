package tile_interactive;

import components.GamePanel;
import utils.SpriteManager;

import java.util.Random;

public class DestructibleWall extends InteractiveTile {

    public DestructibleWall(GamePanel gamePanel, int worldX, int worldY) {
        super(gamePanel, worldX, worldY);

        Random random = new Random();
        int randomNum = random.nextInt(3) + 1;

        switch (randomNum) {
            case 1 -> {
                image = SpriteManager.scaleImage(
                    SpriteManager.loadImage("/interactive_tiles/dark_wall_broken_1.png"),
                    gamePanel.tileSize,
                    gamePanel.tileSize
                );
            }
            case 2 -> {
                image = SpriteManager.scaleImage(
                        SpriteManager.loadImage("/interactive_tiles/dark_wall_broken_2.png"),
                        gamePanel.tileSize,
                        gamePanel.tileSize
                );
            }
            case 3 -> {
                image = SpriteManager.scaleImage(
                        SpriteManager.loadImage("/interactive_tiles/dark_wall_broken_3.png"),
                        gamePanel.tileSize,
                        gamePanel.tileSize
                );
            }
        }
    }
}
