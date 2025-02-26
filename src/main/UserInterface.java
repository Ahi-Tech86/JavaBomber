package main;

import utils.Loader;

import java.awt.*;
import java.util.Objects;

public class UserInterface {

    GamePanel gamePanel;
    Graphics2D graphics2D;
    Font maruMonica;
    public int commandNum = 0;

    public UserInterface(GamePanel gamePanel) {
        this.gamePanel = gamePanel;

        maruMonica = Loader.loadFont("/fonts/MP16OSF.ttf");
    }

    public void draw(Graphics2D graphics2D) {
        this.graphics2D = graphics2D;

        graphics2D.setFont(maruMonica);
        graphics2D.setColor(Color.WHITE);

        if (gamePanel.gameState == gamePanel.titleState) {
            drawTitleScreen();
        } else if (gamePanel.gameState == gamePanel.playState) {
            drawGameInterface();
        } else if (gamePanel.gameState == gamePanel.pauseState) {
            drawPauseScreen();
        }
    }

    private void drawPauseScreen() {
        graphics2D.setFont(graphics2D.getFont().deriveFont(Font.PLAIN, 80));
        String text = "PAUSED";

        int x = getXforCenteredText(text);
        int y = gamePanel.screenHeight / 2;

        graphics2D.drawString(text, x, y);
    }

    private void drawTitleScreen() {
        // BACKGROUND
        graphics2D.setColor(new Color(0, 0, 0));
        graphics2D.fillRect(0, 0, gamePanel.screenWidth, gamePanel.screenHeight);

        graphics2D.setFont(graphics2D.getFont().deriveFont(Font.BOLD, 96F));
        String text = "JavaBomberman";
        int x = getXforCenteredText(text);
        int y = gamePanel.tileSize * 3;

        // NAME
        drawStringAndShadowForText(text, x, y, 5);

        // PLAYER
        x = gamePanel.screenWidth / 2 - (gamePanel.tileSize * 2) / 2;
        y += gamePanel.tileSize / 4;
        graphics2D.drawImage(gamePanel.player.idleDown[0], x, y, gamePanel.tileSize * 2, gamePanel.tileSize * 2, null);

        // MENU
        graphics2D.setFont(graphics2D.getFont().deriveFont(Font.BOLD, 48F));

        text = "NEW GAME";
        x = getXforCenteredText(text);
        y += gamePanel.tileSize * 3;
        graphics2D.drawString(text, x, y);
        if (commandNum == 0) {
            graphics2D.drawString(">", x - gamePanel.tileSize, y - 2);
        }

        text = "LOAD GAME";
        x = getXforCenteredText(text);
        y += gamePanel.tileSize;
        graphics2D.drawString(text, x, y);
        if (commandNum == 1) {
            graphics2D.drawString(">", x - gamePanel.tileSize, y - 2);
        }

        text = "QUIT";
        x = getXforCenteredText(text);
        y += gamePanel.tileSize;
        graphics2D.drawString(text, x, y);
        if (commandNum == 2) {
            graphics2D.drawString(">", x - gamePanel.tileSize, y - 2);
        }
    }

    private void drawGameInterface() {
        graphics2D.setFont(graphics2D.getFont().deriveFont(Font.BOLD, 48F));

        // POINTS
        String text = "POINTS: " + gamePanel.player.points;
        int x = getXforCenteredText(text);
        int y = gamePanel.tileSize;
        drawStringAndShadowForText(text, x, y, 2);

        // HOW MANY ENEMIES LEFT
        text = "LEFT: " + gamePanel.enemiesList.stream().filter(Objects::nonNull).count();
        x = gamePanel.tileSize * 13;
        drawStringAndShadowForText(text, x, y, 2);

        // TIME ELAPSED
        long elapsedTime = (System.nanoTime() - gamePanel.levelStartTime) / 1_000_000_000;
        text = "TIME: " + elapsedTime;
        x = gamePanel.tileSize;
        drawStringAndShadowForText(text, x, y, 2);
    }

    private void drawStringAndShadowForText(String text, int x, int y, int offset) {
        graphics2D.setColor(Color.gray);
        graphics2D.drawString(text, x + offset, y + offset);
        graphics2D.setColor(Color.WHITE);
        graphics2D.drawString(text, x, y);
    }

    private int getXforCenteredText(String text) {
        int length = (int) graphics2D.getFontMetrics().getStringBounds(text, graphics2D).getWidth();

        return gamePanel.screenWidth / 2 - length / 2;
    }
}
