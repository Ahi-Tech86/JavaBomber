package main;

import entities.Player;
import explosions.ExplosionEffect;
import explosions.ExplosiveEntity;
import objects.SuperObject;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {

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
    public final int worldWidth = maxWorldCol * tileSize;
    public final int worldHeight = maxWorldRow * tileSize;

    // FPS
    final int FPS = 60;

    // SYSTEM
    Thread gameThread;
    KeyHandler keyHandler = new KeyHandler();
    TileManager tileManager = new TileManager(this);
    public CollisionChecker collisionChecker = new CollisionChecker(this);
    public AssetSetter assetSetter = new AssetSetter(this);
    public Player player = new Player(this, keyHandler);

    public SuperObject[] objectsList = new SuperObject[10];
    public ExplosiveEntity[] explosiveList = new ExplosiveEntity[10];
    public ExplosionEffect[] explosionEffectsList = new ExplosionEffect[100];

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
        assetSetter.setObjects();
    }

    public void update() {
        player.update();

        for (int i = 0; i < explosiveList.length; i++) {
            if (explosiveList[i] != null) {
                if (!explosiveList[i].isExploded) {
                    explosiveList[i].update();
                } else {
                    explosiveList[i] = null;
                }
            }
        }

        for (int i = 0; i < explosionEffectsList.length; i++) {
            if (explosionEffectsList[i] != null) {
                if (explosionEffectsList[i].isActive) {
                    explosionEffectsList[i].update();
                } else {
                    explosionEffectsList[i] = null;
                }
            }
        }
    }

    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D graphics2D = (Graphics2D) graphics;

        // TILE
        tileManager.draw(graphics2D);

        // OBJECT
        for (int i = 0; i < objectsList.length; i++) {
            if (objectsList[i] != null) {
                objectsList[i].draw(graphics2D, this);
            }
        }

        // EXPLOSIVE
        for (int i = 0; i < explosiveList.length; i++) {
            if (explosiveList[i] != null) {
                explosiveList[i].draw(graphics2D, this);
            }
        }

        // EXPLOSION EFFECTS
        for (int i = 0; i < explosionEffectsList.length; i++) {
            if (explosionEffectsList[i] != null) {
                explosionEffectsList[i].draw(graphics2D, this);
            }
        }

        // PLAYER
        player.draw(graphics2D);

        graphics2D.dispose();
    }
}
