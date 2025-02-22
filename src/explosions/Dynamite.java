package explosions;

import main.GamePanel;
import utils.SpriteManager;

import java.awt.image.BufferedImage;

public class Dynamite extends ExplosiveEntity {

    public Dynamite(int worldX, int worldY, GamePanel gamePanel) {
        super(worldX, worldY, gamePanel);
        this.explosionArea = 1;
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

        createExplosionEffect(worldX, worldY);

        // RIGHT DIRECTION
        propagateExplosion(1, 0);
        // LEFT DIRECTION
        propagateExplosion(-1, 0);
        // UP DIRECTION
        propagateExplosion(0, -1);
        // DOWN DIRECTION
        propagateExplosion(0, 1);
    }
}
