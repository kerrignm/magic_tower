package com.game.magictower.model;

import com.game.magictower.res.Assets;
import com.game.magictower.res.LiveBitmap;

public class Player {
    
    private static final float CRIT_RATE = 5.0f;
    private static final float RISE_RATE = 0.005f;

    private int level;
    private int hp;
    private int attack;
    private int defend;
    private int money;
    private int exp;
    private int Ykey;
    private int Bkey;
    private int Rkey;

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
        this.Ykey = 0;
        this.Bkey = 0;
        this.Rkey = 0;

        this.toward = 1;
        this.posX = 5;
        this.posY = 9;
    }

    public void move(int cx, int cy) {
        posX = cx;
        posY = cy;
    }

    public LiveBitmap getImage() {
        if (toward == 0)
            return Assets.getInstance().playerMap.get(-1);
        if (toward == 1)
            return Assets.getInstance().playerMap.get(-2);
        if (toward == 2)
            return Assets.getInstance().playerMap.get(-3);
        if (toward == 3)
            return Assets.getInstance().playerMap.get(-4);
        return null;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getDefend() {
        return defend;
    }

    public void setDefend(int defend) {
        this.defend = defend;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public int getYkey() {
        return Ykey;
    }

    public void setYkey(int ykey) {
        Ykey = ykey;
    }

    public int getBkey() {
        return Bkey;
    }

    public void setBkey(int bkey) {
        Bkey = bkey;
    }

    public int getRkey() {
        return Rkey;
    }

    public void setRkey(int rkey) {
        Rkey = rkey;
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

