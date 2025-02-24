package main;

import java.awt.*;

public class GameObject implements Renderable {
    public int worldX;
    public int worldY;

    public GameObject(int worldX, int worldY) {
        this.worldX = worldX;
        this.worldY = worldY;
    }

    @Override
    public void draw(Graphics2D graphics2D, GamePanel gamePanel) {
    }
}
