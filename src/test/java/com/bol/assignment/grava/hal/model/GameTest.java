package com.bol.assignment.grava.hal.model;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

public class GameTest {

    @Test
    public void emptyGameTest(){
        Game game = Game.getEmptyGame();
        assert(isGameEmpty(game));
    }

    @Test
    public void invalidEmptyGameTest(){
        Game game = Game.getEmptyGame();
        assert(!game.isValid());
    }

    @Test
    public void invalidNewGameTest(){
        Game game = new Game(101, 102);
        assert(game.isValid());
    }

    @Test
    public void newGamePitStatusTest(){
        Game game = new Game(101, 102);
        assert(isPitStatusValid(game));
    }

    @Test
    public void newGameWonStatusTest(){
        Game game = new Game(101, 102);
        assert(game.getWon_by() < 0);
    }

    @Test
    public void newGameCurrentPlayerTest(){
        Game game = new Game(101, 102);
        assert(game.getCurrently_playing() == 101 || game.getCurrently_playing() == 102);
    }

    @Test
    public void getStonesInPitTest(){
        Game game = new Game(101, 102);
        assert(Arrays.equals(game.getStonesInPit(101), new int[]{6, 6, 6, 6, 6, 6, 0}));
    }

    @Test
    public void setIdTest(){
        Game game = new Game(101,102);
        game.setId(100);
        assert(game.getId() == 100);
    }


    @Test
    public void setNameTest(){
        Game game = new Game(101, 102);
        game.setName("New Grava Hal Game");
        assert(game.getName().equals("New Grava Hal Game"));
    }


    @Test
    public void setPlayer1_idTest(){
        Game game = Game.getEmptyGame();
        game.setPlayer1_id(101);
        assert(game.getPlayer1_id() == 101 && game.getPlayer2_id() == 0);
    }

    @Test
    public void setPlayer2_idTest(){
        Game game = Game.getEmptyGame();
        game.setPlayer2_id(102);
        assert(game.getPlayer2_id() == 102 && game.getPlayer1_id() == 0);
    }

    @Test
    public void setStonesInPitsStringInvalidTest(){
        Game game = Game.getEmptyGame();
        game.setPlayer1_id(101);
        game.setStonesInPits(101, "3:2:4:6:2:3:4:5:");
        assert(game.getPitStatusString(101).equals("UNKNOWN"));
    }

    @Test
    public void setStonesInPitsStringNegativeNumTest(){
        Game game = Game.getEmptyGame();
        game.setPlayer1_id(101);
        game.setStonesInPits(101, "3:2:4:6:-2:4:5:");
        assert(game.getPitStatusString(101).equals("UNKNOWN"));
    }

    @Test
    public void setStonesInPitsStringBigNumTest(){
        Game game = Game.getEmptyGame();
        game.setPlayer1_id(101);
        game.setStonesInPits(101, "3:2:4:6:122:4:5:");
        assert(game.getPitStatusString(101).equals("UNKNOWN"));
    }

    @Test
    public void setStonesInPitsStringLessPitsTest(){
        Game game = Game.getEmptyGame();
        game.setPlayer1_id(101);
        game.setStonesInPits(101, "3:2:4:4:5:");
        assert(game.getPitStatusString(101).equals("UNKNOWN"));
    }

    @Test
    public void setStonesInPitsStringP1Test(){
        Game game = Game.getEmptyGame();
        game.setPlayer1_id(101);
        game.setStonesInPits(101, "3:2:4:4:5:8:7");
        assert(game.getPitStatusString(101).equals("3:2:4:4:5:8:7:"));
    }

    @Test
    public void setStonesInPitsStringP2Test(){
        Game game = Game.getEmptyGame();
        game.setPlayer2_id(101);
        game.setStonesInPits(101, "3:2:4:4:5:8:7");
        assert(game.getPitStatusString(101).equals("3:2:4:4:5:8:7:"));
    }

    @Test
    public void setStonesInPitsInvalidTest(){
        Game game = Game.getEmptyGame();
        game.setPlayer1_id(101);
        game.setStonesInPits(101, new int[]{3, 2, 4, 6, 2, 3, 4, 5});
        assert(game.getStonesInPit(101) == null);
    }

    @Test
    public void setStonesInPitsNegativeTest(){
        Game game = Game.getEmptyGame();
        game.setPlayer2_id(102);
        game.setStonesInPits(102, new int[]{-1, 12, 45, 23, 22, 2, -4});
        assert(game.getStonesInPit(102) == null);
    }

    @Test
    public void setStonesInPitsBigNumberTest(){
        Game game = Game.getEmptyGame();
        game.setPlayer2_id(102);
        game.setStonesInPits(102, new int[]{111, 12, 45, 23, 22, 2, 4});
        assert(game.getStonesInPit(102) == null);
    }

    @Test
    public void setStonesInPitsMorePitsTest(){
        Game game = Game.getEmptyGame();
        game.setPlayer2_id(102);
        game.setStonesInPits(102, new int[] {1, 12, 45, 23, 22, 2, 4, 34 });
        assert(game.getStonesInPit(102) == null);
    }

    @Test
    public void setStonesInPitsLessPitsTest(){
        Game game = Game.getEmptyGame();
        game.setPlayer2_id(102);
        game.setStonesInPits(102, new int[] {1, 12, 45, 23, 22, 2 });
        assert(game.getStonesInPit(102) == null);
    }

    @Test
    public void setStonesInPitsTest(){
        Game game = Game.getEmptyGame();
        game.setPlayer2_id(102);
        game.setStonesInPits(102, new int[] {1, 12, 45, 23, 22, 2, 0 });
        assert(Arrays.equals(game.getStonesInPit(102), new int[] {1, 12, 45, 23, 22, 2, 0 }));
    }

    @Test
    public void setStonesInPitsPlayer2Test(){
        Game game = Game.getEmptyGame();
        game.setPlayer1_id(102);
        game.setStonesInPits(102, new int[] {1, 12, 45, 23, 22, 2, 0 });
        assert(Arrays.equals(game.getStonesInPit(102), new int[] {1, 12, 45, 23, 22, 2, 0 }));
    }


    @Test
    public void playGameInvalidPlayerTest(){
        Game game = new Game(101, 102);
        assert(!game.playGame(0, -1));
    }

    @Test
    public void playGameWrongPlayerTest(){
        Game game = new Game(101, 102);
        int id = game.getCurrently_playing() == 101 ? 102 : 101;
        assert(!game.playGame(0, id));
    }

    @Test
    public void playGameInvalidPitTest(){
        Game game = new Game(101, 102);
        assert(!game.playGame(7, 101));
    }


    @Test
    public void playGameInvalidStonesInPitTest(){
        Game game = new Game(101, 102);
        int id = game.getCurrently_playing();
        game.setStonesInPits(id, new int[] {0, 4, 2, 2, 2, 5, 3});
        assert(!game.playGame(0, id));
    }

    @Test
    public void playGameInvalidPitGravaHalTest(){
        Game game = new Game(101, 102);
        int id = game.getCurrently_playing();
        assert(!game.playGame(6, id));
    }

    @Test
    public void playGameTest(){
        Game game = new Game(101, 102);
        int id = game.getCurrently_playing();
        assert(game.playGame(3, id));
    }

    @Test
    public void playGameRepeatPlayerTurnTest(){
        Game game = new Game(101, 102);
        int id = game.getCurrently_playing();
        game.playGame(0, id);
        assert(game.getCurrently_playing() == id);
    }

    @Test
    public void setCurrentlyPlayingTest(){
        Game game = new Game(101, 102);
        game.setCurrently_playing(102);
        assert(game.getCurrently_playing() == 102);
    }

    @Test
    public void setWon_byTest(){
        Game game = new Game(101, 102);
        game.setWon_by(101);
        assert(game.getWon_by() == 101);
    }

    @Test
    public void playGameSowStonesPitsStatusTest(){
        Game game = new Game(101, 102);
        int id = game.getCurrently_playing();
        game.playGame(0, id);
        assert(Arrays.equals(game.getStonesInPit(id), new int[]{0, 7, 7, 7, 7, 7, 1}));
    }

    @Test
    public void playGameCaptureStoneTest(){
        Game game = new Game(101, 102);
        int id = game.getCurrently_playing();
        game.playGame(0, id);
        game.playGame(1, id);
        id = game.getCurrently_playing();
        game.playGame(0, id);
        id = game.getCurrently_playing();
        game.playGame(0, id);
        int[] stones = game.getStonesInPit(id);
        assert(stones[6] == 10); //captured stones

    }


    @Test
    public void playGameContinueSowingInOwnsPitsTest(){
        Game game = new Game(101, 102);
        int id = game.getCurrently_playing();
        game.playGame(0, id);
        game.playGame(1, id);
        id = game.getCurrently_playing();
        game.playGame(0, id);
        id = game.getCurrently_playing();
        game.playGame(0, id);
        id = game.getCurrently_playing();
        game.playGame(1, id);
        id = game.getCurrently_playing();
        int[] opponentPits_1 = game.getStonesInPit(id == game.getPlayer1_id() ? 102 : 101);
        game.playGame(5, id);
        int[] opponentPits_2 = game.getStonesInPit(id == game.getPlayer1_id() ? 102 : 101);


        int[] stones = game.getStonesInPit(id);
        assert(stones[6] == 11 && stones[5] == 0 && opponentPits_1[6] == opponentPits_2[6] && stones[0] == 1); //sowed stones in opponents pits and then back in own's pits
    }


    @Test
    public void playGameWonGameTest(){
        Game game = new Game(101, 102);
        int id = game.getCurrently_playing();
        game.playGame(0, id);
        game.playGame(1, id);
        id = game.getCurrently_playing();
        game.playGame(0, id);
        id = game.getCurrently_playing();
        game.playGame(0, id);
        id = game.getCurrently_playing();
        game.playGame(1, id);
        id = game.getCurrently_playing();
        game.playGame(5, id);
        id = game.getCurrently_playing();
        game.playGame(0, id);
        id = game.getCurrently_playing();
        game.playGame(1, id);
        id = game.getCurrently_playing();
        game.playGame(1, id);
        id = game.getCurrently_playing();
        game.playGame(0, id);
        id = game.getCurrently_playing();
        game.playGame(2, id);
        id = game.getCurrently_playing();
        game.playGame(1, id);
        id = game.getCurrently_playing();
        game.playGame(4, id);
        id = game.getCurrently_playing();
        game.playGame(5, id);
        game.playGame(0, id);
        id = game.getCurrently_playing();
        game.playGame(3, id);
        id = game.getCurrently_playing();
        game.playGame(5, id);
        game.playGame(1, id);
        id = game.getCurrently_playing();
        game.playGame(1, id);
        id = game.getCurrently_playing();
        game.playGame(0, id);
        id = game.getCurrently_playing();
        game.playGame(5, id);

        int won_id = game.getPlayer1_id() == id ? game.getPlayer2_id() : game.getPlayer1_id();
        assert(game.getWon_by() == won_id);
    }

    @Test
    public void getStonesInPitStringInValidTest(){
        assert(Game.getStonesInPitString(new int[] {0, 0, 2, 3, 4, 5, 5, 6}).equals("UNKNOWN"));
    }


    @Test
    public void getStonesInPitStringInNegativeCountTest(){
        assert(Game.getStonesInPitString(new int[] {0, -1, 2, 3, 4, 5, 6}).equals("UNKNOWN"));
    }

    @Test
    public void getStonesInPitStringInBiggerNumberTest(){
        assert(Game.getStonesInPitString(new int[] {0, 1, 2, 3, 4, 5, 156}).equals("UNKNOWN"));
    }


    @Test
    public void getStonesCountInvalidTest(){
        assert(Game.getStonesCount("0:0:2:3:4:5:5:3:2") == null);
    }


    @Test
    public void getStonesCountInvalidNegativeTest(){
        assert(Game.getStonesCount("0:0:2:3:-4:5:8") == null);
    }

    @Test
    public void getStonesCountTest(){
        int[] arr = Game.getStonesCount("0:0:2:3:4:5:8");
        assert(Arrays.equals(arr, new int[] {0,0,2,3,4,5,8}));
    }

    @Test
    public void getStonesCountInvalidBigNumTest(){
        assert(Game.getStonesCount("0:0:2:3:4:5:511") == null);
    }


    private boolean isPitStatusValid(Game game){
        return (game.getPitStatusString(game.getPlayer1_id()).equals(game.getPitStatusString(game.getPlayer2_id()))
                && game.getPitStatusString(game.getPlayer2_id()).equals("6:6:6:6:6:6:0:"));
    }

    private boolean isGameEmpty(Game game){
        return (game.getId() == 0 && game.getName() == null && game.getPlayer1_id() == 0 &&
                game.getWon_by() == 0 && game.getCurrently_playing() == 0 &&
                game.getPlayer2_id() == 0);
    }
}
