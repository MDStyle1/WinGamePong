package com.mds.wingame;

import com.mds.wingame.window.VisualEvent;

public class WinGame {
    public static void main(String[] args) {
        WinGame winGame = new WinGame();
        winGame.Start();
    }
    private VisualEvent visualEvent;
    private void Start(){
        visualEvent=new VisualEvent();
    }
}
