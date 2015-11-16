package com.bol.assignment.grava.hal.service.impl;

import com.bol.assignment.grava.hal.dao.GameDao;
import com.bol.assignment.grava.hal.dao.PlayerDao;
import com.bol.assignment.grava.hal.model.Game;
import com.bol.assignment.grava.hal.model.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GravaHalService {

    @Autowired
    private GameDao gameDao;

    @Autowired
    private PlayerDao playerDao;

    public int addNewGame(Game game) {
        return gameDao.addNewGame(game);
    }

    public int addNewPlayer(Player p) {
        return playerDao.addNewPlayer(p);
    }

    public Game createNewGame() {
        Player p1 = new Player();
        p1.setName("Player 1");
        int p1_id = addNewPlayer(p1);

        Player p2 = new Player();
        p2.setName("Player 2");
        int p2_id = addNewPlayer(p1);

        Game newGame = new Game(p1_id, p2_id);
        int id = addNewGame(newGame);
        newGame.setId(id);

        return newGame;
    }

    public Game getGameById(int game_id) {
        return gameDao.getGameById(game_id);
    }

    public List<Game> getGamesPlayedBy(Player user) {
        return gameDao.getGamesPlayedBy(user);
    }

    public List<Game> getGamesWonBy(Player user) {
        return gameDao.getGamesWonBy(user);
    }

    public Game playGame(int pitIndex, int gameId, int player_id) {
        Game game = getGameById(gameId);
        if (game == null) return null;

        game.playGame(pitIndex, player_id);
        gameDao.updateGameStatus(game);
        return game;
    }

    public String canPlay(Game game, int pitIndex, int playerId) {
        if (game.getWon_by() > 0) {
            String player = game.getWon_by() == game.getPlayer1_id() ? "Player one!" : "Player two!";
            return "ERR: Game over. Won by " + player;
        }

        if (game.getCurrently_playing() != playerId) {
            String player = game.getCurrently_playing() == game.getPlayer1_id() ? " Player one" : "Player two";
            return "ERR: Now its " + player + "'s turn!";
        }

        return "OK";
    }
}
