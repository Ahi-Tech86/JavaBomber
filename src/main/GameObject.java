package main;

import java.awt.*;

public class GameObject implements Renderable {
    public int worldX;
    public int worldY;
    public Rectangle solidArea;
    public int solidAreaDefaultX;
    public int solidAreaDefaultY;

    public GameObject(int worldX, int worldY) {
        this.worldX = worldX;
        this.worldY = worldY;
        this.solidArea = new Rectangle(0, 0, 64, 64);
        this.solidAreaDefaultX = 0;
        this.solidAreaDefaultY = 0;
    }

    @Override
    public void draw(Graphics2D graphics2D, GamePanel gamePanel) {
    }
}
