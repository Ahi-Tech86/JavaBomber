package tile_interactive;

import explosions.ExplosionEffect;
import main.GameObject;
import main.GamePanel;
import observer.UpdatableObserver;

import java.awt.*;
import java.awt.image.BufferedImage;

public class InteractiveTile extends GameObject implements UpdatableObserver {

    GamePanel gamePanel;

    protected BufferedImage image;

    public InteractiveTile(GamePanel gamePanel, int worldX, int worldY) {
        super(gamePanel, worldX, worldY);
        this.gamePanel = gamePanel;
    }

    @Override
    public void update() {
    }

    @Override
    public void draw(Graphics2D graphics2D, GamePanel gamePanel) {
        int screenX = worldX - gamePanel.player.worldX + gamePanel.player.screenX;
        int screenY = worldY - gamePanel.player.worldY + gamePanel.player.screenY;

        if (isOnScreen()) {
            graphics2D.drawImage(image, screenX, screenY, null);
        }
    }
}
