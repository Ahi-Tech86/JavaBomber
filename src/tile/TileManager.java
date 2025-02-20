package tile;

import main.GamePanel;
import utils.SpriteManager;

import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileManager {

    GamePanel gamePanel;
    public Tile[] tileset;
    public int[][] mapTileNum;

    public TileManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;

        tileset = new Tile[10];
        mapTileNum = new int[gamePanel.maxWorldCol][gamePanel.maxWorldRow];

        getTileImage();
        loadMap("/maps/map0.txt");
    }

    private void getTileImage() {
        tileset[0] = new Tile();
        tileset[0].image = SpriteManager.loadImage("/tileset/road00.png");

        tileset[1] = new Tile();
        tileset[1].image = SpriteManager.loadImage("/tileset/wall.png");
        tileset[1].collision = true;
    }

    private void loadMap(String filePath) {
        try {
            InputStream inputStream = getClass().getResourceAsStream(filePath);
            assert inputStream != null;
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            int col = 0;
            int row = 0;

            while (col < gamePanel.maxWorldCol && row < gamePanel.maxWorldRow) {
                String line = bufferedReader.readLine();

                while (col < gamePanel.maxWorldCol) {
                    String[] numbers = line.split(" ");
                    int num = Integer.parseInt(numbers[col]);

                    mapTileNum[col][row] = num;
                    col++;
                }

                if (col == gamePanel.maxWorldCol) {
                    col = 0;
                    row++;
                }
            }

            bufferedReader.close();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void draw(Graphics2D graphics2D) {
        int worldCol = 0;
        int worldRow = 0;

        while (worldCol < gamePanel.maxWorldCol && worldRow < gamePanel.maxWorldRow) {
            int tileNum = mapTileNum[worldCol][worldRow];

            int worldX = worldCol * gamePanel.tileSize;
            int worldY = worldRow * gamePanel.tileSize;
            int screenX = worldX - gamePanel.player.worldX + gamePanel.player.screenX;
            int screenY = worldY - gamePanel.player.worldY + gamePanel.player.screenY;

            if (
                    worldX + gamePanel.tileSize > gamePanel.player.worldX - gamePanel.player.screenX &&
                    worldX - gamePanel.tileSize < gamePanel.player.worldX + gamePanel.player.screenX &&
                    worldY + gamePanel.tileSize > gamePanel.player.worldY - gamePanel.player.screenY &&
                    worldY - gamePanel.tileSize < gamePanel.player.worldY + gamePanel.player.screenY
            ) {
                graphics2D.drawImage(tileset[tileNum].image, screenX, screenY, gamePanel.tileSize, gamePanel.tileSize, null);
            }

            worldCol++;
            if (worldCol == gamePanel.maxWorldCol) {
                worldCol = 0;
                worldRow++;
            }
        }
    }
}
