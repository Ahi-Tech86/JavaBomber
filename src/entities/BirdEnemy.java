package entities;

import explosions.ExplosionEffect;
import main.GamePanel;
import tile_interactive.InteractiveTile;
import utils.SpriteManager;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

public class BirdEnemy extends Entity {

    Random random;
    GamePanel gamePanel;

    private int actionLockCounter;

    public BirdEnemy(GamePanel gamePanel, int worldX, int worldY) {
        super(gamePanel, worldX, worldY);
        this.gamePanel = gamePanel;
        this.random = new Random();

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
        this.isPlayer = false;
        this.isMoving = false;
        this.direction = Direction.DOWN;
        this.lastDirection = Direction.DOWN;
    }

    private void loadBirdSprites() {
        boolean isWhiteBird = random.nextBoolean();
        String birdType = isWhiteBird ? "bird_white" : "bird_blue";
        loadSprites(birdType);
    }

    private void loadSprites(String birdType) {
        idleUp = SpriteManager.loadAndScaleSpriteFrames("/enemies/" + birdType + "/idle/idle_up", 4, gamePanel.tileSize, gamePanel.tileSize);
        idleDown = SpriteManager.loadAndScaleSpriteFrames("/enemies/" + birdType + "/idle/idle_down", 4, gamePanel.tileSize, gamePanel.tileSize);
        idleLeft = SpriteManager.loadAndScaleSpriteFrames("/enemies/" + birdType + "/idle/idle_left", 4, gamePanel.tileSize, gamePanel.tileSize);
        idleRight = SpriteManager.loadAndScaleSpriteFrames("/enemies/" + birdType + "/idle/idle_right", 4, gamePanel.tileSize, gamePanel.tileSize);

        walkUp = SpriteManager.loadAndScaleSpriteFrames("/enemies/" + birdType + "/walk/walk_up", 8, gamePanel.tileSize, gamePanel.tileSize);
        walkDown = SpriteManager.loadAndScaleSpriteFrames("/enemies/" + birdType + "/walk/walk_down", 8, gamePanel.tileSize, gamePanel.tileSize);
        walkLeft = SpriteManager.loadAndScaleSpriteFrames("/enemies/" + birdType + "/walk/walk_left", 8, gamePanel.tileSize, gamePanel.tileSize);
        walkRight = SpriteManager.loadAndScaleSpriteFrames("/enemies/" + birdType + "/walk/walk_right", 8, gamePanel.tileSize, gamePanel.tileSize);    }

    private void setDirection(int i) {
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
    }

    private void setAction() {
        actionLockCounter++;

        if (actionLockCounter == 150) {
            int i = random.nextInt(100) + 1;
            setDirection(i);
            actionLockCounter = 0;
        }
    }

    private void handleDeath() {
        gamePanel.player.points += 400;
        gamePanel.playSE(3);
        gamePanel.removeObserver(this);
        gamePanel.enemiesList.remove(this);
        gamePanel.enemiesNumbers--;
    }

    private void checkCollisions() {
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

    private void updateSprites() {
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
    public void update() {
        if (!alive) {
            handleDeath();
        }

        setAction();
        checkCollisions();
        updatePosition();
        updateSprites();
    }

    @Override
    public void draw(Graphics2D graphics2D, GamePanel gamePanel) {
        BufferedImage image = getCurrentImage();
        int screenX = worldX - gamePanel.player.worldX + gamePanel.player.screenX;
        int screenY = worldY - gamePanel.player.worldY + gamePanel.player.screenY;

        if (dying) {
            dyingAnimation(graphics2D);
        }

        if (isOnScreen()) {
            graphics2D.drawImage(image, screenX, screenY, null);

            // RESET ALPHA
            graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        }
    }

    private void dyingAnimation(Graphics2D graphics2D) {
        dyingCounter++;

        if (dyingCounter <= 5) {
            graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0f));
        } else if (dyingCounter <= 10) {
            graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        } else if (dyingCounter <= 15) {
            graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0f));
        } else if (dyingCounter <= 20) {
            graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        } else if (dyingCounter <= 25) {
            graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0f));
        } else if (dyingCounter <= 30) {
            graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        } else if (dyingCounter <= 35) {
            graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0f));
        } else if (dyingCounter <= 40) {
            graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        } else {
            dying = false;
            alive = false;
        }
    }
}
