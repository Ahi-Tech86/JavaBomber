package main;

import entities.BirdEnemy;
import objects.Key;

public class AssetSetter {

    GamePanel gamePanel;

    public AssetSetter(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void setObjects() {
        gamePanel.objectsList.add(new Key(gamePanel.tileSize, 5 * gamePanel.tileSize));
    }

    public void setEnemies() {
        gamePanel.enemiesList.add(new BirdEnemy(gamePanel, 10 * gamePanel.tileSize, 1 * gamePanel.tileSize));
        gamePanel.enemiesList.add(new BirdEnemy(gamePanel, 14 * gamePanel.tileSize, 1 * gamePanel.tileSize));
    }
}
