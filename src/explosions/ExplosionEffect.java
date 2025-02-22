package explosions;

import main.GamePanel;
import utils.SpriteManager;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ExplosionEffect {
    private int worldX;
    private int worldY;
    public boolean isActive = true;
    private int explosionEffectTimer = 90;

    private int spriteNum = 1;
    private int spriteCounter = 0;
    private BufferedImage[] effectFrames;

    public ExplosionEffect(int worldX, int worldY) {
        this.worldX = worldX;
        this.worldY = worldY;

        effectFrames = new BufferedImage[]{
                SpriteManager.loadImage("/effects/explosion_0.png"),
                SpriteManager.loadImage("/effects/explosion_1.png"),
                SpriteManager.loadImage("/effects/explosion_2.png"),
                SpriteManager.loadImage("/effects/explosion_3.png"),
                SpriteManager.loadImage("/effects/explosion_4.png"),
                SpriteManager.loadImage("/effects/explosion_5.png"),
        };
    }

    public void update() {
        explosionEffectTimer--;
        if (explosionEffectTimer == 0) {
            isActive = false;
        }

        spriteCounter++;
        if (spriteCounter > 10) {
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

        graphics2D.drawImage(
                image,
                screenX,
                screenY,
                gamePanel.tileSize,
                gamePanel.tileSize,
                null
        );
    }
}
