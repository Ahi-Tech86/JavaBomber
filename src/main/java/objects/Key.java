package objects;

import components.GamePanel;
import utils.SpriteManager;

import java.awt.*;

public class Key extends SuperObject {

    public Key(GamePanel gamePanel, int worldX, int worldY) {
        super(gamePanel, worldX, worldY);
        name = "Key";
        collision = false;
        this.solidArea = new Rectangle(16, 16, 32, 32);
        this.solidAreaDefaultX = solidArea.x;
        this.solidAreaDefaultY = solidArea.y;
        image = SpriteManager.loadImage("/objects/key/key_0.png");
        image = SpriteManager.scaleImage(image, tileSizeHalf, tileSizeHalf);
    }
}
