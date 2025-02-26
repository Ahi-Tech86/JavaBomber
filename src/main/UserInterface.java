package main;

import utils.Loader;

import java.awt.*;
import java.util.Objects;

public class UserInterface {

    GamePanel gamePanel;
    Graphics2D graphics2D;

    Font titleFont;
    Font menuFont;
    Font debugFont;
    Font maruMonica;

    public int commandNum = 0;
    public boolean debugMode = false;

    public UserInterface(GamePanel gamePanel) {
        this.gamePanel = gamePanel;

        maruMonica = Loader.loadFont("/fonts/MP16OSF.ttf");
        debugFont = maruMonica.deriveFont(Font.PLAIN, 20F);
        titleFont = maruMonica.deriveFont(Font.BOLD, 96F);
        menuFont = maruMonica.deriveFont(Font.BOLD, 48F);
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

        // NAME
        graphics2D.setFont(titleFont);
        String text = "JavaBomberman";
        int x = getXforCenteredText(text);
        int y = gamePanel.tileSize * 3;
        drawStringAndShadowForText(text, x, y, 5, Color.gray);

        // PLAYER
        x = gamePanel.screenWidth / 2 - (gamePanel.tileSize * 2) / 2;
        y += gamePanel.tileSize / 4;
        graphics2D.drawImage(gamePanel.player.idleDown[0], x, y, gamePanel.tileSize * 2, gamePanel.tileSize * 2, null);

        // MENU
        graphics2D.setFont(menuFont);
        String[] menuOptions = {"NEW GAME", "LOAD GAME", "QUIT"};
        y += gamePanel.tileSize * 3;

        for (int i = 0; i < menuOptions.length; i++) {
            String menuText = menuOptions[i];
            x = getXforCenteredText(menuText);
            graphics2D.drawString(menuText, x, y);

            if (commandNum == i) {
                graphics2D.drawString(">", x - gamePanel.tileSize, y - 2);
            }

            y += gamePanel.tileSize;
        }
    }

    private void drawGameInterface() {
        graphics2D.setFont(menuFont);

        // POINTS
        String text = "POINTS: " + gamePanel.player.points;
        int x = getXforCenteredText(text);
        int y = gamePanel.tileSize;
        drawStringAndShadowForText(text, x, y, 3, Color.black);

        // HOW MANY ENEMIES LEFT
        text = "LEFT: " + gamePanel.enemiesList.stream().filter(Objects::nonNull).count();
        x = gamePanel.tileSize * 13;
        drawStringAndShadowForText(text, x, y, 3, Color.black);

        // TIME ELAPSED
        long elapsedTime = (System.nanoTime() - gamePanel.levelStartTime) / 1_000_000_000;
        text = "TIME: " + elapsedTime;
        x = gamePanel.tileSize;
        drawStringAndShadowForText(text, x, y, 3, Color.black);

        // DEBUG MODE
        if (debugMode) {
            graphics2D.setFont(debugFont);
            y += gamePanel.tileSize;

            String[] debugInfo = {
                    "X: " + gamePanel.player.worldX + "; Y: " + gamePanel.player.worldY,
                    "Col: " + (gamePanel.player.worldX / gamePanel.tileSize) + "; Row: " + (gamePanel.player.worldY / gamePanel.tileSize),
                    "Max life: " + gamePanel.player.maxLife + "; Life: " + gamePanel.player.life,
                    "Speed: " + gamePanel.player.speed + "; Direction: " + gamePanel.player.direction.toString() + "; Last direction: " + gamePanel.player.lastDirection.toString(),
                    "Invincible: " + gamePanel.player.invincible + "; Invincible counter: " + gamePanel.player.invincibleCounter,
                    "Alive: " + gamePanel.player.alive + "; Dying: " + gamePanel.player.dying + "; Dying counter: " + gamePanel.player.dyingCounter
            };

            for (String info : debugInfo) {
                graphics2D.drawString(info, gamePanel.tileSize, y);
                y += gamePanel.tileSize / 2;
            }
        }
    }

    private void drawStringAndShadowForText(String text, int x, int y, int offset, Color shadowColor) {
        graphics2D.setColor(shadowColor);
        graphics2D.drawString(text, x + offset, y + offset);
        graphics2D.setColor(Color.WHITE);
        graphics2D.drawString(text, x, y);
    }

    private int getXforCenteredText(String text) {
        int length = (int) graphics2D.getFontMetrics().getStringBounds(text, graphics2D).getWidth();

        return gamePanel.screenWidth / 2 - length / 2;
    }
}
