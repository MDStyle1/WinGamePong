package com.mds.wingame;

import com.mds.game.StartCreateInterface;
import com.mds.game.controller.PlayerControllerInterface;
import com.mds.game.map.MapInterface;

public class Starter implements StartCreateInterface {
    public PlayerControllerInterface player;
    public MapInterface map;
    @Override
    public void setPlayer(PlayerControllerInterface playerControllerInterface) {
        player=playerControllerInterface;
    }

    @Override
    public void setMap(MapInterface mapInterface) {
        map = mapInterface;
    }
}
