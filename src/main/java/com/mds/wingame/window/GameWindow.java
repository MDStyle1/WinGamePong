package com.mds.wingame.window;

import com.mds.game.controller.PlayerControllerInterface;
import com.mds.game.gamemode.Game;
import com.mds.game.gamemode.GameInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class GameWindow extends JPanel {

    private boolean blockControll=true;
    private boolean isKey=false;
    private int key=0;
    private PlayerControllerInterface playerController1;
    public MainWindow mainWindow;
    private GameInterface gameInterface;
    private GameDraw gameDraw;
    private TextScore textScore;
    public GameWindow(MainWindow mainWindow,GameInterface gameInterface){
        this.mainWindow=mainWindow;
        this.gameInterface=gameInterface;
        playerController1=gameInterface.getPlayer1();
        setFocusable(false);
        setLayout(null);
        add(new ButMenu());
        gameDraw = new GameDraw(100,10,gameInterface.getSizeX(),gameInterface.getSizeY());
        textScore=new TextScore();
        add(textScore);
        blockControll=false;
    }

    private void updateScore(){
        if(textScore!=null)textScore.setText("Score:"+gameInterface.getScore());
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
            } else gameInterface.playPause();
        }else {
            if(i==65||i==68){
                playerController1.move(0);
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

    private class TextScore extends JLabel{
        public TextScore() {
            setBounds(10,40,80,25);
            setText("Score:0");
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
    private class GameDraw{
        private int x;
        private int y;
        private int height;
        private int width;
        private Map map;
        public GameDraw(int x, int y, int width, int height){
            this.x = x;
            this.y = y;
            this.height =height;
            this.width = width;
            setBounds(200,10,80,25);
            map = new Map(gameInterface.getMap());
            add(map);
            gameInterface.setEventMapGraphics(new Game.EventMapGraphics() {
                @Override
                public void updateMap(BufferedImage bufferedImage) {
                    GameDraw.this.updateMap(bufferedImage);
                }
            });
            add(new TextHelp());
            setFocusable(false);
        }
        private void updateMap(BufferedImage image){
            map.updateIcon(image);
            updateScore();
        }
        private void paintGameWindow(Graphics g){
            g.drawLine(x+0,y+0,x+ width,y+0);
            g.drawLine(x+ width,y+0,x+ width,y+height);
            g.drawLine(x+ width,y+height,x+0,y+height);
            g.drawLine(x+0,y+height,x+0,y+0);
        }
        private class TextHelp extends JTextPane{
            public TextHelp(){
                setEditable(false);
                setBounds(5,70,90,50);
                setText("Player1 A D\nPause spacebar");
            }
        }
        private class Map extends JLabel{
            public Map(BufferedImage image){
                ImageIcon icon = new ImageIcon(image);
                setIcon(icon);
                setBounds(100,10,image.getWidth(),image.getHeight());
            }
            public void updateIcon(BufferedImage image){
                ImageIcon icon = new ImageIcon(image);
                setIcon(icon);
            }
        }
    }
}
