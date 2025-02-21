package main;

import objects.Key;

public class AssetSetter {

    GamePanel gamePanel;

    public AssetSetter(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void setObjects() {
        gamePanel.objectsList[0] = new Key();
        gamePanel.objectsList[0].worldX = 1 * gamePanel.tileSize;
        gamePanel.objectsList[0].worldY = 5 * gamePanel.tileSize;
    }
}
