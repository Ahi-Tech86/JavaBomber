package entities;

import java.awt.image.BufferedImage;

public class Entity {
    public int x, y;
    public int speed;
    public boolean isMoving;
    public Direction direction;
    public Direction lastDirection;

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

    public int spriteNum = 1;
    public int spriteCounter = 0;
}
