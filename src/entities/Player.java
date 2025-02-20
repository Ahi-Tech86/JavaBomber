package entities;

import main.GamePanel;
import main.KeyHandler;
import utils.SpriteManager;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends Entity {

    GamePanel gamePanel;
    KeyHandler keyHandler;

    public final int screenX;
    public final int screenY;

    public Player(GamePanel gamePanel, KeyHandler keyHandler) {
        this.gamePanel = gamePanel;
        this.keyHandler = keyHandler;

        screenX = gamePanel.screenWidth / 2 - (gamePanel.tileSize / 2);
        screenY = gamePanel.screenHeight / 2 - (gamePanel.tileSize / 2);

        solidArea = new Rectangle();
        solidArea.x = 20;
        solidArea.y = 14;
        solidArea.width = 22;
        solidArea.height = 40;

        setDefaultVariables();
        loadPlayerSprites();
    }

    private void setDefaultVariables() {
        this.worldX = gamePanel.tileSize;
        this.worldY = gamePanel.tileSize;
        this.speed = 4;
        this.isMoving = false;
        this.direction = Direction.DOWN;
        this.lastDirection = Direction.DOWN;
    }

    private void loadPlayerSprites() {
        idleUp = new BufferedImage[4];
        idleDown = new BufferedImage[4];
        idleLeft = new BufferedImage[4];
        idleRight = new BufferedImage[4];

        walkUp = new BufferedImage[8];
        walkDown = new BufferedImage[8];
        walkLeft = new BufferedImage[8];
        walkRight = new BufferedImage[8];

        for (int i = 0; i < 4; i++) {
            idleUp[i] = SpriteManager.loadImage("/character/idle/idle_up_" + i + ".png");
            idleDown[i] = SpriteManager.loadImage("/character/idle/idle_down_" + i + ".png");
            idleLeft[i] = SpriteManager.loadImage("/character/idle/idle_left_" + i + ".png");
            idleRight[i] = SpriteManager.loadImage("/character/idle/idle_right_" + i + ".png");
        }

        for (int i = 0; i < 8; i++) {
            walkUp[i] = SpriteManager.loadImage("/character/walk/walk_up_" + i + ".png");
            walkDown[i] = SpriteManager.loadImage("/character/walk/walk_down_" + i + ".png");
            walkLeft[i] = SpriteManager.loadImage("/character/walk/walk_left_" + i + ".png");
            walkRight[i] = SpriteManager.loadImage("/character/walk/walk_right_" + i + ".png");
        }
    }

    public void update() {
        if (keyHandler.upPressed || keyHandler.downPressed || keyHandler.leftPressed || keyHandler.rightPressed) {

            if (keyHandler.upPressed) {
                isMoving = true;
                this.direction = Direction.UP;
                this.lastDirection = Direction.UP;
            } else if (keyHandler.downPressed) {
                isMoving = true;
                this.direction = Direction.DOWN;
                this.lastDirection = Direction.DOWN;
            } else if (keyHandler.leftPressed) {
                isMoving = true;
                this.direction = Direction.LEFT;
                this.lastDirection = Direction.LEFT;
            } else if (keyHandler.rightPressed) {
                isMoving = true;
                this.direction = Direction.RIGHT;
                this.lastDirection = Direction.RIGHT;
            }

            // CHECK PLAYER COLLISION
            collisionOn = false;
            gamePanel.collisionChecker.checkTile(this);

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
                spriteCounter = 0;
                spriteNum++;

                if (spriteNum > 8) {
                    spriteNum = 1;
                }
            }

        } else {
            isMoving = false;

            spriteCounter++;
            if (spriteCounter > 10) {
                spriteCounter = 0;
                spriteNum++;

                if (spriteNum > 4) {
                    spriteNum = 1;
                }
            }
        }


    }

    public void draw(Graphics2D graphics2D) {
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

        graphics2D.drawImage(image, screenX, screenY, gamePanel.tileSize, gamePanel.tileSize, null);

        graphics2D.setColor(Color.RED);
        graphics2D.drawRect(screenX + solidArea.x, screenY + solidArea.y, solidArea.width, solidArea.height);
    }
}
