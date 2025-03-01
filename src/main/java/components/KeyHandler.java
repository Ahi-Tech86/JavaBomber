package components;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    GamePanel gamePanel;

    public boolean upPressed, downPressed, leftPressed, rightPressed, ePressed;

    public KeyHandler(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // useless
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        if (gamePanel.gameState == gamePanel.titleState) {
            switch (code) {
                case KeyEvent.VK_W -> {
                    gamePanel.userInterface.commandNum--;

                    if (gamePanel.userInterface.commandNum < 0) {
                        gamePanel.userInterface.commandNum = 2;
                    }
                }
                case KeyEvent.VK_S -> {
                    gamePanel.userInterface.commandNum++;

                    if (gamePanel.userInterface.commandNum > 2) {
                        gamePanel.userInterface.commandNum = 0;
                    }
                }
                case KeyEvent.VK_ENTER -> {
                    if (gamePanel.userInterface.commandNum == 0) {
                        gamePanel.gameState = gamePanel.playState;
                    } else if (gamePanel.userInterface.commandNum == 1) {
                        // nothing now
                    } else if (gamePanel.userInterface.commandNum == 2) {
                        System.exit(0);
                    }
                }
            }
        } else if (gamePanel.gameState == gamePanel.gameOverState) {
            switch (code) {
                case KeyEvent.VK_W -> {
                    gamePanel.userInterface.commandNum--;
                    if (gamePanel.userInterface.commandNum < 0) {
                        gamePanel.userInterface.commandNum = 1;
                    }
                }
                case KeyEvent.VK_S -> {
                    gamePanel.userInterface.commandNum++;
                    if (gamePanel.userInterface.commandNum > 1) {
                        gamePanel.userInterface.commandNum = 0;
                    }
                }
                case KeyEvent.VK_ENTER -> {
                    if (gamePanel.userInterface.commandNum == 0) {
                        gamePanel.gameState = gamePanel.playState;
                        gamePanel.retryGame();
                    } else if (gamePanel.userInterface.commandNum == 1) {
                        gamePanel.gameState = gamePanel.titleState;
                        gamePanel.restartGame();
                    }
                }
            }
        } else {
            switch (code) {
                case KeyEvent.VK_E -> ePressed = true;
                case KeyEvent.VK_W -> upPressed = true;
                case KeyEvent.VK_S -> downPressed = true;
                case KeyEvent.VK_A -> leftPressed = true;
                case KeyEvent.VK_D -> rightPressed = true;
                case KeyEvent.VK_P -> {
                    if (gamePanel.gameState == gamePanel.playState) {
                        gamePanel.gameState = gamePanel.pauseState;
                    } else if (gamePanel.gameState == gamePanel.pauseState) {
                        gamePanel.gameState = gamePanel.playState;
                    }
                }
                case KeyEvent.VK_Z -> gamePanel.userInterface.debugMode = !gamePanel.userInterface.debugMode;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        switch (code) {
            case KeyEvent.VK_E -> ePressed = false;
            case KeyEvent.VK_W -> upPressed = false;
            case KeyEvent.VK_S -> downPressed = false;
            case KeyEvent.VK_A -> leftPressed = false;
            case KeyEvent.VK_D -> rightPressed = false;
        }
    }
}
