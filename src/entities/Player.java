package entities;

import explosions.DynamitePack;
import main.GamePanel;
import main.KeyHandler;
import observer.UpdatableObserver;
import utils.SpriteManager;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Player extends Entity implements UpdatableObserver {

    GamePanel gamePanel;
    KeyHandler keyHandler;

    public final int screenX;
    public final int screenY;

    public int points;
    private int throwCooldown;
    private int throwCounter;

    public Player(GamePanel gamePanel, KeyHandler keyHandler) {
        super(gamePanel.tileSize, gamePanel.tileSize);
        this.gamePanel = gamePanel;
        this.keyHandler = keyHandler;

        screenX = gamePanel.screenWidth / 2 - (gamePanel.tileSize / 2);
        screenY = gamePanel.screenHeight / 2 - (gamePanel.tileSize / 2);

        solidArea = new Rectangle();
        solidArea.x = 20;
        solidArea.y = 14;
        solidArea.width = 22;
        solidArea.height = 40;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        points = 0;

        throwCooldown = 20;
        throwCounter = 0;

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
        this.maxLife = 2;
        this.life = this.maxLife;
        this.isPlayer = true;
    }

    private void loadPlayerSprites() {
        idleUp = SpriteManager.loadAndScaleSpriteFrames("/character/idle/idle_up", 4, gamePanel.tileSize, gamePanel.tileSize);
        idleDown = SpriteManager.loadAndScaleSpriteFrames("/character/idle/idle_down", 4, gamePanel.tileSize, gamePanel.tileSize);
        idleLeft = SpriteManager.loadAndScaleSpriteFrames("/character/idle/idle_left", 4, gamePanel.tileSize, gamePanel.tileSize);
        idleRight = SpriteManager.loadAndScaleSpriteFrames("/character/idle/idle_right", 4, gamePanel.tileSize, gamePanel.tileSize);

        walkUp = SpriteManager.loadAndScaleSpriteFrames("/character/walk/walk_up", 8, gamePanel.tileSize, gamePanel.tileSize);
        walkDown = SpriteManager.loadAndScaleSpriteFrames("/character/walk/walk_down", 8, gamePanel.tileSize, gamePanel.tileSize);
        walkLeft = SpriteManager.loadAndScaleSpriteFrames("/character/walk/walk_left", 8, gamePanel.tileSize, gamePanel.tileSize);
        walkRight = SpriteManager.loadAndScaleSpriteFrames("/character/walk/walk_right", 8, gamePanel.tileSize, gamePanel.tileSize);
    }

    private void pickUpObject(int index) {
        if (index != 999) {
            gamePanel.objectsList.remove(index);
            gamePanel.playSE(2);
        }
    }

    private void contactWithEnemy(int index) {
        if (index != 999) {
            if (!invincible) {
                life -= 1;
                invincible = true;
            }
        }
    }

    @Override
    public void update() {
        if (throwCounter > 0) {
            throwCounter--;
        }

        if (keyHandler.ePressed && throwCounter == 0) {
            DynamitePack dynamitePack = new DynamitePack(this.worldX, this.worldY, gamePanel);
            gamePanel.explosiveEntityList.add(dynamitePack);
            gamePanel.addObserver(dynamitePack);
            gamePanel.playSE(1);
            throwCounter = throwCooldown;
        }

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

            // CHECK OBJECT COLLISION
            int objIndex = gamePanel.collisionChecker.checkObject(this, true);
            pickUpObject(objIndex);

            // CHECK ENEMIES COLLISION
            int enemyIndex = gamePanel.collisionChecker.checkEntity(this, (ArrayList<Entity>) gamePanel.enemiesList);
            contactWithEnemy(enemyIndex);

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

            if (spriteNum > 4) {
                spriteNum = 1;
            }

            spriteCounter++;
            if (spriteCounter > 10) {
                spriteCounter = 0;
                spriteNum++;

                if (spriteNum > 4) {
                    spriteNum = 1;
                }
            }
        }

        if (invincible) {
            invincibleCounter++;

            if (invincibleCounter > 30) {
                invincibleCounter = 0;
                invincible = false;
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

        if (invincible) {
            graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
        }

        graphics2D.drawImage(image, screenX, screenY, null);

        // RESET ALPHA
        graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }

    private void drawCheckBox(Graphics2D graphics2D) {
        graphics2D.setColor(Color.RED);
        graphics2D.drawRect(screenX + solidArea.x, screenY + solidArea.y, solidArea.width, solidArea.height);
    }
}
