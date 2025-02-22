package main;

import entities.Direction;
import entities.Entity;

public class CollisionChecker {

    GamePanel gamePanel;

    public CollisionChecker(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void checkTile(Entity entity) {
        int entityLeftWorldX = entity.worldX + entity.solidArea.x;
        int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
        int entityTopWorldY = entity.worldY + entity.solidArea.y;
        int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;

        int entityLeftCol = entityLeftWorldX / gamePanel.tileSize;
        int entityRightCol = entityRightWorldX / gamePanel.tileSize;
        int entityTopRow = entityTopWorldY / gamePanel.tileSize;
        int entityBottomRow = entityBottomWorldY / gamePanel.tileSize;

        int tileNum1, tileNum2;

        switch (entity.direction) {
            case Direction.UP:
                entityTopRow = (entityTopWorldY - entity.speed) / gamePanel.tileSize;
                tileNum1 = gamePanel.tileManager.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gamePanel.tileManager.mapTileNum[entityRightCol][entityTopRow];

                if (gamePanel.tileManager.tileset[tileNum1].collision || gamePanel.tileManager.tileset[tileNum2].collision) {
                    entity.collisionOn = true;
                }
                break;
            case Direction.DOWN:
                entityBottomRow = (entityBottomWorldY + entity.speed) / gamePanel.tileSize;
                tileNum1 = gamePanel.tileManager.mapTileNum[entityLeftCol][entityBottomRow];
                tileNum2 = gamePanel.tileManager.mapTileNum[entityRightCol][entityBottomRow];

                if (gamePanel.tileManager.tileset[tileNum1].collision || gamePanel.tileManager.tileset[tileNum2].collision) {
                    entity.collisionOn = true;
                }
                break;
            case Direction.LEFT:
                entityLeftCol = (entityLeftWorldX - entity.speed) / gamePanel.tileSize;
                tileNum1 = gamePanel.tileManager.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gamePanel.tileManager.mapTileNum[entityLeftCol][entityBottomRow];

                if (gamePanel.tileManager.tileset[tileNum1].collision || gamePanel.tileManager.tileset[tileNum2].collision) {
                    entity.collisionOn = true;
                }
                break;
            case Direction.RIGHT:
                entityRightCol = (entityRightWorldX + entity.speed) / gamePanel.tileSize;
                tileNum1 = gamePanel.tileManager.mapTileNum[entityRightCol][entityTopRow];
                tileNum2 = gamePanel.tileManager.mapTileNum[entityRightCol][entityBottomRow];

                if (gamePanel.tileManager.tileset[tileNum1].collision || gamePanel.tileManager.tileset[tileNum2].collision) {
                    entity.collisionOn = true;
                }
                break;
        }
    }

    public boolean checkTileSolidity(int worldX, int worldY) {
        int explosionCol = worldX / gamePanel.tileSize;
        int explosionRow = worldY / gamePanel.tileSize;

        if (
                explosionCol >= gamePanel.maxWorldCol ||
                explosionRow >= gamePanel.maxWorldRow ||
                explosionRow < 0 || explosionCol < 0
        ) {
            return true;
        }

        int tileNum = gamePanel.tileManager.mapTileNum[explosionCol][explosionRow];

        if (gamePanel.tileManager.tileset[tileNum].collision) {
            return true;
        }

        return false;
    }
}
