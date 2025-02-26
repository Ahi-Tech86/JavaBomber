package tile_interactive;

import main.GameObject;
import main.GamePanel;
import observer.UpdatableObserver;

import java.awt.*;
import java.awt.image.BufferedImage;

public class InteractiveTile extends GameObject implements UpdatableObserver {

    GamePanel gamePanel;

    public boolean destructed = false;
    protected BufferedImage image;
    public boolean destructible = false;

    public InteractiveTile(GamePanel gamePanel, int worldX, int worldY) {
        super(worldX, worldY);
        this.gamePanel = gamePanel;
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Graphics2D graphics2D, GamePanel gamePanel) {
        int screenX = worldX - gamePanel.player.worldX + gamePanel.player.screenX;
        int screenY = worldY - gamePanel.player.worldY + gamePanel.player.screenY;

        if (
                worldX + gamePanel.tileSize > gamePanel.player.worldX - gamePanel.player.screenX &&
                worldX - gamePanel.tileSize < gamePanel.player.worldX + gamePanel.player.screenX &&
                worldY + gamePanel.tileSize > gamePanel.player.worldY - gamePanel.player.screenY &&
                worldY - gamePanel.tileSize < gamePanel.player.worldY + gamePanel.player.screenY
        ) {
            graphics2D.drawImage(image, screenX, screenY, null);
        }
    }
}
