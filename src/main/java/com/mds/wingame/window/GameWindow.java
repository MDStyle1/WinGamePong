package com.mds.wingame.window;

import com.mds.game.Game;
import com.mds.game.GameInterface;
import com.mds.game.controller.PlayerControllerInterface;
import com.mds.game.map.MapInterface;
import com.mds.game.map.objects.ObjectMap;
import com.mds.game.map.objects.TypeObject;
import com.mds.game.util.Vector2d;
import com.mds.wingame.Starter;

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
    private PlayerControllerInterface playerController;
    private GameField game_field;
    public GameWindow(GameInterface game) throws HeadlessException {
        this.game=game;
        scaleGame = 2;
    }

    public void startWindow(Starter starter) {
        map = starter.map;
        playerController=starter.player;
        settingWindowStart();
        startGame();
        game.playPause();
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
                if(key==65){
                    keyTap(1,true);
                }else if(key==68){
                    keyTap(2,true);
                } else if(key==32){
                    keyTap(3,true);
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                int key= e.getKeyCode();
                if(key==65){
                    keyTap(1,false);
                }else if(key==68){
                    keyTap(2,false);
                }
            }
        });
        setVisible(true);
    }
    private void onRepaint(Graphics g){
        List<ObjectMap> objects= map.getObjectsMap();
        g.drawLine(100,0,100,map.getSizeY());
        g.drawLine(100,0,map.getSizeX()+100,0);
        g.drawLine(100,map.getSizeY(),map.getSizeX()+100,map.getSizeY());
        g.drawLine(map.getSizeX()+100,0,map.getSizeX()+100,map.getSizeY());
        if(isKey){
            String str=new String("");
            if(key==1){
                str="Key down A";
            } else str="Key down D";
            g.drawString(str,200,350);
        }
        if(end){
            String str=new String("");
            if(player==1){
                str="Player win!!";
            }str="Bot win!!!";
            g.drawString(str,200,200);
        }for(ObjectMap o:objects){
            TypeObject type = o.getTypeObject();
            switch (type){
                case ball:
                    Vector2d vectorMove = o.getForwardVector();
                    g.drawLine(o.getX()+100,
                            o.getY(),
                            o.getX()+(int)vectorMove.getX()*50+100,
                            o.getY()+(int)vectorMove.getY()*50);
                    g.drawOval(o.getX()-o.getSizeX()+100,o.getY()-o.getSizeY(),
                            o.getR()*scaleGame,o.getR()*scaleGame);
                    vectorMove=o.testV1;
                    g.drawLine(o.getX()+100,
                            o.getY(),
                            o.getX()+(int)vectorMove.getX()*15+100,
                            o.getY()+(int)vectorMove.getY()*15);
                    break;
                case board:
                    vectorMove = o.getForwardVector();
                    g.drawLine(o.getX()+100,
                            o.getY(),
                            o.getX()+(int)vectorMove.getX()*50+100,
                            o.getY()+(int)vectorMove.getY()*50);
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
            if(i==1){
                playerController.move(-1);
            } else if(i==2) {
                playerController.move(1);
            } else game.playPause();
        }else  playerController.move(0);
    }
}