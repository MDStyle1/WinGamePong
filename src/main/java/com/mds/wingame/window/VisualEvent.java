package com.mds.wingame.window;

import com.mds.game.Game;
import com.mds.game.GameInterface;
import com.mds.game.VisualEventInterface;

public class VisualEvent implements VisualEventInterface {
    public GameWindow gameWindow;
    private GameInterface myGame;

    public VisualEvent() {
        myGame = Game.createGame(this);
        gameWindow = new GameWindow(myGame);
        myGame.createMapAndStart();
    }

    @Override
    public void eventEndGame(int i) {
        gameWindow.endGame(i);
    }

    @Override
    public void eventCreateGame() {
    }

    @Override
    public void eventGameStart() {
        gameWindow.startWindow();
    }
}
