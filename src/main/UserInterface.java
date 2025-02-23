package main;

import utils.Loader;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

public class UserInterface {

    GamePanel gamePanel;
    Graphics2D graphics2D;
    Font maruMonica;

    public UserInterface(GamePanel gamePanel) {
        this.gamePanel = gamePanel;

        maruMonica = Loader.loadFont("/fonts/MP16OSF.ttf");
    }

    public void draw(Graphics2D graphics2D) {
        this.graphics2D = graphics2D;

        graphics2D.setFont(maruMonica);
        graphics2D.setColor(Color.WHITE);

        if (gamePanel.gameState == gamePanel.playState) {
            // nothing now
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

    private int getXforCenteredText(String text) {
        int length = (int) graphics2D.getFontMetrics().getStringBounds(text, graphics2D).getWidth();

        return gamePanel.screenWidth / 2 - length / 2;
    }
}
