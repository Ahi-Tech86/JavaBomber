package main;

import entities.Direction;
import entities.Entity;
import objects.SuperObject;

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

    public int checkObject(Entity entity, boolean player) {
        int index = 999;

        for (int i = 0; i < gamePanel.objectsList.size(); i++) {
            if (gamePanel.objectsList.get(i) != null) {
                // Get entity's solid area position
                entity.solidArea.x = entity.worldX + entity.solidArea.x;
                entity.solidArea.y = entity.worldY + entity.solidArea.y;

                // Get object's solid area position
                gamePanel.objectsList.get(i).solidArea.x = gamePanel.objectsList.get(i).worldX + gamePanel.objectsList.get(i).solidArea.x;
                gamePanel.objectsList.get(i).solidArea.y = gamePanel.objectsList.get(i).worldY + gamePanel.objectsList.get(i).solidArea.y;

                switch (entity.direction) {
                    case UP -> entity.solidArea.y -= entity.speed;
                    case DOWN -> entity.solidArea.y += entity.speed;
                    case LEFT -> entity.solidArea.x -= entity.speed;
                    case RIGHT -> entity.solidArea.x += entity.speed;
                }

                if (entity.solidArea.intersects(gamePanel.objectsList.get(i).solidArea)) {
                    if (gamePanel.objectsList.get(i).collision) {
                        entity.collisionOn = true;
                    }

                    if (player) {
                        index = i;
                    }
                }

                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                gamePanel.objectsList.get(i).solidArea.x = gamePanel.objectsList.get(i).solidAreaDefaultX;
                gamePanel.objectsList.get(i).solidArea.y = gamePanel.objectsList.get(i).solidAreaDefaultY;
            }
        }

        return index;
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
