package explosions;

import main.GamePanel;
import utils.SpriteManager;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ExplosionEffect {

    public boolean isActive = true;
    GamePanel gamePanel;
    private final int worldX;
    private final int worldY;
    private int explosionEffectTimer = 90;

    private int spriteNum = 1;
    private int spriteCounter = 0;
    private final BufferedImage[] effectFrames;

    public ExplosionEffect(GamePanel gamePanel, int worldX, int worldY) {
        this.gamePanel = gamePanel;
        this.worldX = worldX;
        this.worldY = worldY;

        String[] effectPaths = {
                "/effects/explosion_0.png",
                "/effects/explosion_1.png",
                "/effects/explosion_2.png",
                "/effects/explosion_3.png",
                "/effects/explosion_4.png",
                "/effects/explosion_5.png"
        };

        effectFrames = SpriteManager.loadAndSaveSpriteFrames(effectPaths, gamePanel.tileSize, gamePanel.tileSize);
    }

    public void update() {
        explosionEffectTimer--;
        if (explosionEffectTimer == 0) {
            isActive = false;
        }

        spriteCounter++;
        if (spriteCounter > 15) {
            spriteNum++;
            spriteCounter = 0;

            if (spriteNum > effectFrames.length) {
                spriteNum = 1;
            }
        }
    }

    public void draw(Graphics2D graphics2D, GamePanel gamePanel) {
        BufferedImage image = effectFrames[spriteNum - 1];

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
