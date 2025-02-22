package explosions;

import main.GamePanel;
import utils.SpriteManager;

import java.awt.image.BufferedImage;

public class Dynamite extends ExplosiveEntity {

    public Dynamite(int worldX, int worldY, GamePanel gamePanel) {
        super(worldX, worldY, gamePanel);
        this.explosionTimer = 90;
        this.isExploded = false;
        this.explosionFrames = new BufferedImage[]{
                SpriteManager.loadImage("/objects/dynamite/dynamite_0.png"),
                SpriteManager.loadImage("/objects/dynamite/dynamite_1.png"),
                SpriteManager.loadImage("/objects/dynamite/dynamite_2.png"),
                SpriteManager.loadImage("/objects/dynamite/dynamite_3.png"),
                SpriteManager.loadImage("/objects/dynamite/dynamite_4.png"),
                SpriteManager.loadImage("/objects/dynamite/dynamite_5.png"),
                SpriteManager.loadImage("/objects/dynamite/dynamite_6.png"),
                SpriteManager.loadImage("/objects/dynamite/dynamite_7.png"),
                SpriteManager.loadImage("/objects/dynamite/dynamite_8.png")
        };
    }

    protected void explode() {
        isExploded = true;

        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                createExplosionEffect(worldX + x * gamePanel.tileSize, worldY + y * gamePanel.tileSize);
            }
        }
    }
}
