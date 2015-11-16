package com.bol.assignment.grava.hal.model;

import org.junit.Test;

public class PlayerTest {

    @Test
    public void emptyPlayerTest(){
        Player p = new Player();

        assert(p.getId() == 0);
        assert(p.getName() == null);
    }

    @Test
    public void setPlayerDetailsTest(){
        Player p = new Player();
        p.setId(101);
        p.setName("Player One");

        assert(p.getName().equals("Player One"));
        assert(p.getId() == 101);
    }

    @Test
    public void validateEmptyPlayerTest(){
        Player p = new Player();
        assert(p.validate().contains("ERR:"));
    }

    @Test
    public void validateEmptyIdPlayerTest(){
        Player p = new Player();
        p.setName("Player name");
        assert(p.validate().contains("ERR: There is no id!"));
    }

    @Test
    public void validateEmptyNamePlayerTest(){
        Player p = new Player();
        p.setId(101);
        assert(p.validate().contains("ERR: Please give a name!"));
    }

    @Test
    public void validateValidPlayerTest(){
        Player p = new Player();
        p.setId(101);
        p.setName("Player One");
        assert(p.validate() == null);
    }



}
