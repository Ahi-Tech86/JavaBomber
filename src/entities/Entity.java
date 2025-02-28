package entities;

import explosions.ExplosionEffect;
import main.GameObject;
import main.GamePanel;
import observer.UpdatableObserver;
import tile_interactive.InteractiveTile;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

public class Entity extends GameObject implements UpdatableObserver {

    GamePanel gamePanel;

    public int life;
    public int maxLife;
    public int speed;
    public boolean isMoving;
    public Direction direction;
    public Direction lastDirection;

    protected boolean onPath = false;

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
        this.gamePanel = gamePanel;
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

    protected void checkCollision() {
        collisionOn = false;
        gamePanel.collisionChecker.checkTile(this);
        gamePanel.collisionChecker.checkObject(this, false);
        gamePanel.collisionChecker.checkEntity(this, (ArrayList<Entity>) gamePanel.enemiesList);
        gamePanel.collisionChecker.checkInteractiveTiles(this, (ArrayList<InteractiveTile>) gamePanel.interactiveTileList);
        boolean contactPlayer = gamePanel.collisionChecker.checkPlayer(this);
        boolean exploded = gamePanel.collisionChecker.checkEntityInExplosionArea(this, (ArrayList<ExplosionEffect>) gamePanel.explosionEffectList);

        if (exploded) {
            this.dying = true;
        }

        if (!this.isPlayer && contactPlayer) {
            if (!gamePanel.player.invincible) {
                gamePanel.player.life -= 1;
                gamePanel.player.invincible = true;
            }
        }
    }

    protected void searchPath(int goalCol, int goalRow) {
        int startCol = (worldX + solidArea.x) / gamePanel.tileSize;
        int startRow = (worldY + solidArea.y) / gamePanel.tileSize;

        gamePanel.pathFinder.setNode(startCol, startRow, goalCol, goalRow);

        if (gamePanel.pathFinder.search()) {
            // Next worldX & worldY
            int nextX = gamePanel.pathFinder.pathList.get(0).col * gamePanel.tileSize;
            int nextY = gamePanel.pathFinder.pathList.get(0).row * gamePanel.tileSize;

            // Entity's solidArea position
            int entityLeftX = worldX + solidArea.x;
            int entityRightX = worldX + solidArea.x + solidArea.width;
            int entityTopY = worldY + solidArea.y;
            int entityBottomY = worldY + solidArea.y + solidArea.height;

            if (entityTopY > nextY && entityLeftX >= nextX && entityRightX < nextX + gamePanel.tileSize) {
                direction = Direction.UP;
                lastDirection = Direction.UP;
            } else if (entityTopY < nextY && entityLeftX >= nextX && entityRightX < nextX + gamePanel.tileSize) {
                direction = Direction.DOWN;
                lastDirection = Direction.DOWN;
            } else if (entityTopY >= nextY && entityBottomY < nextY + gamePanel.tileSize) {
                // left or right
                if (entityLeftX > nextX) {
                    direction = Direction.LEFT;
                    lastDirection = Direction.LEFT;
                }

                else if (entityLeftX < nextX) {
                    direction = Direction.RIGHT;
                    lastDirection = Direction.RIGHT;
                }
            } else if (entityTopY > nextY && entityLeftX > nextX) {
                // up or left
                direction = Direction.UP;
                lastDirection = Direction.UP;
                checkCollision();

                if (collisionOn) {
                    direction = Direction.LEFT;
                    lastDirection = Direction.LEFT;
                }
            } else if (entityTopY > nextY && entityLeftX < nextX) {
                // up or right
                direction = Direction.UP;
                lastDirection = Direction.UP;
                checkCollision();

                if (collisionOn) {
                    direction = Direction.RIGHT;
                    lastDirection = Direction.RIGHT;
                }
            } else if (entityTopY < nextY && entityLeftX > nextX) {
                // down or left
                direction = Direction.DOWN;
                lastDirection = Direction.DOWN;
                checkCollision();

                if (collisionOn) {
                    direction = Direction.LEFT;
                    lastDirection = Direction.LEFT;
                }
            } else if (entityTopY < nextY && entityLeftX < nextX) {
                // down or right
                direction = Direction.DOWN;
                lastDirection = Direction.DOWN;
                checkCollision();

                if (collisionOn) {
                    direction = Direction.RIGHT;
                    lastDirection = Direction.RIGHT;
                }
            }
        }
    }

    public void checkStopChasingOrNot(Entity target, int distance, int rate) {
        if (getTileDistance(target) > distance) {
            int i = new Random().nextInt(rate);
            if (i == 0) {
                onPath = false;
            }
        }
    }

    public int getTileDistance(Entity target) {
        int tileDistance = (getXdistance(target) + getYdistance(target)) / gamePanel.tileSize;
        return tileDistance;
    }

    public int getXdistance(Entity target) {
        int xDistance = Math.abs(worldX - target.worldX);
        return xDistance;
    }

    public int getYdistance(Entity target) {
        int yDistance = Math.abs(worldY - target.worldY);
        return yDistance;
    }

    public int getGoalCol(Entity target) {
        int goalCol = (target.worldX + target.solidArea.x) / gamePanel.tileSize;
        return goalCol;
    }

    public int getGoalRow(Entity target) {
        int goalRow = (target.worldY + target.solidArea.y) / gamePanel.tileSize;
        return goalRow;
    }

    public void checkStartChasingOrNot(Entity target, int distance, int rate) {
        if (getTileDistance(target) < distance) {
            int i = new Random().nextInt(rate);
            if (i == 0) {
                onPath = true;
            }
        }
    }

    @Override
    public void update() {
    }
}
