package components;

import ai.PathFinder;
import entities.Entity;
import entities.Player;
import explosions.ExplosionEffect;
import explosions.ExplosiveEntity;
import objects.SuperObject;
import observer.Subject;
import tile.TileManager;
import tile_interactive.InteractiveTile;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class GamePanel extends JPanel implements Runnable, Subject {

    // SCREEN SETTINGS
    final int scale = 4;
    final int originalTileSize = 16;

    public final int maxScreenCol = 16;
    public final int maxScreenRow = 9;
    public final int tileSize = scale * originalTileSize;
    public final int screenWidth = maxScreenCol * tileSize;
    public final int screenHeight = maxScreenRow * tileSize;

    // WORLD SETTINGS
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 13;

    // FPS
    final int FPS = 60;

    public int enemiesNumbers;
    protected long levelStartTime;

    // SYSTEM
    Thread gameThread;
    Sound se = new Sound();
    Sound music = new Sound();
    public PathFinder pathFinder = new PathFinder(this);
    UserInterface userInterface = new UserInterface(this);
    KeyHandler keyHandler = new KeyHandler(this);
    public TileManager tileManager = new TileManager(this);
    public CollisionChecker collisionChecker = new CollisionChecker(this);
    public AssetSetter assetSetter = new AssetSetter(this);
    public Player player = new Player(this, keyHandler);

    // OBJECTS AND ENTITIES
    ArrayList<GameObject> gameObjectsList = new ArrayList<>();
    public List<Entity> enemiesList = new ArrayList<>();
    public List<SuperObject> objectsList = new ArrayList<>();
    public List<ExplosionEffect> explosionEffectList = new ArrayList<>();
    public List<ExplosiveEntity> explosiveEntityList = new ArrayList<>();
    public List<InteractiveTile> interactiveTileList = new ArrayList<>();

    // STATES
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int gameOverState = 3;

    private final Comparator<GameObject> gameObjectComparator = new Comparator<GameObject>() {
        @Override
        public int compare(GameObject object1, GameObject object2) {
            return Integer.compare(object1.worldY, object2.worldY);
        }
    };

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        int oneSecond = 1000000000;
        double drawInterval = (double) oneSecond / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        long timer = 0;
        int drawCount = 0;

        while (gameThread != null) {

            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if (delta >= 1) {
                // 1 UPDATE INFORMATION
                update();

                // 2 DRAW THE SCREEN
                repaint();

                delta--;
                drawCount++;
            }

            if (timer >= oneSecond) {
                drawCount = 0;
                timer = 0;
            }
        }
    }

    public void setupGame() {
        gameState = titleState;
        assetSetter.setObjects();
        assetSetter.setEnemies();
        assetSetter.setInteractiveTiles();
        addObserver(player);

        for (Entity enemy : enemiesList) {
            addObserver(enemy);
        }

        for (InteractiveTile iTile : interactiveTileList) {
            addObserver(iTile);
        }

        levelStartTime = System.nanoTime();
        enemiesNumbers = (int) enemiesList.stream().filter(Objects::nonNull).count();
    }

    public void retryGame() {
        player.setDefaultPositions();
        player.resetPlayerVariables();
        enemiesList.clear();
        assetSetter.setEnemies();
        for (Entity enemy : enemiesList) {
            addObserver(enemy);
        }
        enemiesNumbers = (int) enemiesList.stream().filter(Objects::nonNull).count();
    }

    public void restartGame() {
        this.levelStartTime = System.nanoTime();
        player.setDefaultPositions();
        player.resetPlayerVariables();
        enemiesList.clear();
        objectsList.clear();
        interactiveTileList.clear();
        assetSetter.setObjects();
        assetSetter.setEnemies();
        assetSetter.setInteractiveTiles();

        for (Entity enemy : enemiesList) {
            addObserver(enemy);
        }

        for (InteractiveTile iTile : interactiveTileList) {
            addObserver(iTile);
        }

        enemiesNumbers = (int) enemiesList.stream().filter(Objects::nonNull).count();
    }

    public void update() {
        if (gameState == playState) {
            notifyObservers();
        }
    }

    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D graphics2D = (Graphics2D) graphics;

        graphics2D.setColor(Color.gray);
        graphics2D.fillRect(0, 0, screenWidth, screenHeight);

        if (gameState == titleState) {
            userInterface.draw(graphics2D);
        } else {
            // TILE
            tileManager.draw(graphics2D);

            gameObjectsList.clear();
            gameObjectsList.addAll(enemiesList);
            gameObjectsList.addAll(objectsList);
            gameObjectsList.addAll(explosiveEntityList);
            gameObjectsList.addAll(explosionEffectList);
            gameObjectsList.addAll(interactiveTileList);

            Collections.sort(gameObjectsList, gameObjectComparator);

            for (GameObject obj : gameObjectsList) {
                obj.draw(graphics2D, this);
            }

            player.draw(graphics2D, this);
            userInterface.draw(graphics2D);
        }

        graphics2D.dispose();
    }

    public void setMusic(int i) {
        music.setFile(i);
        music.play();
        music.loop();
    }

    public void stopMusic() {
        music.stop();
    }

    public void playSE(int i) {
        se.setFile(i);
        se.play();
    }
}
