package com.bol.assignment.grava.hal.dao;


import com.bol.assignment.grava.hal.model.Game;
import com.bol.assignment.grava.hal.model.Player;

import java.util.List;

public interface GameDao {

    int addNewGame(Game game);

    Game getGameById(int game_id);

    List<Game> getGamesPlayedBy(Player user);

    List<Game> getGamesWonBy(Player user);

    int whoseTurnNow(int game_id);

    void updateGameStatus(Game game);
}
