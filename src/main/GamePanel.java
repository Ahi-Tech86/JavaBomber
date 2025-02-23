package main;

import entities.Player;
import explosions.ExplosionEffect;
import explosions.ExplosiveEntity;
import objects.SuperObject;
import observer.Subject;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
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
    public final int maxWorldCol = 32;
    public final int maxWorldRow = 14;

    // FPS
    final int FPS = 60;

    // SYSTEM
    Thread gameThread;
    Sound se = new Sound();
    Sound music = new Sound();
    UserInterface userInterface = new UserInterface(this);
    KeyHandler keyHandler = new KeyHandler(this);
    TileManager tileManager = new TileManager(this);
    public CollisionChecker collisionChecker = new CollisionChecker(this);
    public AssetSetter assetSetter = new AssetSetter(this);
    public Player player = new Player(this, keyHandler);

    // OBJECTS AND ENTITIES
    public SuperObject[] objectsList = new SuperObject[10];
    public List<ExplosionEffect> explosionEffectList = new ArrayList<>();
    public List<ExplosiveEntity> explosiveEntityList = new ArrayList<>();

    // STATES
    public int gameState;
    public final int playState = 1;
    public final int pauseState = 2;

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
                //System.out.println("FPS: " + drawCount);
                drawCount = 0;
                timer = 0;
            }
        }
    }

    public void setupGame() {
        gameState = playState;
        assetSetter.setObjects();
        addObserver(player);
    }

    public void update() {
        if (gameState == playState) {
            notifyObservers();
        }
    }

    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D graphics2D = (Graphics2D) graphics;

        // DEBUG
        long drawStart = 0;
        drawStart = System.nanoTime();

        // TILE
        tileManager.draw(graphics2D);

        // OBJECT
        for (int i = 0; i < objectsList.length; i++) {
            if (objectsList[i] != null) {
                objectsList[i].draw(graphics2D, this);
            }
        }

        // EXPLOSIVE
        for (int i = 0; i < explosiveEntityList.size(); i++) {
            if (explosiveEntityList.get(i) != null) {
                explosiveEntityList.get(i).draw(graphics2D, this);
            }
        }

        // EFFECTS
        for (int i = 0; i < explosionEffectList.size(); i++) {
            if (explosionEffectList.get(i) != null) {
                explosionEffectList.get(i).draw(graphics2D, this);
            }
        }

        // PLAYER
        player.draw(graphics2D);

        userInterface.draw(graphics2D);

        long drawEnd = System.nanoTime();
        long passed = drawEnd - drawStart;

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
