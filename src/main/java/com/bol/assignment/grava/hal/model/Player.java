package com.bol.assignment.grava.hal.model;

import spark.utils.StringUtils;


public class Player {
    private int id;
    private String name;

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

    public String validate() {
        String error = null;

        if (StringUtils.isEmpty(name)) {
            error = "ERR: Please give a name!";
        }

        if(id <= 0 ){
            error += "\nERR: There is no id!";
        }

        return error;
    }
}
