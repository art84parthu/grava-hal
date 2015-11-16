package com.bol.assignment.grava.hal.dao;

import com.bol.assignment.grava.hal.model.Player;

public interface PlayerDao {

    Player getPlayerByName(String name);

    Player getPlayerById(int id);

    int addNewPlayer(Player user);

}
