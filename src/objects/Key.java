package objects;

import utils.SpriteManager;

import java.awt.*;

public class Key extends SuperObject {

    public Key(int worldX, int worldY) {
        super(worldX, worldY);
        name = "Key";
        collision = false;
        this.solidArea = new Rectangle(0, 0, 64, 64);
        this.solidAreaDefaultX = solidArea.x;
        this.solidAreaDefaultY = solidArea.y;
        image = SpriteManager.loadImage("/objects/key/key_0.png");
        image = SpriteManager.scaleImage(image, tileSizeHalf, tileSizeHalf);
    }
}
