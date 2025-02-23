package objects;

import utils.SpriteManager;

public class Key extends SuperObject {

    public Key() {
        name = "Key";
        collision = false;
        image = SpriteManager.loadImage("/objects/key/key_0.png");
    }

    public Key(int worldX, int worldY) {
        name = "Key";
        collision = false;
        this.worldX = worldX;
        this.worldY = worldY;
        image = SpriteManager.loadImage("/objects/key/key_0.png");
        image = SpriteManager.scaleImage(image, tileSizeHalf, tileSizeHalf);
    }
}
