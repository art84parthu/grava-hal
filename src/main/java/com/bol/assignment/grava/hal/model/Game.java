package com.bol.assignment.grava.hal.model;


import com.bol.assignment.grava.hal.model.common.Constants;

import java.util.HashMap;

public class Game {
    private static final HashMap<Integer, Integer> pitsMap = new HashMap<Integer, Integer>();

    static {
        pitsMap.put(5, 0);
        pitsMap.put(4, 1);
        pitsMap.put(3, 2);
        pitsMap.put(2, 3);
        pitsMap.put(1, 4);
        pitsMap.put(0, 5);
    }

    private int id;
    private String name;
    private int player1_id;
    private int player2_id;
    private int[] stonesInPits_p1;
    private int[] stonesInPits_p2;
    private int currently_playing;
    private int won_by;

    private Game() {
    }

    public Game(int player1_id, int player2_id) {
        this.name = Constants.DEFAULT_GAME_NAME;
        this.player1_id = player1_id;
        this.player2_id = player2_id;
        this.stonesInPits_p1 = getDefaultStonesInPitsForNewGame();
        this.stonesInPits_p2 = getDefaultStonesInPitsForNewGame();
        this.currently_playing = chooseRandomPlayer() == 1 ? player1_id : player2_id;
        this.won_by = -1;
    }

    //Empty game is not the same as new game! :-)
    public static Game getEmptyGame() {
        return new Game();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPlayer1_id() {
        return player1_id;
    }

    public void setPlayer1_id(int player1_id) {
        this.player1_id = player1_id;
    }

    public int getPlayer2_id() {
        return player2_id;
    }

    public void setPlayer2_id(int player2_id) {
        this.player2_id = player2_id;
    }

    public int getCurrently_playing() {
        return currently_playing;
    }

    public int getWon_by() {
        return won_by;
    }

    public void setCurrently_playing(int currently_playing) {
            this.currently_playing = currently_playing;
    }

    public void setWon_by(int won_by) {
            this.won_by = won_by;
    }

    public boolean isValid() {
        if (player1_id <= 0 || player2_id <= 0) return false;

        return true;
    }

    public int[] getStonesInPit(int player) {
        if (player == this.player1_id) {
            return this.stonesInPits_p1;
        } else if (player == this.player2_id) {
            return this.stonesInPits_p2;
        }

        return null;
    }

    public String getPitStatusString(int player) {
        String stones = null;
        if (player == player2_id) {
            stones = getStonesInPitString(this.stonesInPits_p2);
        } else if (player == player1_id) {
            stones = getStonesInPitString(this.stonesInPits_p1);
        }
        return stones;
    }

    public void setStonesInPits(int player, int[] stonesInPits) {
        if(isValidPitStoneStatus(stonesInPits)) {
            if (player == player1_id) {
                this.stonesInPits_p1 = stonesInPits;
            } else {
                this.stonesInPits_p2 = stonesInPits;
            }
        }
    }

    public void setStonesInPits(int player, String stones) {

        if (stones != null) {
            int[] stones_arr = getStonesCount(stones);
            if (!isValidPitStoneStatus(stones_arr)) {
                return;
            }
            if (player == player1_id) {
                this.stonesInPits_p1 = stones_arr;
            } else {
                this.stonesInPits_p2 = stones_arr;
            }
        }
    }

    //returning false here means, there is something wrong with the input.
    public boolean playGame(int pitIndex, int playerId) {

        int[] stones = getStonesInPit(playerId);
        int playing = playerId;

        if(pitIndex < 0 || pitIndex >= getTotalNumberOfPitsPerPlayer()) return false;
        if(!isValidPitStoneStatus(stones)) return false;
        if(playerId != this.currently_playing) return false;
        if (this.won_by > 0) return false;

        if (isPitNonEmpty(pitIndex, stones)) {
            Boolean result = sowStones(true, -1, pitIndex, playerId);
            if (result == null) return false;

            if (result) {
                //switch player
                //We should do this only here and not in the sowStones because we kept shuffling
                //player id in the sowStones. So, at the time we when stones are used up we might already be
                //having a different player id.
                this.currently_playing = this.getOtherPlayerId(playerId);
            }

            return true;
        }

        return false;
    }

    public static String getStonesInPitString(int[] arr) {
        String stonesInPits = "";

        if (arr != null && arr.length == getTotalNumberOfPitsPerPlayer()) {
            for (int i : arr) {
                if (i >= 0 && i <= (Constants.PITS_STONE_COUNT_DEFAULT * getTotalNumberOfPitsPerPlayer() * 2)) {
                    stonesInPits += i + ":";
                } else {
                    stonesInPits = "UNKNOWN";
                    break;
                }
            }
        } else {
            stonesInPits = "UNKNOWN";
        }

        return stonesInPits;
    }

    public static int[] getStonesCount(String str) {
        if (str == null) return null;
        String[] arr = str.split(":");
        int[] stones_in_pits = null;
        if (arr.length == getTotalNumberOfPitsPerPlayer()) {
            stones_in_pits = new int[getTotalNumberOfPitsPerPlayer()];
            for (int i = 0; i < getTotalNumberOfPitsPerPlayer(); i++) {
                stones_in_pits[i] = Integer.parseInt(arr[i]);
                if(!(stones_in_pits[i] >= 0 && stones_in_pits[i] <= (getTotalNumberOfPitsPerPlayer() * 2 * Constants.PITS_STONE_COUNT_DEFAULT))) {
                    stones_in_pits = null;
                    break;
                }
            }
        }
        return stones_in_pits;
    }

    private static int[] getDefaultStonesInPitsForNewGame() {
        int[] stones = new int[Constants.NUM_NORMAL_PITS_PER_PLAYER_DEFAULT + Constants.NUM_GRAVA_HAL_PITS_PER_PLAYER_DEFAULT];

        int i = 0;
        for (; i < Constants.NUM_NORMAL_PITS_PER_PLAYER_DEFAULT; i++) {
            stones[i] = Constants.PITS_STONE_COUNT_DEFAULT;
        }
        stones[i] = Constants.GRAVA_HAL_STONE_COUNT_DEFAULT;

        return stones;
    }

    private static int chooseRandomPlayer() {
        return (int) (Math.random() * 2 + 1);
    }

    private static boolean continueSowingStonesInOpponentsPits(int stonesAvailable, int pitIndex) {
        return (stonesAvailable > 0 && pitIndex == getTotalNumberOfPitsPerPlayer());
    }

    private static boolean switchPlayerTurn(int stonesAvailable, int pitIndex) {
        return (stonesAvailable == 0 && pitIndex < getIndexOfGravaHal());
    }

    private static boolean playerGetsAnotherTurn(int stonesAvailable, int pitIndex) {
        return (stonesAvailable == 0 && pitIndex == getIndexOfGravaHal());
    }

    private static int getTotalStonesCount(int[] stones) {
        int sum = 0;
        for (int i : stones) {
            sum += i;
        }
        return sum;
    }

    private static boolean checkIfAllNormalPitsAreEmpty(int[] stones) {
        boolean allNormalPitsEmpty = true;
        for (int i = 0; i < Constants.NUM_NORMAL_PITS_PER_PLAYER_DEFAULT; i++) {
            if (stones[i] > 0) {
                allNormalPitsEmpty = false;
                break;
            }
        }
        return allNormalPitsEmpty;
    }

    private static boolean isPitOpponentsGravaHal(boolean ownSide, int pitIndex) {
        return (!ownSide && pitIndex == Constants.NUM_NORMAL_PITS_PER_PLAYER_DEFAULT);
    }

    private static int getTotalNumberOfPitsPerPlayer() {
        return (Constants.NUM_NORMAL_PITS_PER_PLAYER_DEFAULT + Constants.NUM_GRAVA_HAL_PITS_PER_PLAYER_DEFAULT);
    }

    private static boolean ifCaptureStonesConditionBeforeSow(boolean ownSide, int pitIndex, int stonesAvailable, int[] stones) {
        return (stonesAvailable == 1 && stones[pitIndex] == 0 && ownSide && pitIndex != getIndexOfGravaHal());
    }

    private static int getIndexOfGravaHal() {
        return Constants.NUM_NORMAL_PITS_PER_PLAYER_DEFAULT;
    }

    private boolean isPitNonEmpty(int index, int[] stones) {
        return stones[index] > 0 ? true : false;
    }

    //returning true means, the current player is done.. change player side.
    //false means, the current player has to play still.. dont change the player side.
    //returning numm means something wrong with the input.
    private Boolean sowStones(boolean ownSide, int stonesAvailable, int pitIndex, int playerId) {
        if (stonesAvailable == 0 || playerId < 0) return null;

        int[] stones = getStonesInPit(playerId);

        if (stonesAvailable == -1) {
            //Start playing
            stonesAvailable = stones[pitIndex];
            stones[pitIndex] = 0;
        }

        while (stonesAvailable > 0 && ++pitIndex < getTotalNumberOfPitsPerPlayer()) {
            if (isPitOpponentsGravaHal(ownSide, pitIndex)) {
                //Don't sow in opponent's grava hal pit.
                //since stonesAvailable > 0, switch side and continue playing in own side
                return this.sowStones(true, stonesAvailable, 0, this.getOtherPlayerId(playerId));
            } else {
                if (ifCaptureStonesConditionBeforeSow(ownSide, pitIndex, stonesAvailable, stones)) {
                    //capture stones
                    this.captureStones(playerId, pitIndex, stones);
                    if (this.isGameOver()) {
                        return false;
                    } else {
                        return true;//change side and continue playing.
                    }
                } else {
                    stones[pitIndex] += 1;
                    stonesAvailable -= 1;
                }
            }
        }

        if (isGameOver()) return false;

        if (playerGetsAnotherTurn(stonesAvailable, pitIndex)) {
            //last sowed in grava hal. So, play again. Ask user to choose pit again.
            //currently playing doesnt change here. so, simply return and wait input from user.
            return false;
        }

        if (switchPlayerTurn(stonesAvailable, pitIndex)) {
            //change player turn. await input from the opponent.
            return true;
        }

        if (continueSowingStonesInOpponentsPits(stonesAvailable, pitIndex)) {
            //continue sowing stones in the opponents pits until its over.
            return this.sowStones(!ownSide, stonesAvailable, -1, this.getOtherPlayerId(playerId));
        }

        return null;
    }

    private boolean isGameOver() {
        if (checkIfAllNormalPitsAreEmpty(this.stonesInPits_p2) || checkIfAllNormalPitsAreEmpty(this.stonesInPits_p1)) {
            this.won_by = getTotalStonesCount(this.stonesInPits_p1) > getTotalStonesCount(this.stonesInPits_p2) ? this.player1_id : this.player2_id;
            return true;
        }
        return false;
    }

    private void captureStones(int playerId, int pitIndex, int[] stones) {
        int[] opponentStones = playerId == this.player1_id ? this.stonesInPits_p2 : this.stonesInPits_p1;
        int capturedStones = 1 + opponentStones[pitsMap.get(pitIndex)];
        stones[getIndexOfGravaHal()] += capturedStones;
        stones[pitIndex] = opponentStones[pitsMap.get(pitIndex)] = 0;
    }

    private int getOtherPlayerId(int playerId) {
        return playerId == this.player1_id ? this.player2_id : this.player1_id;
    }

    private boolean isValidPitStoneStatus(int[] stones){
        if(stones != null && stones.length == getTotalNumberOfPitsPerPlayer()){
            for(int i : stones){
                if(i < 0 || i > (Constants.PITS_STONE_COUNT_DEFAULT * getTotalNumberOfPitsPerPlayer() * 2))
                    return false;
            }
            return true;
        }
        return false;
    }
}
