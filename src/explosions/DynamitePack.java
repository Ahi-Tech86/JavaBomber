package explosions;

import main.GamePanel;
import utils.SpriteManager;

import java.awt.image.BufferedImage;

public class DynamitePack extends ExplosiveEntity {

    public DynamitePack(int worldX, int worldY, GamePanel gamePanel) {
        super(worldX, worldY, gamePanel);
        this.explosionTimer = 90;
        this.isExploded = false;
        this.explosionFrames = new BufferedImage[]{
                SpriteManager.loadImage("/objects/dynamite-pack/dynamite_pack_0.png"),
                SpriteManager.loadImage("/objects/dynamite-pack/dynamite_pack_1.png"),
                SpriteManager.loadImage("/objects/dynamite-pack/dynamite_pack_2.png"),
                SpriteManager.loadImage("/objects/dynamite-pack/dynamite_pack_3.png"),
                SpriteManager.loadImage("/objects/dynamite-pack/dynamite_pack_4.png"),
                SpriteManager.loadImage("/objects/dynamite-pack/dynamite_pack_5.png"),
                SpriteManager.loadImage("/objects/dynamite-pack/dynamite_pack_6.png"),
                SpriteManager.loadImage("/objects/dynamite-pack/dynamite_pack_7.png"),
                SpriteManager.loadImage("/objects/dynamite-pack/dynamite_pack_8.png")
        };
    }

    protected void explode() {
        isExploded = true;

        for (int x = -2; x <= 2; x++) {
            for (int y = -2; y <= 2; y++) {
                createExplosionEffect(worldX + x * gamePanel.tileSize, worldY + y * gamePanel.tileSize);
            }
        }
    }
}
