package objects;

import utils.SpriteManager;

public class Key extends SuperObject {

    public Key(int worldX, int worldY) {
        super(worldX, worldY);
        name = "Key";
        collision = false;
        this.worldX = worldX;
        this.worldY = worldY;
        image = SpriteManager.loadImage("/objects/key/key_0.png");
        image = SpriteManager.scaleImage(image, tileSizeHalf, tileSizeHalf);
    }
}
