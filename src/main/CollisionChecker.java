package main;

import entities.Direction;
import entities.Entity;
import explosions.ExplosionEffect;
import objects.SuperObject;
import tile_interactive.InteractiveTile;

import java.util.ArrayList;

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
                SuperObject obj = gamePanel.objectsList.get(i);

                // Get entity's solid area position
                entity.solidArea.x = entity.worldX + entity.solidArea.x;
                entity.solidArea.y = entity.worldY + entity.solidArea.y;

                // Get object's solid area position
                obj.solidArea.x = obj.worldX + obj.solidArea.x;
                obj.solidArea.y = obj.worldY + obj.solidArea.y;

                switch (entity.direction) {
                    case UP -> entity.solidArea.y -= entity.speed;
                    case DOWN -> entity.solidArea.y += entity.speed;
                    case LEFT -> entity.solidArea.x -= entity.speed;
                    case RIGHT -> entity.solidArea.x += entity.speed;
                }

                if (entity.solidArea.intersects(obj.solidArea)) {
                    if (gamePanel.objectsList.get(i).collision) {
                        entity.collisionOn = true;
                    }

                    if (player) {
                        index = i;
                    }
                }

                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                obj.solidArea.x = obj.solidAreaDefaultX;
                obj.solidArea.y = obj.solidAreaDefaultY;
            }
        }

        return index;
    }

    public boolean checkPlayer(Entity entity) {

        boolean contactWithPlayer = false;

        // Get entity solid area
        entity.solidArea.x = entity.worldX + entity.solidArea.x;
        entity.solidArea.y = entity.worldY + entity.solidArea.y;

        // Get object solid area
        gamePanel.player.solidArea.x = gamePanel.player.worldX + gamePanel.player.solidArea.x;
        gamePanel.player.solidArea.y = gamePanel.player.worldY + gamePanel.player.solidArea.y;

        switch (entity.direction) {
            case UP -> entity.solidArea.y -= entity.speed;
            case DOWN -> entity.solidArea.y += entity.speed;
            case LEFT -> entity.solidArea.x -= entity.speed;
            case RIGHT -> entity.solidArea.x += entity.speed;
        }

        if (entity.solidArea.intersects(gamePanel.player.solidArea)) {
            entity.collisionOn = true;
            contactWithPlayer = true;
        }

        entity.solidArea.x = entity.solidAreaDefaultX;
        entity.solidArea.y = entity.solidAreaDefaultY;
        gamePanel.player.solidArea.x = gamePanel.player.solidAreaDefaultX;
        gamePanel.player.solidArea.y = gamePanel.player.solidAreaDefaultY;

        return contactWithPlayer;
    }

    public int checkEntity(Entity entity, ArrayList<Entity> target) {
        int index = 999;

        for (int i = 0; i < target.size(); i++) {
            if (target.get(i) != null) {
                Entity entityFromTarget = target.get(i);

                // Get entity solid area
                entity.solidArea.x = entity.worldX + entity.solidArea.x;
                entity.solidArea.y = entity.worldY + entity.solidArea.y;

                // Get object solid area
                entityFromTarget.solidArea.x = entityFromTarget.worldX + entityFromTarget.solidArea.x;
                entityFromTarget.solidArea.y = entityFromTarget.worldY + entityFromTarget.solidArea.y;

                switch (entity.direction) {
                    case UP -> entity.solidArea.y -= entity.speed;
                    case DOWN -> entity.solidArea.y += entity.speed;
                    case LEFT -> entity.solidArea.x -= entity.speed;
                    case RIGHT -> entity.solidArea.x += entity.speed;
                }

                if (entity.solidArea.intersects(entityFromTarget.solidArea)) {
                    if (entityFromTarget != entity) {
                        entity.collisionOn = true;
                        index = i;
                    }
                }

                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                entityFromTarget.solidArea.x = entityFromTarget.solidAreaDefaultX;
                entityFromTarget.solidArea.y = entityFromTarget.solidAreaDefaultY;
            }
        }

        return index;
    }

    public int checkInteractiveTiles(Entity entity, ArrayList<InteractiveTile> target) {
        int index = 999;

        for (int i = 0; i < target.size(); i++) {
            if (target.get(i) != null) {
                InteractiveTile iTile = target.get(i);

                // Get entity solid area
                entity.solidArea.x = entity.worldX + entity.solidArea.x;
                entity.solidArea.y = entity.worldY + entity.solidArea.y;

                // Get object solid area
                iTile.solidArea.x = iTile.worldX + iTile.solidArea.x;
                iTile.solidArea.y = iTile.worldY + iTile.solidArea.y;

                switch (entity.direction) {
                    case UP -> entity.solidArea.y -= entity.speed;
                    case DOWN -> entity.solidArea.y += entity.speed;
                    case LEFT -> entity.solidArea.x -= entity.speed;
                    case RIGHT -> entity.solidArea.x += entity.speed;
                }

                if (entity.solidArea.intersects(iTile.solidArea)) {
                    entity.collisionOn = true;
                    index = i;
                }

                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                iTile.solidArea.x = iTile.solidAreaDefaultX;
                iTile.solidArea.y = iTile.solidAreaDefaultY;
            }
        }

        return index;
    }

    public boolean checkEntityInExplosionArea(Entity entity, ArrayList<ExplosionEffect> explosions) {
        int index = 999;

        for (int i = 0; i < explosions.size(); i++) {
            if (explosions.get(i) != null) {
                ExplosionEffect explosion = explosions.get(i);

                // Get entity's solid area position
                entity.solidArea.x = entity.worldX + entity.solidArea.x;
                entity.solidArea.y = entity.worldY + entity.solidArea.y;

                // Get object's solid area position
                explosion.solidArea.x = explosion.worldX + explosion.solidArea.x;
                explosion.solidArea.y = explosion.worldY + explosion.solidArea.y;

                switch (entity.direction) {
                    case UP -> entity.solidArea.y -= entity.speed;
                    case DOWN -> entity.solidArea.y += entity.speed;
                    case LEFT -> entity.solidArea.x -= entity.speed;
                    case RIGHT -> entity.solidArea.x += entity.speed;
                }

                if (entity.solidArea.intersects(explosion.solidArea)) {
                    if (explosion.isLethal) {
                        index = i;
                    }
                }

                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                explosion.solidArea.x = explosion.solidAreaDefaultX;
                explosion.solidArea.y = explosion.solidAreaDefaultY;
            }
        }

        return index != 999;
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

//        if (!gamePanel.tileManager.tileset[tileNum].collision) {
//            for (int i = 0; i < gamePanel.interactiveTileList.size(); i++) {
//                if (gamePanel.interactiveTileList.get(i) != null) {
//                    InteractiveTile iTile = gamePanel.interactiveTileList.get(i);
//                    int iTileCol = iTile.worldX / gamePanel.tileSize;
//                    int iTileRow = iTile.worldY / gamePanel.tileSize;
//
//                    if (iTileCol == explosionCol && iTileRow == explosionRow) {
//                        return false;
//                    }
//                }
//            }
//        }

        return gamePanel.tileManager.tileset[tileNum].collision;
    }

    public boolean checkInteractiveTileExplosion(int worldX, int worldY) {
        int explosionCol = worldX / gamePanel.tileSize;
        int explosionRow = worldY / gamePanel.tileSize;

        for (int i = 0; i < gamePanel.interactiveTileList.size(); i++) {
            if (gamePanel.interactiveTileList.get(i) != null) {
                InteractiveTile iTile = gamePanel.interactiveTileList.get(i);
                int iTileCol = iTile.worldX / gamePanel.tileSize;
                int iTileRow = iTile.worldY / gamePanel.tileSize;

                if (iTileCol == explosionCol && iTileRow == explosionRow) {
                    gamePanel.interactiveTileList.remove(iTile);
                    gamePanel.removeObserver(iTile);

                    return true;
                }
            }
        }

        return false;
    }
}
