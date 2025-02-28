package entities;

import main.GameObject;
import main.GamePanel;
import observer.UpdatableObserver;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Entity extends GameObject implements UpdatableObserver {

    public int life;
    public int maxLife;
    public int speed;
    public boolean isMoving;
    public Direction direction;
    public Direction lastDirection;

    // COLLISION
    public boolean collisionOn = false;

    // SPRITES
    public int spriteNum = 1;
    public int spriteCounter = 0;

    // IDLE
    public BufferedImage[] idleUp;
    public BufferedImage[] idleDown;
    public BufferedImage[] idleLeft;
    public BufferedImage[] idleRight;

    // WALK
    public BufferedImage[] walkUp;
    public BufferedImage[] walkDown;
    public BufferedImage[] walkLeft;
    public BufferedImage[] walkRight;

    // FOR ATTACKING
    public boolean isPlayer;
    public boolean invincible = false;
    public int invincibleCounter = 0;

    public boolean alive = true;
    public boolean dying = false;
    public int dyingCounter = 0;

    public Entity(GamePanel gamePanel, int worldX, int worldY) {
        super(gamePanel, worldX, worldY);
    }

    protected BufferedImage getCurrentImage() {
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

        return image;
    }

    protected void updatePosition() {
        if (!collisionOn && !dying) {
            switch (direction) {
                case UP -> worldY -= speed;
                case DOWN -> worldY += speed;
                case LEFT -> worldX -= speed;
                case RIGHT -> worldX += speed;
            }
        }
    }

    @Override
    public void update() {
    }
}
