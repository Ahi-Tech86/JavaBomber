package main;

import objects.Key;

public class AssetSetter {

    GamePanel gamePanel;

    public AssetSetter(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void setObjects() {
        gamePanel.staticObjectList.add(new Key(1 * gamePanel.tileSize, 5 * gamePanel.tileSize));
    }
}
