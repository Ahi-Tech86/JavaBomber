package objects;

import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SuperObject {

    public String name;
    public BufferedImage image;
    public boolean collision = false;
    public int worldX, worldY;
    protected int tileSizeHalf = 64 / 2;

    public void draw(Graphics2D graphics2D, GamePanel gamePanel) {
        int screenX = worldX - gamePanel.player.worldX + gamePanel.player.screenX;
        int screenY = worldY - gamePanel.player.worldY + gamePanel.player.screenY;

        if (
                worldX + gamePanel.tileSize > gamePanel.player.worldX - gamePanel.player.screenX &&
                worldX - gamePanel.tileSize < gamePanel.player.worldX + gamePanel.player.screenX &&
                worldY + gamePanel.tileSize > gamePanel.player.worldY - gamePanel.player.screenY &&
                worldY - gamePanel.tileSize < gamePanel.player.worldY + gamePanel.player.screenY
        ) {
            graphics2D.drawImage(image, screenX + 16, screenY + 16, null);
        }
    }
}
