## Introduction

This file will describe the purpose of classes, class hierarchy, diagrams and explanation of the Observer pattern.

## Main project classes

### Class 1: GamePanel
The GamePanel class is the central component of the game, managing the game loop, input processing, rendering, and 
interaction with system components. It expends *JPanel* and implements the *Runnable* and *Subject* interfaces, allowing
it to serve as the main game loop and manage observers.


Game loop schema:

![GameLoopSchema](/docs/Gameloop_schema.png)

The GamePanel class contains variables:
1. Screen settings:
   - *scale*, *originalTileSize*, *maxScreenCol*, *maxScreenRow*, *tileSize*, *screenWidth*, *screenHeight*: parameters that determine the size and scale of the screen. 
2. World settings:
   - *maxWorldCol*, *maxWorldRow*: parameters that determine the size of the game world
3. System components:
   - *gameThread*: game thread.
   - *se*, *music*: sounds control objects.
   - *pathFinder*, *userInterface*, *keyHandler*, *tileManager*, *collisionChecker*, *assetSetter*, *player*: objects to control various aspects of game.
4. Lists of objects and entities:
   - *gameObjectsList*, *enemiesList*, *objectsList*, *explosionEffectList*, *explosiveEntityList*, *interactiveTileList*: lists for storing various game objects and entities.
5. Game states:
   - *titleState*, *playState*, *pauseState*, *gameOverState*: variables to control game states.
6. Comparator:
   - *gameObjectComparator*: comparator for sorting game objects by their *worldY* coordinates.

The GamePanel class contains methods:
1. Game loop management:
   - *startGameThread()*: starting game thread.
   - *run()*: the main method of the game loop, updated the game state and redraws the screen.
2. Game management:
   - *setupGame()*: sets the initial state of the game.
   - *retryGame()*: restarts the game while maintaining the current state.
   - *restartGame()*: restarts the game from the beginning.
   - *update()*: updates the game state based on the current state.
3. Rendering:
   - *paintComponent(Graphics graphics)*: redraws game components on the screen.
4. Music and sounds management:
   - *setMusic(int i)*: installs and plays music.
   - *stopMusic()*: stops the music.
   - *playSE(int i)*: plays sound effects.

Interaction with other system components:
1. *Player*: controls the player through the player object, setting its starting positions and resetting variables.
2. *Enemies* and *InteractiveTiles*: adds enemies and interactive tiles to the observers list so they can be notified of changes.
3. *TileManager*, *CollisionChecker*, *AssetSetter*: uses these components to manage tiles, check collisions, and set objects and entities.
4. *UserInterface*: uses for drawing user interface on screen.
5. *PathFinder*: uses to find path for entities.
6. *Sound*: management sound effects and music.

## Inheritance schema

![Inheritance schema](/docs/Classes_Inheritance.drawio.png)

## Observer pattern

Observer pattern schema:

![Observer schema](/docs/Observer_schema.drawio.png)