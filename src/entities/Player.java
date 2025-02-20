package entities;

import main.GamePanel;
import main.KeyHandler;
import utils.SpriteManager;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends Entity {

    GamePanel gamePanel;
    KeyHandler keyHandler;

    public Player(GamePanel gamePanel, KeyHandler keyHandler) {
        this.gamePanel = gamePanel;
        this.keyHandler = keyHandler;

        setDefaultVariables();
        loadPlayerSprites();
    }

    private void setDefaultVariables() {
        this.x = 100;
        this.y = 100;
        this.speed = 4;
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
        if (keyHandler.upPressed) {
            this.direction = Direction.UP;
            this.lastDirection = Direction.UP;
            this.y -= this.speed;
        } else if (keyHandler.downPressed) {
            this.direction = Direction.DOWN;
            this.lastDirection = Direction.DOWN;
            this.y += this.speed;
        } else if (keyHandler.leftPressed) {
            this.direction = Direction.LEFT;
            this.lastDirection = Direction.LEFT;
            this.x -= this.speed;
        } else if (keyHandler.rightPressed) {
            this.direction = Direction.RIGHT;
            this.lastDirection = Direction.RIGHT;
            this.x += this.speed;
        }

        spriteCounter++;
        if (spriteCounter > 10) {
            spriteCounter = 0;
            spriteNum++;

            if (spriteNum > 8) {
                spriteNum = 1;
            }
        }
    }

    public void draw(Graphics2D graphics2D) {
        BufferedImage image = switch (direction) {
            case Direction.UP -> walkUp[spriteNum - 1];
            case Direction.DOWN -> walkDown[spriteNum - 1];
            case Direction.LEFT -> walkLeft[spriteNum - 1];
            case Direction.RIGHT -> walkRight[spriteNum - 1];
            default -> null;
        };

        graphics2D.drawImage(image, x, y, gamePanel.tileSize, gamePanel.tileSize, null);
    }
}
