package com.mds.wingame.window;

import com.mds.game.controller.PlayerControllerInterface;
import com.mds.game.gamemode.GameInterface;
import com.mds.game.map.objects.ObjectMapInterface;
import com.mds.game.map.objects.TypeObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class GameWindow extends JPanel {

    private boolean blockControll=true;
    private boolean isKey=false;
    private int key=0;
    private PlayerControllerInterface playerController1;
    public MainWindow mainWindow;
    private GameInterface gameInterface;
    private GameDraw gameDraw;
    public GameWindow(MainWindow mainWindow,GameInterface gameInterface){
        this.mainWindow=mainWindow;
        this.gameInterface=gameInterface;
        playerController1=gameInterface.getPlayer1();
        setFocusable(false);
        setLayout(null);
        add(new ButMenu());
        gameDraw = new GameDraw(100,10,gameInterface.getSizeX(),gameInterface.getSizeY());
        add(gameDraw);
        blockControll=false;
    }

    public void keyTap(int i,boolean is){
        if(blockControll){
            return;
        }
        isKey=is;
        key=i;
        if(isKey){
            if(i==65){
                playerController1.move(-1);
            } else if(i==68) {
                playerController1.move(1);
//            } else if(i==37){
//                playerController2.move(-1);
//            } else if(i==39) {
//                playerController2.move(1);
            } else gameInterface.playPause();
        }else {
            if(i==65||i==68){
                playerController1.move(0);
//            } else if(i==37||i==39){
//                playerController2.move(0);
            }
        }
    }

    private void openClose(){
        blockControll=true;
        if(gameInterface.isPause()){
            gameInterface.playPause();
        }
        gameInterface.stopGame();
        setVisible(!isVisible());
    }
    public void onRepaint(Graphics g) {
        gameDraw.onRepaint(g);
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        onRepaint(g);
        repaint();
    }
    private class GameDraw extends JPanel{
        private int x;
        private int y;
        private int height;
        private int width;
        public GameDraw(int x, int y, int width, int height){
            this.x = x;
            this.y = y;
            this.height =height;
            this.width = width;
            setFocusable(false);
        }
        public void onRepaint(Graphics g) {
            paintGameWindow(g);
            paintGameMap(g);
            paintHelpControl(g);
        }
        private void paintHelpControl(Graphics g){
            g.drawString("Player1 A D", 5,100);
            g.drawString("Player2 <- ->",5,120);
            g.drawString("Pause spacebar",5,140);
        }
        private void paintGameWindow(Graphics g){
            g.drawLine(x+0,y+0,x+ width,y+0);
            g.drawLine(x+ width,y+0,x+ width,y+height);
            g.drawLine(x+ width,y+height,x+0,y+height);
            g.drawLine(x+0,y+height,x+0,y+0);
        }
        private void paintGameMap(Graphics g){
            List<ObjectMapInterface> objects= gameInterface.getMap();
            for(ObjectMapInterface o:objects){
                TypeObject type = o.getTypeObject();
                switch (type){
                    case ball:
                        g.drawOval(o.getX()-o.getSizeX()+x,o.getY()-o.getSizeY()+y,
                                o.getR()*2,o.getR()*2);
                        break;
                    case board:
                        g.drawLine(o.getX()-o.getSizeX()+x,
                                o.getY()+o.getSizeY()+y,
                                o.getX()+o.getSizeX()+x,
                                o.getY()+o.getSizeY()+y);
                        g.drawLine(o.getX()+o.getSizeX()+x,
                                o.getY()+o.getSizeY()+y,
                                o.getX()+o.getSizeX()+x,
                                o.getY()-o.getSizeY()+y);
                        g.drawLine(o.getX()+o.getSizeX()+x,
                                o.getY()-o.getSizeY()+y,
                                o.getX()-o.getSizeX()+x,
                                o.getY()-o.getSizeY()+y);
                        g.drawLine(o.getX()-o.getSizeX()+x,
                                o.getY()-o.getSizeY()+y,
                                o.getX()-o.getSizeX()+x,
                                o.getY()+o.getSizeY()+y);
                        break;
                    case object:
                        break;
                }
            }
        }
    }

    private class ButMenu extends JButton{
        public ButMenu() {
            setFocusable(false);
            setBounds(10,10,80,25);
            setText("Menu");
            addActionListener(new MyActionListener());
        }
        private class MyActionListener implements ActionListener {
            public void actionPerformed(ActionEvent e) {
                openClose();
                mainWindow.openCloseMainMenu();
            }
        }
    }
}
