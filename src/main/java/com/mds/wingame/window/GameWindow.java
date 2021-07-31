package com.mds.wingame.window;

import com.mds.game.GameInterface;
import com.mds.game.controller.PlayerControllerInterface;
import com.mds.game.map.MapInterface;
import com.mds.game.map.objects.ObjectMapInterface;
import com.mds.game.map.objects.TypeObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

public class GameWindow extends JFrame{

    private GameInterface game;
    private int scaleGame;
    private MapInterface map;
    private boolean end;
    private int player;
    private boolean isKey=false;
    private int key=0;
    private PlayerControllerInterface playerController1;
    private PlayerControllerInterface playerController2;
    private GameField game_field;
    public GameWindow(GameInterface game) throws HeadlessException {
        this.game=game;
        scaleGame = 2;
    }

    public void startWindow() {
        map = game.getMap();
        playerController1 =game.getPlayer1();
        playerController2 = game.getPlayer2();
        settingWindowStart();
        startGame();
    }

    private void settingWindowStart(){
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocation(200,100);
        setSize(400,400);
        setResizable(false);
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int key= e.getKeyCode();
                if(key==65||key==68||key==32||key==37||key==39){
                    keyTap(key,true);
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                int key= e.getKeyCode();
                if(key==65||key==68||key==37||key==39){
                    keyTap(key,false);
                }
            }
        });
        setVisible(true);
    }
    private void onRepaint(Graphics g){
        List<ObjectMapInterface> objects= map.getObjectsMap();
        g.drawLine(100,0,100,map.getSizeY());
        g.drawLine(100,0,map.getSizeX()+100,0);
        g.drawLine(100,map.getSizeY(),map.getSizeX()+100,map.getSizeY());
        g.drawLine(map.getSizeX()+100,0,map.getSizeX()+100,map.getSizeY());
        g.drawString("Player1 A D", 220,100);
        g.drawString("Player2 arrowLEft arrowRight",220,120);
        g.drawString("Pause spacebar",220,140);
        if(end){
            String str=new String("");
            if(player==1){
                str="Player1 win!!";
            }str="Player2 win!!!";
            g.drawString(str,200,200);
        }for(ObjectMapInterface o:objects){
            TypeObject type = o.getTypeObject();
            switch (type){
                case ball:
                    g.drawOval(o.getX()-o.getSizeX()+100,o.getY()-o.getSizeY(),
                            o.getR()*2,o.getR()*scaleGame);
                    break;
                case board:
                    g.drawLine(o.getX()-o.getSizeX()+100,
                            o.getY()+o.getSizeY(),
                            o.getX()+o.getSizeX()+100,
                            o.getY()+o.getSizeY());
                    g.drawLine(o.getX()+o.getSizeX()+100,
                            o.getY()+o.getSizeY(),
                            o.getX()+o.getSizeX()+100,
                            o.getY()-o.getSizeY());
                    g.drawLine(o.getX()+o.getSizeX()+100,
                            o.getY()-o.getSizeY(),
                            o.getX()-o.getSizeX()+100,
                            o.getY()-o.getSizeY());
                    g.drawLine(o.getX()-o.getSizeX()+100,
                            o.getY()-o.getSizeY(),
                            o.getX()-o.getSizeX()+100,
                            o.getY()+o.getSizeY());
                    break;

                case object:
                    break;
            }
        }
    }
    public void startGame(){
        game_field = new GameField();
        add(game_field);
    }

    private class GameField extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            onRepaint(g);
            repaint();
        }
    }
    public void endGame(int player){
        end=true;
        this.player=player;
    }
    public void keyTap(int i,boolean is){
        isKey=is;
        key=i;
        if(isKey){
            if(i==65){
                playerController1.move(-1);
            } else if(i==68) {
                playerController1.move(1);
            } else if(i==37){
                playerController2.move(-1);
            } else if(i==39) {
                playerController2.move(1);
            } else game.playPause();
        }else {
            if(i==65||i==68){
                playerController1.move(0);
            } else if(i==37||i==39){
                playerController2.move(0);
            }
        }
    }
}