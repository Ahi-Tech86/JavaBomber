package entities;

import main.GamePanel;
import utils.SpriteManager;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class BirdEnemy extends Entity {

    GamePanel gamePanel;

    private int actionLockCounter;

    public BirdEnemy(GamePanel gamePanel, int worldX, int worldY) {
        super(worldX, worldY);
        this.gamePanel = gamePanel;

        solidArea = new Rectangle();
        solidArea.x = 16;
        solidArea.y = 14;
        solidArea.width = 30;
        solidArea.height = 40;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        setDefaultVariables();
        loadBirdSprites();
    }

    private void setDefaultVariables() {
        this.speed = 3;
        this.isMoving = false;
        this.direction = Direction.DOWN;
        this.lastDirection = Direction.DOWN;
    }

    private void loadBirdSprites() {
        Random random = new Random();
        boolean isWhiteBird = random.nextBoolean();

        if (isWhiteBird) {
            idleUp = SpriteManager.loadAndScaleSpriteFrames("/enemies/bird_white/idle/idle_up", 4, gamePanel.tileSize, gamePanel.tileSize);
            idleDown = SpriteManager.loadAndScaleSpriteFrames("/enemies/bird_white/idle/idle_down", 4, gamePanel.tileSize, gamePanel.tileSize);
            idleLeft = SpriteManager.loadAndScaleSpriteFrames("/enemies/bird_white/idle/idle_left", 4, gamePanel.tileSize, gamePanel.tileSize);
            idleRight = SpriteManager.loadAndScaleSpriteFrames("/enemies/bird_white/idle/idle_right", 4, gamePanel.tileSize, gamePanel.tileSize);

            walkUp = SpriteManager.loadAndScaleSpriteFrames("/enemies/bird_white/walk/walk_up", 8, gamePanel.tileSize, gamePanel.tileSize);
            walkDown = SpriteManager.loadAndScaleSpriteFrames("/enemies/bird_white/walk/walk_down", 8, gamePanel.tileSize, gamePanel.tileSize);
            walkLeft = SpriteManager.loadAndScaleSpriteFrames("/enemies/bird_white/walk/walk_left", 8, gamePanel.tileSize, gamePanel.tileSize);
            walkRight = SpriteManager.loadAndScaleSpriteFrames("/enemies/bird_white/walk/walk_right", 8, gamePanel.tileSize, gamePanel.tileSize);
        } else {
            idleUp = SpriteManager.loadAndScaleSpriteFrames("/enemies/bird_blue/idle/idle_up", 4, gamePanel.tileSize, gamePanel.tileSize);
            idleDown = SpriteManager.loadAndScaleSpriteFrames("/enemies/bird_blue/idle/idle_down", 4, gamePanel.tileSize, gamePanel.tileSize);
            idleLeft = SpriteManager.loadAndScaleSpriteFrames("/enemies/bird_blue/idle/idle_left", 4, gamePanel.tileSize, gamePanel.tileSize);
            idleRight = SpriteManager.loadAndScaleSpriteFrames("/enemies/bird_blue/idle/idle_right", 4, gamePanel.tileSize, gamePanel.tileSize);

            walkUp = SpriteManager.loadAndScaleSpriteFrames("/enemies/bird_blue/walk/walk_up", 8, gamePanel.tileSize, gamePanel.tileSize);
            walkDown = SpriteManager.loadAndScaleSpriteFrames("/enemies/bird_blue/walk/walk_down", 8, gamePanel.tileSize, gamePanel.tileSize);
            walkLeft = SpriteManager.loadAndScaleSpriteFrames("/enemies/bird_blue/walk/walk_left", 8, gamePanel.tileSize, gamePanel.tileSize);
            walkRight = SpriteManager.loadAndScaleSpriteFrames("/enemies/bird_blue/walk/walk_right", 8, gamePanel.tileSize, gamePanel.tileSize);
        }

    }

    private void setAction() {
        actionLockCounter++;

        if (actionLockCounter == 150) {
            Random random = new Random();
            int i = random.nextInt(100) + 1;

            if (i <= 25) {
                direction = Direction.UP;
                lastDirection = Direction.UP;
            } else if (i < 50) {
                direction = Direction.DOWN;
                lastDirection = Direction.DOWN;
            } else if (i < 75) {
                direction = Direction.LEFT;
                lastDirection = Direction.LEFT;
            } else {
                direction = Direction.RIGHT;
                lastDirection = Direction.RIGHT;
            }

            actionLockCounter = 0;
        }
    }

    @Override
    public void update() {
        setAction();

        collisionOn = false;
        gamePanel.collisionChecker.checkTile(this);
        gamePanel.collisionChecker.checkObject(this, false);

        if (!collisionOn) {
            switch (direction) {
                case UP -> worldY -= speed;
                case DOWN -> worldY += speed;
                case LEFT -> worldX -= speed;
                case RIGHT -> worldX += speed;
            }
        }

        spriteCounter++;
        if (spriteCounter > 10) {
            spriteNum++;
            spriteCounter = 0;

            if (isMoving) {
                if (spriteNum > 8) {
                    spriteNum = 1;
                }
            } else {
                if (spriteNum > 4) {
                    spriteNum = 1;
                }
            }
        }
    }

    @Override
    public void draw(Graphics2D graphics2D, GamePanel gamePanel) {
        BufferedImage image = null;

        if (isMoving) {
            switch (direction) {
                case Direction.UP -> image = walkUp[spriteNum - 1];
                case Direction.DOWN -> image = walkDown[spriteNum - 1];
                case Direction.LEFT -> image = walkLeft[spriteNum - 1];
                case Direction.RIGHT -> image = walkRight[spriteNum - 1];
            }

        } else {
            switch (lastDirection) {
                case Direction.UP -> image = idleUp[spriteNum - 1];
                case Direction.DOWN -> image = idleDown[spriteNum - 1];
                case Direction.LEFT -> image = idleLeft[spriteNum - 1];
                case Direction.RIGHT -> image = idleRight[spriteNum - 1];
            }
        }

        int screenX = worldX - gamePanel.player.worldX + gamePanel.player.screenX;
        int screenY = worldY - gamePanel.player.worldY + gamePanel.player.screenY;

        if (
                worldX + gamePanel.tileSize > gamePanel.player.worldX - gamePanel.player.screenX &&
                worldX - gamePanel.tileSize < gamePanel.player.worldX + gamePanel.player.screenX &&
                worldY + gamePanel.tileSize > gamePanel.player.worldY - gamePanel.player.screenY &&
                worldY - gamePanel.tileSize < gamePanel.player.worldY + gamePanel.player.screenY)
        {
            graphics2D.drawImage(image, screenX, screenY, null);
        }
    }
}
