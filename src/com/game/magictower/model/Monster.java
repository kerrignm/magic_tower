package com.game.magictower.model;

public class Monster {
    
    private static final int ID_BOSS = 59;
    
    private int id;
    private int hp;
    private int attack;
    private int defend;
    private int money;
    private int exp;
    private String name;
    private boolean stronger;
    private int magicDamage;

    public Monster(int id, int hp, int attack, int defend, int money, int exp, String name, boolean stronger, int magicDamage) {
        this.id = id;
        this.name = name;
        this.hp = hp;
        this.attack = attack;
        this.defend = defend;
        this.money = money;
        this.exp = exp;
        this.stronger = stronger;
        this.magicDamage = magicDamage;
    }

    public int getId() {
        return id;
    }

    public int getHp() {
        return hp;
    }

    public int getAttack() {
        return attack;
    }

    public int getDefend() {
        return defend;
    }

    public int getMoney() {
        return money;
    }

    public int getExp() {
        return exp;
    }

    public String getName() {
        return name;
    }
    
    public boolean getStronger() {
        return stronger;
    }
    
    public void setStronger() {
        if (!stronger) {
            stronger = true;
            hp = hp * 4 / 3;
            attack = attack * 4 / 3;
            defend = defend * 4 / 3;
            money = money * 4 / 3;
            exp = exp * 4 / 3;
        }
    }
    
    public void setStrongest() {
        if (id == ID_BOSS) {
            hp = hp * 3 / 2;
            attack = attack * 3 / 2;
            defend = defend * 3 / 2;
            money = money * 3 / 2;
            exp = exp * 3 / 2;
        }
    }
    
    public int getMagicDamage() {
        return magicDamage;
    }

}
