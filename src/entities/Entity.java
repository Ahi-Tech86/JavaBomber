package entities;

import main.GameObject;
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
    public Rectangle solidArea;
    public boolean collisionOn = false;
    public int solidAreaDefaultX, solidAreaDefaultY;

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

    public Entity(int worldX, int worldY) {
        super(worldX, worldY);
    }

    @Override
    public void update() {
    }
}
