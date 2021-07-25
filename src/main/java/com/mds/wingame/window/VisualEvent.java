package com.mds.wingame.window;

import com.mds.game.GameInterface;
import com.mds.game.VisualEventInterface;
import com.mds.wingame.Starter;

public class VisualEvent implements VisualEventInterface {
    public GameWindow gameWindow;
    public Starter starter=new Starter();
    private GameInterface myGame;
    @Override
    public void endGame(int i) {
        gameWindow.endGame(i);
    }

    @Override
    public void createGame() {
        myGame.startCreateGame(starter);
    }

    @Override
    public void gameStart() {
        gameWindow.startWindow(starter);
    }

    @Override
    public void setGameInterface(GameInterface gameInterface) {
        myGame=gameInterface;
        gameWindow=new GameWindow(myGame);
    }
}
