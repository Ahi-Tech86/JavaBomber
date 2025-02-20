package tile;

import main.GamePanel;
import utils.SpriteManager;

import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

public class TileManager {

    GamePanel gamePanel;
    Tile[] tileset;
    int[][] mapTileNum;

    public TileManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;

        tileset = new Tile[10];
        mapTileNum = new int[gamePanel.maxScreenCol][gamePanel.maxScreenRow];

        getTileImage();
        loadMap("/maps/map01.txt");
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
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(inputStream)));

            int col = 0;
            int row = 0;

            while (col < gamePanel.maxScreenCol && row < gamePanel.maxScreenRow) {
                String line = bufferedReader.readLine();

                while (col < gamePanel.maxScreenCol) {
                    String[] numbers = line.split(" ");
                    int num = Integer.parseInt(numbers[col]);

                    mapTileNum[col][row] = num;
                    col++;
                }

                if (col == gamePanel.maxScreenCol) {
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
        int x = 0;
        int y = 0;
        int col = 0;
        int row = 0;

        while (col < gamePanel.maxScreenCol && row < gamePanel.maxScreenRow) {
            int tileNum = mapTileNum[col][row];

            graphics2D.drawImage(tileset[tileNum].image, x, y, gamePanel.tileSize, gamePanel.tileSize, null);
            col++;
            x += gamePanel.tileSize;

            if (col == gamePanel.maxScreenCol) {
                col = 0;
                x = 0;

                row++;
                y += gamePanel.tileSize;
            }
        }
    }
}
