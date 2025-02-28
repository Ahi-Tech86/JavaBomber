package objects;

import main.GamePanel;
import utils.SpriteManager;

import java.awt.*;

public class ExplosionRangeBonus extends SuperObject {

    public ExplosionRangeBonus(GamePanel gamePanel, int worldX, int worldY) {
        super(gamePanel, worldX, worldY);
        name = "Explosion Range Bonus";
        collision = false;
        this.solidArea = new Rectangle(16, 16, 32, 32);
        this.solidAreaDefaultX = solidArea.x;
        this.solidAreaDefaultY = solidArea.y;
        image = SpriteManager.loadImage("/objects/bonus_for_explosive.png");
        image = SpriteManager.scaleImage(image, tileSizeHalf, tileSizeHalf);
    }
}
