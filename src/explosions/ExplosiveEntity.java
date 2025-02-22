package explosions;

import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ExplosiveEntity {

    public GamePanel gamePanel;

    protected int worldX;
    protected int worldY;
    public boolean isExploded;
    protected byte explosionArea;
    protected int explosionTimer;

    protected int spriteNum = 1;
    protected int spriteCounter = 0;
    protected BufferedImage[] explosionFrames;

    public ExplosiveEntity(int worldX, int worldY, GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        int offset = gamePanel.tileSize / 2;
        this.worldX = ((int) ((worldX + offset) / gamePanel.tileSize)) * gamePanel.tileSize;
        this.worldY = ((int) ((worldY + offset) / gamePanel.tileSize)) * gamePanel.tileSize;
    }

    public void draw(Graphics2D graphics2D, GamePanel gamePanel) {
        BufferedImage image = explosionFrames[spriteNum - 1];

        int screenX = worldX - gamePanel.player.worldX + gamePanel.player.screenX;
        int screenY = worldY - gamePanel.player.worldY + gamePanel.player.screenY;

        graphics2D.drawImage(
                image,
                screenX + 8,
                screenY + 8,
                (gamePanel.tileSize / 2) + 16 ,
                (gamePanel.tileSize / 2) + 16,
                null
        );
    }

    public void update() {
        if (explosionTimer > 0) {
            explosionTimer--;
        } else if (!isExploded) {
            explode();
        }

        spriteCounter++;
        if (spriteCounter > 10) {
            spriteCounter = 0;
            spriteNum++;

            if (spriteNum > explosionFrames.length) {
                spriteNum = 1;
                spriteCounter = 0;
            }
        }
    }

    protected void explode() {
    }

    protected void createExplosionEffect(int worldX, int worldY) {
        for (int i = 0; i < gamePanel.explosionEffectsList.length; i++) {
            if (gamePanel.explosionEffectsList[i] == null) {
                if (!gamePanel.collisionChecker.checkTileSolidity(worldX, worldY)) {
                    gamePanel.explosionEffectsList[i] = new ExplosionEffect(worldX, worldY);
                    break;
                }
            }
        }
    }

    protected void propagateExplosion(int dx, int dy) {
        for (int i = 1; i <= explosionArea; i++) {
            int targetX = worldX + i * dx * gamePanel.tileSize;
            int targetY = worldY + i * dy * gamePanel.tileSize;

            if (gamePanel.collisionChecker.checkTileSolidity(targetX, targetY)) {
                break;
            }

            createExplosionEffect(targetX, targetY);
        }
    }
}
