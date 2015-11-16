package com.bol.assignment.grava.hal.config;

import com.bol.assignment.grava.hal.model.Game;
import com.bol.assignment.grava.hal.service.impl.GravaHalService;
import spark.ModelAndView;
import spark.Request;
import spark.template.freemarker.FreeMarkerEngine;

import java.util.HashMap;
import java.util.Map;

import static spark.Spark.get;
import static spark.SparkBase.staticFileLocation;


public class WebConfig {

    private static final String GAME_SESSION_ID = "game_session_id";
    private GravaHalService service;

    public WebConfig(GravaHalService service) {
        this.service = service;
        staticFileLocation("/public");
        setupRoutes();
    }

    private void setupRoutes() {
        get("/", (req, res) -> {
            int gameId = getStoredGameId(req);
            Map<String, Object> map = new HashMap<>();
            map.put("pageTitle", "Grava Hal");
            Game game = null;
            if (gameId > 0) {
                game = service.getGameById(gameId);
            }
            if (game == null) {
                game = service.createNewGame();
                storeGameIdInSession(req, game.getId());
            }
            if (game.getWon_by() > 0) {
                map.put("won_by", game.getWon_by() == game.getPlayer1_id() ? "One" : "Two");
            }
            map.put("game", game);
            map.put("player1_pits", game.getStonesInPit(game.getPlayer1_id()));
            map.put("player2_pits", game.getStonesInPit(game.getPlayer2_id()));
            map.put("which_player", game.getCurrently_playing());
            return new ModelAndView(map, "gravahal_main.ftl");
        }, new FreeMarkerEngine());


        get("/play/:pitIndex/:playerId", (req, res) -> {
            int gameId = getStoredGameId(req);
            Map<String, Object> map = new HashMap<>();
            map.put("pageTitle", "Grava Hal");
            map.put("game_id", gameId);
            Game game = null;
            if (gameId > 0) {
                game = service.getGameById(gameId);
            } else {
                map.put("error", "Unable to find the game!");
                return new ModelAndView(map, "gravahal_main.ftl");
            }
            int pitIndex = Integer.parseInt(req.params(":pitIndex"));
            int playerId = Integer.parseInt(req.params(":playerId"));
            String msg = service.canPlay(game, pitIndex, playerId);
            if (msg.equals("OK")) {
                game = service.playGame(pitIndex, gameId, playerId);
            } else {
                map.put("error", msg);
            }

            if (game.getWon_by() > 0) {
                map.put("won_by", game.getWon_by() == game.getPlayer1_id() ? "One" : "Two");
            }
            map.put("game", game);
            map.put("player1_pits", game.getStonesInPit(game.getPlayer1_id()));
            map.put("player2_pits", game.getStonesInPit(game.getPlayer2_id()));
            map.put("which_player", game.getCurrently_playing());
            return new ModelAndView(map, "gravahal_main.ftl");
        }, new FreeMarkerEngine());

    }


    private void removeGameFromSession(Request req, int game_id) {
        req.session().removeAttribute(GAME_SESSION_ID);
    }

    private void storeGameIdInSession(Request req, int game_id) {
        if (game_id > 0) {
            req.session().attribute(GAME_SESSION_ID, game_id);
        }
    }

    private int getStoredGameId(Request req) {
        Integer id = req.session().attribute(GAME_SESSION_ID);
        if (id == null) {
            return -1;
        }
        return id;
    }
}
