package main;

import java.awt.*;

public class GameObject implements Renderable {
    GamePanel gamePanel;

    public int worldX;
    public int worldY;
    public Rectangle solidArea;
    public int solidAreaDefaultX;
    public int solidAreaDefaultY;

    public GameObject(GamePanel gamePanel, int worldX, int worldY) {
        this.gamePanel = gamePanel;
        this.worldX = worldX;
        this.worldY = worldY;
        this.solidArea = new Rectangle(0, 0, 64, 64);
        this.solidAreaDefaultX = 0;
        this.solidAreaDefaultY = 0;
    }

    @Override
    public void draw(Graphics2D graphics2D, GamePanel gamePanel) {
    }

    public boolean isOnScreen() {
        return
                worldX + gamePanel.tileSize > gamePanel.player.worldX - gamePanel.player.screenX &&
                worldX - gamePanel.tileSize < gamePanel.player.worldX + gamePanel.player.screenX &&
                worldY + gamePanel.tileSize > gamePanel.player.worldY - gamePanel.player.screenY &&
                worldY - gamePanel.tileSize < gamePanel.player.worldY + gamePanel.player.screenY;
    }
}
