package explosions;

import main.GameObject;
import main.GamePanel;
import observer.UpdatableObserver;
import utils.SpriteManager;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ExplosionEffect extends GameObject implements UpdatableObserver {

    public boolean isActive = true;
    GamePanel gamePanel;
    private int explosionEffectTimer = 90;

    private int spriteNum = 1;
    private int spriteCounter = 0;
    private final BufferedImage[] effectFrames;
    public Rectangle solidArea = new Rectangle();
    public int solidAreaDefaultX, solidAreaDefaultY;

    public ExplosionEffect(GamePanel gamePanel, int worldX, int worldY) {
        super(worldX, worldY);
        this.gamePanel = gamePanel;
        this.worldX = worldX;
        this.worldY = worldY;
        this.solidArea.x = 0;
        this.solidArea.y = 0;
        this.solidArea.width = 64;
        this.solidArea.height = 64;

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

    @Override
    public void update() {
        if (isActive) {
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
        } else {
            gamePanel.removeObserver(this);
            gamePanel.explosionEffectList.remove(this);
        }

    }

    @Override
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
