package com.mds.wingame.window;

import com.mds.game.Main;
import com.mds.game.MainInterface;

import java.awt.image.BufferedImage;

public class VisualEvent implements Main.EventMain {
    public MainInterface main;
    public MainWindow mainWindow;

    public VisualEvent() {
        mainWindow=new MainWindow();
        mainWindow.startWindow();
        main = Main.createMain();
        main.setEventMain(this);
        mainWindow.startMenu(main);
    }

    @Override
    public void gameIsCreate() {
        mainWindow.gameIsStart();
    }

    @Override
    public void endGame() {

    }
}
