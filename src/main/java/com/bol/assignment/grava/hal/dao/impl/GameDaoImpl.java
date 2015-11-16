package com.bol.assignment.grava.hal.dao.impl;

import com.bol.assignment.grava.hal.dao.GameDao;
import com.bol.assignment.grava.hal.model.Game;
import com.bol.assignment.grava.hal.model.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class GameDaoImpl implements GameDao {

    private static int gameId = 100;
    private NamedParameterJdbcTemplate template;
    private RowMapper<Game> gameMapper = (rs, rowNum) -> {
        Game g = Game.getEmptyGame();

        g.setId(rs.getInt("id"));
        g.setName(rs.getString("name"));
        g.setPlayer1_id(rs.getInt("player1_id"));
        g.setPlayer2_id(rs.getInt("player2_id"));
        g.setStonesInPits(g.getPlayer1_id(), rs.getString("stonesInPits_p1"));
        g.setStonesInPits(g.getPlayer2_id(), rs.getString("stonesInPits_p2"));
        g.setCurrently_playing(rs.getInt("currently_playing"));
        g.setWon_by(rs.getInt("won_by"));

        return g;
    };

    @Autowired
    public GameDaoImpl(DataSource ds) {
        template = new NamedParameterJdbcTemplate(ds);
    }

    @Override
    public int addNewGame(Game game) {

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", gameId++);
        params.put("name", game.getName());
        params.put("player1", game.getPlayer1_id());
        params.put("player2", game.getPlayer2_id());
        params.put("stonesInPits_p1", game.getPitStatusString(game.getPlayer1_id()));
        params.put("stonesInPits_p2", game.getPitStatusString(game.getPlayer2_id()));
        params.put("currently_playing", game.getCurrently_playing());
        params.put("won_by", game.getWon_by());

        String sql = "insert into game_info (id, name, player1_id, player2_id, stonesInPits_p1, stonesInPits_p2, currently_playing, won_by) values (:id, :name, :player1, :player2, :stonesInPits_p1, :stonesInPits_p2, :currently_playing, :won_by)";

        template.update(sql, params);

        return (gameId - 1);
    }

    @Override
    public Game getGameById(int game_id) {
        Game game = null;
        if (game_id > 0) {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("id", game_id);

            String sql = "select * from game_info where id=:id";

            List<Game> list = template.query(sql, params, gameMapper);

            if (list != null && list.size() > 0) {
                game = list.get(0);
            }
        }
        return game;
    }

    @Override
    public List<Game> getGamesPlayedBy(Player user) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", user.getId());

        String sql = "select * from game_info where player1_id=:id or player2_id=:id";

        List<Game> games = template.query(sql, params, gameMapper);

        if (games != null && games.size() > 0) return games;

        return null;
    }

    @Override
    public List<Game> getGamesWonBy(Player user) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", user.getId());

        String sql = "select * from game_info where won_by=:id";

        List<Game> games = template.query(sql, params, gameMapper);

        if (games != null && games.size() > 0) return games;

        return null;
    }

    @Override
    public int whoseTurnNow(int game_id) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", game_id);

        String sql = "select * from game_info where id=:id";

        List<Game> games = template.query(sql, params, gameMapper);

        if (games != null && games.size() > 0) {
            Game game = games.get(0);
            if (game.getCurrently_playing() == game.getPlayer1_id()) {
                return 2;
            } else {
                return 1;
            }
        }

        return -1;
    }

    @Override
    public void updateGameStatus(Game game) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("game_id", game.getId());
        params.put("stonesInPits_p1", game.getPitStatusString(game.getPlayer1_id()));
        params.put("stonesInPits_p2", game.getPitStatusString(game.getPlayer2_id()));
        params.put("currently_playing", game.getCurrently_playing());
        params.put("won_by", game.getWon_by());

        String sql = "update game_info set stonesInPits_p1=:stonesInPits_p1, stonesInPits_p2=:stonesInPits_p2, currently_playing=:currently_playing, won_by=:won_by where id=:game_id";
        template.update(sql, params);
    }
}
