package explosions;

import components.GamePanel;
import utils.SpriteManager;

import java.awt.image.BufferedImage;

public class DynamitePack extends ExplosiveEntity {

    public DynamitePack(int worldX, int worldY, GamePanel gamePanel, int explosiveRangeBonus) {
        super(worldX, worldY, gamePanel);
        this.explosionArea = (byte) (2 + explosiveRangeBonus);
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

        createExplosionEffect(worldX, worldY);

        // RIGHT DIRECTION
        propagateExplosion(1, 0);
        // LEFT DIRECTION
        propagateExplosion(-1, 0);
        // UP DIRECTION
        propagateExplosion(0, -1);
        // DOWN DIRECTION
        propagateExplosion(0, 1);

        gamePanel.playSE(0);
    }
}
