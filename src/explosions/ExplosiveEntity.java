package explosions;

import main.GamePanel;
import observer.UpdatableObserver;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ExplosiveEntity implements UpdatableObserver {

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

    @Override
    public void update() {
        if (!isExploded) {
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
        } else {
            gamePanel.removeObserver(this);
            gamePanel.explosiveEntityList.remove(this);
        }

    }

    protected void explode() {
    }

    protected void createExplosionEffect(int worldX, int worldY) {
        ExplosionEffect explosionEffect = new ExplosionEffect(gamePanel, worldX, worldY);
        gamePanel.explosionEffectList.add(explosionEffect);
        gamePanel.addObserver(explosionEffect);
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
