package main;

import entities.Player;
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
    public Player player = new Player(this, keyHandler);

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
                System.out.println("FPS: " + drawCount);
                drawCount = 0;
                timer = 0;
            }
        }
    }

    public void update() {
        player.update();
    }

    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D graphics2D = (Graphics2D) graphics;

        tileManager.draw(graphics2D);

        player.draw(graphics2D);

        graphics2D.dispose();
    }
}
