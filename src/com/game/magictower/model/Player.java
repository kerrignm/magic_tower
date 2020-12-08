package com.game.magictower.model;

import android.graphics.Bitmap;

import com.game.magictower.res.Assets;

public class Player extends BaseInfo {
    
    private static final float CRIT_RATE = 5.0f;
    private static final float RISE_RATE = 0.005f;

    private int exp;

    private int toward;     //  0-left 1-down 2-right 3-up
    private int posX;
    private int posY;

    public Player() {
        reset();
    }
    
    public void reset() {
        this.level = 1;
        this.hp = 1000;
        this.attack = 10;
        this.defend = 10;
        this.money = 0;
        this.exp = 0;
        this.ykey = 0;
        this.bkey = 0;
        this.rkey = 0;

        this.toward = 1;
        this.posX = 5;
        this.posY = 9;
    }

    public void move(int cx, int cy) {
        posX = cx;
        posY = cy;
    }

    public Bitmap getImage() {
        return Assets.getInstance().playerMap.get(toward);
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public int getToward() {
        return toward;
    }

    public void setToward(int toward) {
        this.toward = toward;
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }
    
    public float getCritRate() {
        return CRIT_RATE + RISE_RATE * attack;
    }
}

